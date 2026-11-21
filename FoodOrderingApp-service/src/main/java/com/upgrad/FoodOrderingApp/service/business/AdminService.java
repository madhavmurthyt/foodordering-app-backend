package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.common.ItemType;
import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminService {

    private static final Set<String> VALID_ROLES = Set.of("CUSTOMER", "RESTAURANT_OWNER", "DRIVER", "ADMIN");
    private static final Set<String> VALID_STATUSES = Set.of("PENDING", "ACTIVE", "SUSPENDED");

    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager entityManager;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AddressDao addressDao;

    public long getTotalOrders() {
        return orderDao.countAllOrders();
    }

    public double getTotalRevenue() {
        Double revenue = orderDao.sumAllRevenue();
        return revenue != null ? revenue : 0.0;
    }

    public long getActiveUsers() {
        return customerDao.countAllCustomers();
    }

    public long getTotalRestaurants() {
        return restaurantDao.countAllRestaurants();
    }

    public List<CustomerEntity> getUsers(int page, int size) {
        return customerDao.getAllCustomers(page, size);
    }

    public long getTotalUsers() {
        return customerDao.countAllCustomers();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity changeUserRole(String customerUuid, String newRole) throws CustomerNotFoundException {
        if (!VALID_ROLES.contains(newRole)) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }

        CustomerEntity customer = customerDao.getCustomerByUuid(customerUuid);
        if (customer == null) {
            throw new CustomerNotFoundException("USR-001", "User not found");
        }

        customer.setRole(newRole);
        return customerDao.updateCustomer(customer);
    }

    public List<RestaurantEntity> getRestaurants(int page, int size) {
        return restaurantDao.getAllRestaurants(page, size);
    }

    public long getTotalRestaurantsCount() {
        return restaurantDao.countAllRestaurants();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity changeRestaurantStatus(String restaurantUuid, String status) throws RestaurantNotFoundException {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        restaurant.setStatus(status);
        return restaurantDao.updateRestaurant(restaurant);
    }

    public List<OrderEntity> getOrders(int page, int size) {
        return orderDao.getAllOrders(page, size);
    }

    public long getTotalOrdersCount() {
        return orderDao.countAllOrders();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity createRestaurant(String name, String photoUrl, Integer avgPrice,
                                              String flatBuildingName, String locality, String city,
                                              String pincode, String stateUuid, List<String> categoryUuids) throws RestaurantNotFoundException {
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
        restaurant.setStatus("ACTIVE");
        restaurant.setAddress(address);
        restaurantDao.saveRestaurant(restaurant);

        if (categoryUuids != null) {
            for (String catUuid : categoryUuids) {
                CategoryEntity category = categoryDao.getCategoryById(catUuid);
                if (category != null) {
                    RestaurantCategoryEntity rc = new RestaurantCategoryEntity();
                    rc.setRestaurantEntity(restaurant);
                    rc.setCategoryEntity(category);
                    entityManager.persist(rc);
                }
            }
        }

        return restaurant;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemEntity addMenuItem(String restaurantUuid, String itemName, Integer price, String type)
            throws RestaurantNotFoundException, ItemNotFoundException {
        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

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
    public ItemEntity editMenuItem(String restaurantUuid, String itemUuid, String itemName, Integer price, String type)
            throws RestaurantNotFoundException, ItemNotFoundException {
        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        ItemEntity item = itemDao.getItemById(itemUuid);
        if (item == null) {
            throw new ItemNotFoundException("INF-001", "Item not found");
        }

        if (itemName != null && !itemName.isEmpty()) {
            item.setItemName(itemName);
        }
        if (price != null) {
            item.setPrice(price);
        }
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
    public ItemEntity deleteMenuItem(String restaurantUuid, String itemUuid)
            throws RestaurantNotFoundException, ItemNotFoundException {
        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        ItemEntity item = itemDao.getItemById(itemUuid);
        if (item == null) {
            throw new ItemNotFoundException("INF-001", "Item not found");
        }

        restaurantDao.removeRestaurantItem(restaurant.getId(), item.getId());
        itemDao.deleteItem(item);

        return item;
    }
}
