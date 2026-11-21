package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.business.RestaurantOwnerService;
import com.upgrad.FoodOrderingApp.service.common.AppUtils;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class RestaurantOwnerController {

    @Autowired
    private RoleGuard roleGuard;

    @Autowired
    private RestaurantOwnerService ownerService;

    @RequestMapping(method = RequestMethod.POST, path = "/restaurant",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createRestaurant(
            @RequestHeader("authorization") final String headerParam,
            @RequestBody Map<String, Object> request)
            throws AuthorizationFailedException, RestaurantNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = roleGuard.authorizeRole(accessToken, "RESTAURANT_OWNER");

        String name = (String) request.get("restaurant_name");
        if (name == null || name.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "RST-002");
            error.put("message", "Restaurant name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        String photoUrl = (String) request.get("photo_url");
        Integer avgPrice = request.get("average_price_for_two") != null
                ? ((Number) request.get("average_price_for_two")).intValue() : null;

        @SuppressWarnings("unchecked")
        Map<String, Object> addressMap = (Map<String, Object>) request.get("address");
        String flat = "", locality = "", city = "", pincode = "", stateUuid = "";
        if (addressMap != null) {
            flat = (String) addressMap.getOrDefault("flat_buil_number", "");
            locality = (String) addressMap.getOrDefault("locality", "");
            city = (String) addressMap.getOrDefault("city", "");
            pincode = (String) addressMap.getOrDefault("pincode", "");
            stateUuid = (String) addressMap.getOrDefault("state_uuid", "");
        }

        RestaurantEntity restaurant = ownerService.createRestaurant(
                customer, name, photoUrl, avgPrice, flat, locality, city, pincode, stateUuid);

        Map<String, Object> response = new HashMap<>();
        response.put("id", restaurant.getUuid());
        response.put("restaurant_name", restaurant.getRestaurantName());
        response.put("status", "PENDING");
        response.put("message", "RESTAURANT CREATED — PENDING ADMIN APPROVAL");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/mine",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getMyRestaurants(
            @RequestHeader("authorization") final String headerParam)
            throws AuthorizationFailedException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = roleGuard.authorizeRole(accessToken, "RESTAURANT_OWNER");

        List<RestaurantEntity> restaurants = ownerService.getMyRestaurants(customer.getId());

        List<Map<String, Object>> list = restaurants.stream().map(r -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", r.getUuid());
            m.put("restaurant_name", r.getRestaurantName());
            m.put("status", r.getStatus());
            m.put("photo_url", r.getPhotoUrl());
            m.put("customer_rating", r.getCustomerRating());
            m.put("number_of_customers_rated", r.getNumberOfCustomersRated());
            m.put("average_price_for_two", r.getAveragePriceForTwo());
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("restaurants", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, path = "/restaurant/{restaurantUuid}/item",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addMenuItems(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid,
            @RequestBody Map<String, Object> request)
            throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = roleGuard.authorizeRole(accessToken, "RESTAURANT_OWNER");

        List<Map<String, Object>> itemsReq = (List<Map<String, Object>>) request.get("items");
        List<Map<String, Object>> createdItems = new ArrayList<>();

        if (itemsReq != null) {
            for (Map<String, Object> itemReq : itemsReq) {
                String itemName = (String) itemReq.get("item_name");
                if (itemName == null || itemName.isEmpty()) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("code", "ITM-001");
                    error.put("message", "Item name is required");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
                Integer price = itemReq.get("price") != null ? ((Number) itemReq.get("price")).intValue() : 0;
                if (price <= 0) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("code", "ITM-002");
                    error.put("message", "Price must be greater than 0");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
                String type = (String) itemReq.get("type");
                if (type == null) type = "VEG";

                ItemEntity item = ownerService.addMenuItem(restaurantUuid, customer, itemName, price, type);

                Map<String, Object> m = new HashMap<>();
                m.put("id", item.getUuid());
                m.put("item_name", item.getItemName());
                m.put("price", item.getPrice());
                m.put("type", item.getType().toString());
                createdItems.add(m);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("items", createdItems);
        response.put("message", "MENU ITEMS ADDED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/restaurant/{restaurantUuid}/item/{itemUuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> editMenuItem(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid,
            @PathVariable("itemUuid") final String itemUuid,
            @RequestBody Map<String, Object> request)
            throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = roleGuard.authorizeRole(accessToken, "RESTAURANT_OWNER");

        String itemName = (String) request.get("item_name");
        Integer price = request.get("price") != null ? ((Number) request.get("price")).intValue() : null;
        String type = (String) request.get("type");

        ItemEntity item = ownerService.editMenuItem(restaurantUuid, customer, itemUuid, itemName, price, type);

        Map<String, Object> response = new HashMap<>();
        response.put("id", item.getUuid());
        response.put("item_name", item.getItemName());
        response.put("price", item.getPrice());
        response.put("type", item.getType().toString());
        response.put("message", "MENU ITEM UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/restaurant/{restaurantUuid}/item/{itemUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteMenuItem(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid,
            @PathVariable("itemUuid") final String itemUuid)
            throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = roleGuard.authorizeRole(accessToken, "RESTAURANT_OWNER");

        ownerService.deleteMenuItem(restaurantUuid, customer, itemUuid);

        Map<String, Object> response = new HashMap<>();
        response.put("id", itemUuid);
        response.put("message", "MENU ITEM DELETED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
