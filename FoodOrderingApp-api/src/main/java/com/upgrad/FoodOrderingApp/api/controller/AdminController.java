package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.AdminService;
import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.common.AppUtils;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
