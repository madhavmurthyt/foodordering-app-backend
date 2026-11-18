package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleGuard {

    @Autowired
    private CustomerService customerService;

    public CustomerEntity authorizeRole(final String accessToken, final String requiredRole) throws AuthorizationFailedException {
        CustomerEntity customer = customerService.getCustomer(accessToken);
        if (!requiredRole.equals(customer.getRole())) {
            throw new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation");
        }
        return customer;
    }
}
