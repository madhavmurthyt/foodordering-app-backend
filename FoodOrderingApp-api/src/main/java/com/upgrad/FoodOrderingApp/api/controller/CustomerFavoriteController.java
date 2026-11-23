package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.CustomerService;
import com.upgrad.FoodOrderingApp.service.common.AppUtils;
import com.upgrad.FoodOrderingApp.service.dao.CustomerFavoriteDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerFavoriteEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CustomerFavoriteController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerFavoriteDao favoriteDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @RequestMapping(method = RequestMethod.GET, path = "/customer/favorites",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFavorites(
            @RequestHeader("authorization") final String headerParam)
            throws AuthorizationFailedException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = customerService.getCustomer(accessToken);

        List<CustomerFavoriteEntity> favorites = favoriteDao.getFavoritesByCustomer(customer.getId());

        List<Map<String, Object>> list = favorites.stream().map(f -> {
            RestaurantEntity r = f.getRestaurant();
            Map<String, Object> m = new HashMap<>();
            m.put("id", r.getUuid());
            m.put("restaurant_name", r.getRestaurantName());
            m.put("photo_url", r.getPhotoUrl());
            m.put("customer_rating", r.getCustomerRating());
            m.put("average_price_for_two", r.getAveragePriceForTwo());
            m.put("number_of_customers_rated", r.getNumberOfCustomersRated());
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("restaurants", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customer/favorites/{restaurantUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Map<String, Object>> addFavorite(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid)
            throws AuthorizationFailedException, RestaurantNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = customerService.getCustomer(accessToken);

        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        CustomerFavoriteEntity existing = favoriteDao.findFavorite(customer.getId(), restaurant.getId());
        if (existing != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", restaurant.getUuid());
            response.put("message", "Restaurant is already in your favorites");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomerFavoriteEntity fav = new CustomerFavoriteEntity();
        fav.setCustomer(customer);
        fav.setRestaurant(restaurant);
        fav.setCreatedAt(LocalDateTime.now());
        favoriteDao.save(fav);

        Map<String, Object> response = new HashMap<>();
        response.put("id", restaurant.getUuid());
        response.put("message", "RESTAURANT ADDED TO FAVORITES");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/customer/favorites/{restaurantUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Map<String, Object>> removeFavorite(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid)
            throws AuthorizationFailedException, RestaurantNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = customerService.getCustomer(accessToken);

        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        CustomerFavoriteEntity existing = favoriteDao.findFavorite(customer.getId(), restaurant.getId());
        if (existing == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", restaurant.getUuid());
            response.put("message", "Restaurant is not in your favorites");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        favoriteDao.delete(existing);

        Map<String, Object> response = new HashMap<>();
        response.put("id", restaurant.getUuid());
        response.put("message", "RESTAURANT REMOVED FROM FAVORITES");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
