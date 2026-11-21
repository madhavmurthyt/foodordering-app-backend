package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.AdminService;
import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.common.AppUtils;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleGuard roleGuard;

    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET, path = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getDashboard(
            @RequestHeader("authorization") final String headerParam) throws AuthorizationFailedException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        Map<String, Object> response = new HashMap<>();
        response.put("total_orders", adminService.getTotalOrders());
        response.put("total_revenue", adminService.getTotalRevenue());
        response.put("active_users", adminService.getActiveUsers());
        response.put("total_restaurants", adminService.getTotalRestaurants());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestHeader("authorization") final String headerParam,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws AuthorizationFailedException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        List<CustomerEntity> users = adminService.getUsers(page, size);
        long total = adminService.getTotalUsers();

        List<Map<String, Object>> userList = users.stream().map(u -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", u.getUuid());
            m.put("first_name", u.getFirstName());
            m.put("last_name", u.getLastName());
            m.put("email", u.getEmail());
            m.put("contact_number", u.getContactNumber());
            m.put("role", u.getRole());
            m.put("auth_provider", u.getAuthProvider());
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("users", userList);
        response.put("page", page);
        response.put("size", size);
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/users/{customerUuid}/role",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> changeUserRole(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable String customerUuid,
            @RequestBody Map<String, String> request) throws AuthorizationFailedException, CustomerNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        String newRole = request.get("role");
        if (newRole == null || newRole.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "USR-002");
            error.put("message", "Invalid role");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CustomerEntity customer;
        try {
            customer = adminService.changeUserRole(customerUuid, newRole);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "USR-002");
            error.put("message", "Invalid role");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", customer.getUuid());
        response.put("first_name", customer.getFirstName());
        response.put("role", customer.getRole());
        response.put("message", "USER ROLE UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRestaurants(
            @RequestHeader("authorization") final String headerParam,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws AuthorizationFailedException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        List<RestaurantEntity> restaurants = adminService.getRestaurants(page, size);
        long total = adminService.getTotalRestaurantsCount();

        List<Map<String, Object>> restaurantList = restaurants.stream().map(r -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", r.getUuid());
            m.put("restaurant_name", r.getRestaurantName());
            m.put("photo_url", r.getPhotoUrl());
            m.put("customer_rating", r.getCustomerRating());
            m.put("average_price_for_two", r.getAveragePriceForTwo());
            m.put("number_of_customers_rated", r.getNumberOfCustomersRated());
            m.put("status", r.getStatus());
            if (r.getAddress() != null) {
                Map<String, Object> addr = new HashMap<>();
                addr.put("id", r.getAddress().getUuid());
                addr.put("flat_building_name", r.getAddress().getFlatBuilNo());
                addr.put("locality", r.getAddress().getLocality());
                addr.put("city", r.getAddress().getCity());
                addr.put("pincode", r.getAddress().getPincode());
                if (r.getAddress().getState() != null) {
                    addr.put("state", r.getAddress().getState().getStateName());
                }
                m.put("address", addr);
            }
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("restaurants", restaurantList);
        response.put("page", page);
        response.put("size", size);
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/restaurants/{restaurantUuid}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> changeRestaurantStatus(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable String restaurantUuid,
            @RequestBody Map<String, String> request) throws AuthorizationFailedException, RestaurantNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        String status = request.get("status");
        if (status == null || status.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "RST-001");
            error.put("message", "Invalid status");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        RestaurantEntity restaurant;
        try {
            restaurant = adminService.changeRestaurantStatus(restaurantUuid, status);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "RST-001");
            error.put("message", "Invalid status");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", restaurant.getUuid());
        response.put("restaurant_name", restaurant.getRestaurantName());
        response.put("status", restaurant.getStatus());
        response.put("message", "RESTAURANT STATUS UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestHeader("authorization") final String headerParam,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws AuthorizationFailedException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        List<OrderEntity> orders = adminService.getOrders(page, size);
        long total = adminService.getTotalOrdersCount();

        List<Map<String, Object>> orderList = orders.stream().map(o -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", o.getUuid());
            m.put("customer_name", o.getCustomer().getFirstName() + " " +
                (o.getCustomer().getLastName() != null ? o.getCustomer().getLastName() : ""));
            m.put("restaurant_name", o.getRestaurant().getRestaurantName());
            m.put("bill", o.getBill());
            m.put("discount", o.getDiscount());
            m.put("date", o.getDate().toString());
            if (o.getPayment() != null) {
                m.put("payment", o.getPayment().getPaymentName());
            }
            if (o.getAddress() != null) {
                String addr = o.getAddress().getFlatBuilNo() + ", " +
                    o.getAddress().getLocality() + ", " +
                    o.getAddress().getCity() + " " +
                    o.getAddress().getPincode();
                m.put("address", addr);
            }
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderList);
        response.put("page", page);
        response.put("size", size);
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, path = "/restaurants",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createRestaurant(
            @RequestHeader("authorization") final String headerParam,
            @RequestBody Map<String, Object> request) throws AuthorizationFailedException, RestaurantNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        String name = (String) request.get("restaurant_name");
        if (name == null || name.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "RST-002");
            error.put("message", "Restaurant name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        String photoUrl = (String) request.get("photo_url");
        Integer avgPrice = request.get("average_price_for_two") != null
                ? ((Number) request.get("average_price_for_two")).intValue() : 0;

        Map<String, String> addr = (Map<String, String>) request.get("address");
        String flatBuildingName = addr != null ? addr.get("flat_building_name") : "";
        String locality = addr != null ? addr.get("locality") : "";
        String city = addr != null ? addr.get("city") : "";
        String pincode = addr != null ? addr.get("pincode") : "";
        String stateUuid = addr != null ? addr.get("state_uuid") : "";

        List<String> categoryUuids = (List<String>) request.get("category_uuids");

        RestaurantEntity restaurant = adminService.createRestaurant(
                name, photoUrl, avgPrice, flatBuildingName, locality, city, pincode, stateUuid, categoryUuids);

        Map<String, Object> response = new HashMap<>();
        response.put("id", restaurant.getUuid());
        response.put("restaurant_name", restaurant.getRestaurantName());
        response.put("status", restaurant.getStatus());
        response.put("message", "RESTAURANT CREATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, path = "/restaurants/{restaurantUuid}/items",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addMenuItems(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable String restaurantUuid,
            @RequestBody Map<String, Object> request) throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        if (items == null || items.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "ITM-001");
            error.put("message", "Item name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        List<Map<String, Object>> createdItems = new ArrayList<>();
        for (Map<String, Object> itemReq : items) {
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
            ItemEntity item = adminService.addMenuItem(restaurantUuid, itemName, price, type);

            Map<String, Object> m = new HashMap<>();
            m.put("id", item.getUuid());
            m.put("item_name", item.getItemName());
            m.put("price", item.getPrice());
            m.put("type", item.getType().toString());
            createdItems.add(m);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("items", createdItems);
        response.put("message", createdItems.size() + " ITEMS ADDED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/restaurants/{restaurantUuid}/items/{itemUuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> editMenuItem(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable String restaurantUuid,
            @PathVariable String itemUuid,
            @RequestBody Map<String, Object> request) throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        String itemName = (String) request.get("item_name");
        Integer price = request.get("price") != null ? ((Number) request.get("price")).intValue() : null;
        String type = (String) request.get("type");

        ItemEntity item = adminService.editMenuItem(restaurantUuid, itemUuid, itemName, price, type);

        Map<String, Object> response = new HashMap<>();
        response.put("id", item.getUuid());
        response.put("item_name", item.getItemName());
        response.put("price", item.getPrice());
        response.put("type", item.getType().toString());
        response.put("message", "ITEM UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/restaurants/{restaurantUuid}/items/{itemUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteMenuItem(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable String restaurantUuid,
            @PathVariable String itemUuid) throws AuthorizationFailedException, RestaurantNotFoundException, ItemNotFoundException {
        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        ItemEntity item = adminService.deleteMenuItem(restaurantUuid, itemUuid);

        Map<String, Object> response = new HashMap<>();
        response.put("id", item.getUuid());
        response.put("message", "ITEM DELETED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
