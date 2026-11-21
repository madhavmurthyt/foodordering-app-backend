package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.CustomerService;
import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.common.AppUtils;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.InviteTokenDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.InviteTokenEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class InviteController {

    @Autowired
    private RoleGuard roleGuard;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private InviteTokenDao inviteTokenDao;

    @RequestMapping(method = RequestMethod.POST, path = "/admin/restaurants/{restaurantUuid}/invite",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Map<String, Object>> generateInvite(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("restaurantUuid") final String restaurantUuid)
            throws AuthorizationFailedException, RestaurantNotFoundException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        roleGuard.authorizeRole(accessToken, "ADMIN");

        RestaurantEntity restaurant = restaurantDao.getRestaurantByUuid(restaurantUuid);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("RNF-001", "Restaurant not found");
        }

        InviteTokenEntity invite = new InviteTokenEntity();
        invite.setUuid(UUID.randomUUID().toString());
        invite.setToken(UUID.randomUUID().toString());
        invite.setRestaurant(restaurant);
        invite.setExpiresAt(LocalDateTime.now().plusDays(7));
        inviteTokenDao.save(invite);

        Map<String, Object> response = new HashMap<>();
        response.put("token", invite.getToken());
        response.put("restaurant_name", restaurant.getRestaurantName());
        response.put("invite_url", "/invite/" + invite.getToken());
        response.put("expires_at", invite.getExpiresAt().toString());
        response.put("message", "INVITE LINK GENERATED SUCCESSFULLY");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/invite/{token}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> validateInvite(
            @PathVariable("token") final String token) {

        Map<String, Object> response = new HashMap<>();
        InviteTokenEntity invite = inviteTokenDao.findByToken(token);

        if (invite == null) {
            response.put("status", "invalid");
            response.put("message", "Invalid invite link");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (invite.getUsedAt() != null) {
            response.put("status", "used");
            response.put("message", "This invite link has already been used");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            response.put("status", "expired");
            response.put("message", "This invite link has expired");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("restaurant_name", invite.getRestaurant().getRestaurantName());
        response.put("status", "valid");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invite/{token}/claim",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Map<String, Object>> claimInvite(
            @RequestHeader("authorization") final String headerParam,
            @PathVariable("token") final String token)
            throws AuthorizationFailedException {

        final String accessToken = AppUtils.getBearerAuthToken(headerParam);
        CustomerEntity customer = customerService.getCustomer(accessToken);

        Map<String, Object> error = new HashMap<>();

        InviteTokenEntity invite = inviteTokenDao.findByToken(token);
        if (invite == null) {
            error.put("code", "INV-004");
            error.put("message", "Invalid invite link");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            error.put("code", "INV-001");
            error.put("message", "Invite link has expired");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (invite.getUsedAt() != null) {
            error.put("code", "INV-002");
            error.put("message", "Invite link has already been used");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        RestaurantEntity restaurant = invite.getRestaurant();
        if (restaurant.getOwner() != null) {
            error.put("code", "INV-003");
            error.put("message", "Restaurant already has an owner");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        customer.setRole("RESTAURANT_OWNER");
        customerDao.updateCustomer(customer);

        restaurant.setOwner(customer);
        restaurantDao.updateRestaurant(restaurant);

        invite.setUsedAt(LocalDateTime.now());
        invite.setUsedBy(customer);
        inviteTokenDao.update(invite);

        Map<String, Object> response = new HashMap<>();
        response.put("restaurant_id", restaurant.getUuid());
        response.put("restaurant_name", restaurant.getRestaurantName());
        response.put("role", "RESTAURANT_OWNER");
        response.put("message", "YOU ARE NOW THE OWNER OF THIS RESTAURANT");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
