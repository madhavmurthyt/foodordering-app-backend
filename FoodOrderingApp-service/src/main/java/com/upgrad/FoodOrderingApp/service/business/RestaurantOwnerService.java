package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.common.ItemType;
import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantOwnerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private AddressDao addressDao;

    public List<RestaurantEntity> getMyRestaurants(Integer ownerId) {
        return entityManager.createQuery(
                "SELECT r FROM RestaurantEntity r WHERE r.owner.id = :ownerId ORDER BY r.restaurantName",
                RestaurantEntity.class)
            .setParameter("ownerId", ownerId)
            .getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity createRestaurant(CustomerEntity owner, String name, String photoUrl,
                                              Integer avgPrice, String flatBuildingName, String locality,
                                              String city, String pincode, String stateUuid)
            throws RestaurantNotFoundException {

        StateEntity state = stateDao.findStateByUUID(stateUuid);
        if (state == null) {
            throw new RestaurantNotFoundException("SNF-001", "State not found");
        }

        AddressEntity address = new AddressEntity(UUID.randomUUID().toString(), flatBuildingName, locality, city, pincode, state);
        address.setActive(1);
        addressDao.saveAddress(address);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setUuid(UUID.randomUUID().toString());
        restaurant.setRestaurantName(name);
        restaurant.setPhotoUrl(photoUrl);
        restaurant.setAvgPrice(avgPrice != null ? avgPrice : 0);
        restaurant.setCustomerRating(0);
        restaurant.setNumberCustomersRated(0);
        restaurant.setStatus("PENDING");
        restaurant.setOwner(owner);
        restaurant.setAddress(address);
        restaurantDao.saveRestaurant(restaurant);

        return restaurant;
    }

    private RestaurantEntity getOwnedRestaurant(String restaurantUuid, CustomerEntity owner)
            throws RestaurantNotFoundException, AuthorizationFailedException {
        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }
        if (restaurant.getOwner() == null || !restaurant.getOwner().getId().equals(owner.getId())) {
            throw new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation");
        }
        return restaurant;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemEntity addMenuItem(String restaurantUuid, CustomerEntity owner,
                                   String itemName, Integer price, String type)
            throws RestaurantNotFoundException, AuthorizationFailedException, ItemNotFoundException {
        RestaurantEntity restaurant = getOwnedRestaurant(restaurantUuid, owner);

        ItemType itemType;
        try {
            itemType = ItemType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ItemNotFoundException("ITM-003", "Type must be VEG or NON_VEG");
        }

        ItemEntity item = new ItemEntity();
        item.setUuid(UUID.randomUUID().toString());
        item.setItemName(itemName);
        item.setPrice(price);
        item.setType(itemType);
        itemDao.saveItem(item);

        restaurantDao.addRestaurantItem(restaurant.getId(), item.getId());
        return item;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemEntity editMenuItem(String restaurantUuid, CustomerEntity owner,
                                    String itemUuid, String itemName, Integer price, String type)
            throws RestaurantNotFoundException, AuthorizationFailedException, ItemNotFoundException {
        getOwnedRestaurant(restaurantUuid, owner);

        ItemEntity item = itemDao.getItemById(itemUuid);
        if (item == null) {
            throw new ItemNotFoundException("INF-001", "Item not found");
        }

        if (itemName != null && !itemName.isEmpty()) item.setItemName(itemName);
        if (price != null) item.setPrice(price);
        if (type != null) {
            try {
                item.setType(ItemType.valueOf(type));
            } catch (IllegalArgumentException e) {
                throw new ItemNotFoundException("ITM-003", "Type must be VEG or NON_VEG");
            }
        }

        return itemDao.updateItem(item);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemEntity deleteMenuItem(String restaurantUuid, CustomerEntity owner, String itemUuid)
            throws RestaurantNotFoundException, AuthorizationFailedException, ItemNotFoundException {
        RestaurantEntity restaurant = getOwnedRestaurant(restaurantUuid, owner);

        ItemEntity item = itemDao.getItemById(itemUuid);
        if (item == null) {
            throw new ItemNotFoundException("INF-001", "Item not found");
        }

        restaurantDao.removeRestaurantItem(restaurant.getId(), item.getId());
        itemDao.deleteItem(item);
        return item;
    }
}
