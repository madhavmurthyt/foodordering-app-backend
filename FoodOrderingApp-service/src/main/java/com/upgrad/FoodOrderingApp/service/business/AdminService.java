package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    private static final Set<String> VALID_ROLES = Set.of("CUSTOMER", "RESTAURANT_OWNER", "DRIVER", "ADMIN");
    private static final Set<String> VALID_STATUSES = Set.of("ACTIVE", "SUSPENDED");

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RestaurantDao restaurantDao;

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
}
