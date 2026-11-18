package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.AdminService;
import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleGuard mockRoleGuard;

    @MockBean
    private AdminService mockAdminService;

    private CustomerEntity buildAdmin() {
        CustomerEntity admin = new CustomerEntity();
        admin.setUuid(UUID.randomUUID().toString());
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole("ADMIN");
        admin.setEmail("admin@test.com");
        admin.setContactNumber("9999999999");
        return admin;
    }

    // ==================== GET /admin/dashboard ====================

    @Test
    public void shouldReturnDashboardForAdmin() throws Exception {
        CustomerEntity admin = buildAdmin();
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(admin);
        when(mockAdminService.getTotalOrders()).thenReturn(42L);
        when(mockAdminService.getTotalRevenue()).thenReturn(12345.67);
        when(mockAdminService.getActiveUsers()).thenReturn(10L);
        when(mockAdminService.getTotalRestaurants()).thenReturn(5L);

        mockMvc
            .perform(get("/admin/dashboard")
                .header("authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("total_orders").value(42))
            .andExpect(jsonPath("total_revenue").value(12345.67))
            .andExpect(jsonPath("active_users").value(10))
            .andExpect(jsonPath("total_restaurants").value(5));
    }

    @Test
    public void shouldReturn403WhenNonAdminAccessesDashboard() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(get("/admin/dashboard")
                .header("authorization", "Bearer customer-token"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("code").value("AUT-001"));
    }

    // ==================== GET /admin/users ====================

    @Test
    public void shouldReturnUsersListForAdmin() throws Exception {
        CustomerEntity admin = buildAdmin();
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(admin);

        CustomerEntity user = new CustomerEntity();
        user.setUuid(UUID.randomUUID().toString());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@test.com");
        user.setContactNumber("1234567890");
        user.setRole("CUSTOMER");

        when(mockAdminService.getUsers(0, 20)).thenReturn(List.of(user));
        when(mockAdminService.getTotalUsers()).thenReturn(1L);

        mockMvc
            .perform(get("/admin/users")
                .header("authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("users[0].first_name").value("John"))
            .andExpect(jsonPath("users[0].role").value("CUSTOMER"))
            .andExpect(jsonPath("total").value(1));
    }

    @Test
    public void shouldReturn403WhenNonAdminAccessesUsers() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(get("/admin/users")
                .header("authorization", "Bearer customer-token"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("code").value("AUT-001"));
    }

    @Test
    public void shouldReturnEmptyUsersWhenNoneExist() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());
        when(mockAdminService.getUsers(0, 20)).thenReturn(Collections.emptyList());
        when(mockAdminService.getTotalUsers()).thenReturn(0L);

        mockMvc
            .perform(get("/admin/users")
                .header("authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("users").isEmpty())
            .andExpect(jsonPath("total").value(0));
    }

    // ==================== PUT /admin/users/{uuid}/role ====================

    @Test
    public void shouldChangeUserRole() throws Exception {
        CustomerEntity admin = buildAdmin();
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(admin);

        CustomerEntity updatedUser = new CustomerEntity();
        updatedUser.setUuid("user-uuid-123");
        updatedUser.setFirstName("John");
        updatedUser.setRole("RESTAURANT_OWNER");
        when(mockAdminService.changeUserRole(eq("user-uuid-123"), eq("RESTAURANT_OWNER")))
            .thenReturn(updatedUser);

        mockMvc
            .perform(put("/admin/users/user-uuid-123/role")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"role\":\"RESTAURANT_OWNER\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("role").value("RESTAURANT_OWNER"))
            .andExpect(jsonPath("message").value("USER ROLE UPDATED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturn403WhenNonAdminChangesRole() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(put("/admin/users/some-uuid/role")
                .header("authorization", "Bearer customer-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"role\":\"ADMIN\"}"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("code").value("AUT-001"));
    }

    @Test
    public void shouldReturnBadRequestForEmptyRole() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());

        mockMvc
            .perform(put("/admin/users/some-uuid/role")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"role\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value("USR-002"));
    }

    @Test
    public void shouldReturnBadRequestForInvalidRole() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());
        when(mockAdminService.changeUserRole(anyString(), eq("INVALID_ROLE")))
            .thenThrow(new IllegalArgumentException("Invalid role"));

        mockMvc
            .perform(put("/admin/users/some-uuid/role")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"role\":\"INVALID_ROLE\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value("USR-002"));
    }

    @Test
    public void shouldReturn404WhenUserNotFound() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());
        when(mockAdminService.changeUserRole(eq("bad-uuid"), anyString()))
            .thenThrow(new CustomerNotFoundException("USR-001", "User not found"));

        mockMvc
            .perform(put("/admin/users/bad-uuid/role")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"role\":\"ADMIN\"}"))
            .andExpect(status().isNotFound());
    }

    // ==================== GET /admin/restaurants ====================

    @Test
    public void shouldReturnRestaurantsListForAdmin() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());
        when(mockAdminService.getRestaurants(0, 20)).thenReturn(Collections.emptyList());
        when(mockAdminService.getTotalRestaurantsCount()).thenReturn(0L);

        mockMvc
            .perform(get("/admin/restaurants")
                .header("authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("restaurants").isEmpty())
            .andExpect(jsonPath("total").value(0));
    }

    @Test
    public void shouldReturn403WhenNonAdminAccessesRestaurants() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(get("/admin/restaurants")
                .header("authorization", "Bearer customer-token"))
            .andExpect(status().isForbidden());
    }

    // ==================== PUT /admin/restaurants/{uuid}/status ====================

    @Test
    public void shouldChangeRestaurantStatus() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setUuid("rest-uuid");
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setStatus("SUSPENDED");
        when(mockAdminService.changeRestaurantStatus(eq("rest-uuid"), eq("SUSPENDED")))
            .thenReturn(restaurant);

        mockMvc
            .perform(put("/admin/restaurants/rest-uuid/status")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"status\":\"SUSPENDED\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("status").value("SUSPENDED"))
            .andExpect(jsonPath("message").value("RESTAURANT STATUS UPDATED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturnBadRequestForEmptyStatus() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());

        mockMvc
            .perform(put("/admin/restaurants/rest-uuid/status")
                .header("authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"status\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value("RST-001"));
    }

    @Test
    public void shouldReturn403WhenNonAdminChangesRestaurantStatus() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(put("/admin/restaurants/rest-uuid/status")
                .header("authorization", "Bearer customer-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"status\":\"ACTIVE\"}"))
            .andExpect(status().isForbidden());
    }

    // ==================== GET /admin/orders ====================

    @Test
    public void shouldReturnOrdersListForAdmin() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(buildAdmin());
        when(mockAdminService.getOrders(0, 20)).thenReturn(Collections.emptyList());
        when(mockAdminService.getTotalOrdersCount()).thenReturn(0L);

        mockMvc
            .perform(get("/admin/orders")
                .header("authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("orders").isEmpty())
            .andExpect(jsonPath("total").value(0));
    }

    @Test
    public void shouldReturn403WhenNonAdminAccessesOrders() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
            .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc
            .perform(get("/admin/orders")
                .header("authorization", "Bearer customer-token"))
            .andExpect(status().isForbidden());
    }
}
