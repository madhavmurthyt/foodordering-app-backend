package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.CustomerService;
import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.InviteTokenDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.InviteTokenEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InviteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleGuard mockRoleGuard;

    @MockBean
    private CustomerService mockCustomerService;

    @MockBean
    private CustomerDao mockCustomerDao;

    @MockBean
    private RestaurantDao mockRestaurantDao;

    @MockBean
    private InviteTokenDao mockInviteTokenDao;

    private CustomerEntity buildAdmin() {
        CustomerEntity admin = new CustomerEntity();
        admin.setId(1);
        admin.setUuid(UUID.randomUUID().toString());
        admin.setFirstName("Admin");
        admin.setRole("ADMIN");
        admin.setEmail("admin@test.com");
        admin.setContactNumber("9999999999");
        return admin;
    }

    private CustomerEntity buildCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(2);
        customer.setUuid(UUID.randomUUID().toString());
        customer.setFirstName("Customer");
        customer.setRole("CUSTOMER");
        customer.setEmail("customer@test.com");
        customer.setContactNumber("8888888888");
        return customer;
    }

    private RestaurantEntity buildRestaurant() {
        RestaurantEntity r = new RestaurantEntity();
        r.setId(1);
        r.setUuid(UUID.randomUUID().toString());
        r.setRestaurantName("Pizza Palace");
        r.setStatus("ACTIVE");
        return r;
    }

    private InviteTokenEntity buildValidInvite(RestaurantEntity restaurant) {
        InviteTokenEntity invite = new InviteTokenEntity();
        invite.setId(1);
        invite.setUuid(UUID.randomUUID().toString());
        invite.setToken("valid-token-123");
        invite.setRestaurant(restaurant);
        invite.setExpiresAt(LocalDateTime.now().plusDays(7));
        invite.setUsedAt(null);
        return invite;
    }

    // ==================== POST /admin/restaurants/{uuid}/invite ====================

    @Test
    public void shouldGenerateInviteLinkForAdmin() throws Exception {
        CustomerEntity admin = buildAdmin();
        RestaurantEntity restaurant = buildRestaurant();

        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(admin);
        when(mockRestaurantDao.getRestaurantByUuid(restaurant.getUuid())).thenReturn(restaurant);
        when(mockInviteTokenDao.save(any(InviteTokenEntity.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/admin/restaurants/{uuid}/invite", restaurant.getUuid())
                        .header("authorization", "Bearer admin-token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.restaurant_name").value("Pizza Palace"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.invite_url").exists())
                .andExpect(jsonPath("$.message").value("INVITE LINK GENERATED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturn403WhenNonAdminGeneratesInvite() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(post("/admin/restaurants/{uuid}/invite", UUID.randomUUID().toString())
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn404WhenRestaurantNotFoundForInvite() throws Exception {
        CustomerEntity admin = buildAdmin();
        when(mockRoleGuard.authorizeRole(anyString(), eq("ADMIN"))).thenReturn(admin);
        when(mockRestaurantDao.getRestaurantByUuid(anyString())).thenReturn(null);

        mockMvc.perform(post("/admin/restaurants/{uuid}/invite", UUID.randomUUID().toString())
                        .header("authorization", "Bearer admin-token"))
                .andExpect(status().isNotFound());
    }

    // ==================== GET /invite/{token} ====================

    @Test
    public void shouldReturnValidStatusForValidToken() throws Exception {
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);

        when(mockInviteTokenDao.findByToken("valid-token-123")).thenReturn(invite);

        mockMvc.perform(get("/invite/{token}", "valid-token-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("valid"))
                .andExpect(jsonPath("$.restaurant_name").value("Pizza Palace"));
    }

    @Test
    public void shouldReturnInvalidStatusForUnknownToken() throws Exception {
        when(mockInviteTokenDao.findByToken("bad-token")).thenReturn(null);

        mockMvc.perform(get("/invite/{token}", "bad-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("invalid"))
                .andExpect(jsonPath("$.message").value("Invalid invite link"));
    }

    @Test
    public void shouldReturnExpiredStatusForExpiredToken() throws Exception {
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);
        invite.setExpiresAt(LocalDateTime.now().minusDays(1));

        when(mockInviteTokenDao.findByToken("expired-token")).thenReturn(invite);

        mockMvc.perform(get("/invite/{token}", "expired-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("expired"))
                .andExpect(jsonPath("$.message").value("This invite link has expired"));
    }

    @Test
    public void shouldReturnUsedStatusForUsedToken() throws Exception {
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);
        invite.setUsedAt(LocalDateTime.now().minusDays(1));

        when(mockInviteTokenDao.findByToken("used-token")).thenReturn(invite);

        mockMvc.perform(get("/invite/{token}", "used-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("used"))
                .andExpect(jsonPath("$.message").value("This invite link has already been used"));
    }

    // ==================== POST /invite/{token}/claim ====================

    @Test
    public void shouldClaimInviteSuccessfully() throws Exception {
        CustomerEntity customer = buildCustomer();
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);

        when(mockCustomerService.getCustomer(anyString())).thenReturn(customer);
        when(mockInviteTokenDao.findByToken("valid-token-123")).thenReturn(invite);
        when(mockInviteTokenDao.update(any(InviteTokenEntity.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/invite/{token}/claim", "valid-token-123")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurant_name").value("Pizza Palace"))
                .andExpect(jsonPath("$.role").value("RESTAURANT_OWNER"))
                .andExpect(jsonPath("$.message").value("YOU ARE NOW THE OWNER OF THIS RESTAURANT"));
    }

    @Test
    public void shouldReturn404WhenClaimingInvalidToken() throws Exception {
        CustomerEntity customer = buildCustomer();
        when(mockCustomerService.getCustomer(anyString())).thenReturn(customer);
        when(mockInviteTokenDao.findByToken("bad-token")).thenReturn(null);

        mockMvc.perform(post("/invite/{token}/claim", "bad-token")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("INV-004"));
    }

    @Test
    public void shouldReturn400WhenClaimingExpiredToken() throws Exception {
        CustomerEntity customer = buildCustomer();
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);
        invite.setExpiresAt(LocalDateTime.now().minusDays(1));

        when(mockCustomerService.getCustomer(anyString())).thenReturn(customer);
        when(mockInviteTokenDao.findByToken("expired-token")).thenReturn(invite);

        mockMvc.perform(post("/invite/{token}/claim", "expired-token")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INV-001"))
                .andExpect(jsonPath("$.message").value("Invite link has expired"));
    }

    @Test
    public void shouldReturn400WhenClaimingUsedToken() throws Exception {
        CustomerEntity customer = buildCustomer();
        RestaurantEntity restaurant = buildRestaurant();
        InviteTokenEntity invite = buildValidInvite(restaurant);
        invite.setUsedAt(LocalDateTime.now().minusHours(1));

        when(mockCustomerService.getCustomer(anyString())).thenReturn(customer);
        when(mockInviteTokenDao.findByToken("used-token")).thenReturn(invite);

        mockMvc.perform(post("/invite/{token}/claim", "used-token")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INV-002"));
    }

    @Test
    public void shouldReturn400WhenRestaurantAlreadyHasOwner() throws Exception {
        CustomerEntity customer = buildCustomer();
        CustomerEntity existingOwner = buildAdmin();
        RestaurantEntity restaurant = buildRestaurant();
        restaurant.setOwner(existingOwner);
        InviteTokenEntity invite = buildValidInvite(restaurant);

        when(mockCustomerService.getCustomer(anyString())).thenReturn(customer);
        when(mockInviteTokenDao.findByToken("valid-token-123")).thenReturn(invite);

        mockMvc.perform(post("/invite/{token}/claim", "valid-token-123")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INV-003"))
                .andExpect(jsonPath("$.message").value("Restaurant already has an owner"));
    }

    @Test
    public void shouldReturn403WhenUnauthenticatedUserClaims() throws Exception {
        when(mockCustomerService.getCustomer(anyString()))
                .thenThrow(new AuthorizationFailedException("ATHR-001", "Customer is not logged in"));

        mockMvc.perform(post("/invite/{token}/claim", "valid-token-123")
                        .header("authorization", "Bearer invalid-token"))
                .andExpect(status().isForbidden());
    }
}
