package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.business.RoleGuard;
import com.upgrad.FoodOrderingApp.service.business.RestaurantOwnerService;
import com.upgrad.FoodOrderingApp.service.common.ItemType;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantOwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleGuard mockRoleGuard;

    @MockBean
    private RestaurantOwnerService mockOwnerService;

    private CustomerEntity buildOwner() {
        CustomerEntity owner = new CustomerEntity();
        owner.setId(1);
        owner.setUuid(UUID.randomUUID().toString());
        owner.setFirstName("Owner");
        owner.setRole("RESTAURANT_OWNER");
        owner.setEmail("owner@test.com");
        owner.setContactNumber("7777777777");
        return owner;
    }

    private RestaurantEntity buildRestaurant(CustomerEntity owner) {
        RestaurantEntity r = new RestaurantEntity();
        r.setId(1);
        r.setUuid(UUID.randomUUID().toString());
        r.setRestaurantName("My Restaurant");
        r.setStatus("PENDING");
        r.setCustomerRating(0);
        r.setNumberCustomersRated(0);
        r.setAvgPrice(300);
        r.setOwner(owner);
        return r;
    }

    private ItemEntity buildItem(String name, int price, ItemType type) {
        ItemEntity item = new ItemEntity();
        item.setUuid(UUID.randomUUID().toString());
        item.setItemName(name);
        item.setPrice(price);
        item.setType(type);
        return item;
    }

    // ==================== POST /restaurant (owner creates) ====================

    @Test
    public void shouldCreateRestaurantForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        RestaurantEntity restaurant = buildRestaurant(owner);

        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.createRestaurant(any(), eq("My Restaurant"), anyString(),
                anyInt(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(restaurant);

        String body = """
                {
                    "restaurant_name": "My Restaurant",
                    "photo_url": "https://example.com/photo.jpg",
                    "average_price_for_two": 300,
                    "address": {
                        "flat_buil_number": "123",
                        "locality": "MG Road",
                        "city": "Bangalore",
                        "pincode": "560001",
                        "state_uuid": "state-uuid"
                    }
                }
                """;

        mockMvc.perform(post("/restaurant")
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.restaurant_name").value("My Restaurant"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.message").value("RESTAURANT CREATED — PENDING ADMIN APPROVAL"));
    }

    @Test
    public void shouldReturn400WhenRestaurantNameMissing() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);

        mockMvc.perform(post("/restaurant")
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"average_price_for_two\": 300}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("RST-002"));
    }

    @Test
    public void shouldReturn403WhenNonOwnerCreatesRestaurant() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(post("/restaurant")
                        .header("authorization", "Bearer customer-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"restaurant_name\": \"Test\"}"))
                .andExpect(status().isForbidden());
    }

    // ==================== GET /restaurant/mine ====================

    @Test
    public void shouldReturnOwnerRestaurants() throws Exception {
        CustomerEntity owner = buildOwner();
        RestaurantEntity r1 = buildRestaurant(owner);
        r1.setRestaurantName("Restaurant A");
        RestaurantEntity r2 = buildRestaurant(owner);
        r2.setRestaurantName("Restaurant B");
        r2.setStatus("ACTIVE");

        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.getMyRestaurants(owner.getId())).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/restaurant/mine")
                        .header("authorization", "Bearer owner-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurants.length()").value(2))
                .andExpect(jsonPath("$.restaurants[0].restaurant_name").value("Restaurant A"))
                .andExpect(jsonPath("$.restaurants[1].restaurant_name").value("Restaurant B"));
    }

    @Test
    public void shouldReturnEmptyListWhenOwnerHasNoRestaurants() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.getMyRestaurants(owner.getId())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/restaurant/mine")
                        .header("authorization", "Bearer owner-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurants.length()").value(0));
    }

    @Test
    public void shouldReturn403WhenNonOwnerGetsMyRestaurants() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(get("/restaurant/mine")
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isForbidden());
    }

    // ==================== POST /restaurant/{uuid}/item (owner adds items) ====================

    @Test
    public void shouldAddMenuItemsForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        ItemEntity item = buildItem("Pasta", 250, ItemType.VEG);

        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.addMenuItem(anyString(), any(), eq("Pasta"), eq(250), eq("VEG")))
                .thenReturn(item);

        String body = """
                {
                    "items": [
                        {"item_name": "Pasta", "price": 250, "type": "VEG"}
                    ]
                }
                """;

        mockMvc.perform(post("/restaurant/{uuid}/item", UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items[0].item_name").value("Pasta"))
                .andExpect(jsonPath("$.items[0].price").value(250))
                .andExpect(jsonPath("$.message").value("MENU ITEMS ADDED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturn400WhenItemNameMissingForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);

        String body = """
                {
                    "items": [
                        {"price": 250, "type": "VEG"}
                    ]
                }
                """;

        mockMvc.perform(post("/restaurant/{uuid}/item", UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ITM-001"));
    }

    @Test
    public void shouldReturn400WhenPriceZeroForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);

        String body = """
                {
                    "items": [
                        {"item_name": "Test", "price": 0, "type": "VEG"}
                    ]
                }
                """;

        mockMvc.perform(post("/restaurant/{uuid}/item", UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ITM-002"));
    }

    @Test
    public void shouldReturn403WhenNonOwnerAddsItem() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(post("/restaurant/{uuid}/item", UUID.randomUUID().toString())
                        .header("authorization", "Bearer customer-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\": [{\"item_name\": \"X\", \"price\": 100}]}"))
                .andExpect(status().isForbidden());
    }

    // ==================== PUT /restaurant/{uuid}/item/{itemUuid} ====================

    @Test
    public void shouldEditMenuItemForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        ItemEntity updated = buildItem("Updated Pasta", 300, ItemType.VEG);

        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.editMenuItem(anyString(), any(), anyString(), eq("Updated Pasta"), eq(300), eq("VEG")))
                .thenReturn(updated);

        String body = """
                {"item_name": "Updated Pasta", "price": 300, "type": "VEG"}
                """;

        mockMvc.perform(put("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item_name").value("Updated Pasta"))
                .andExpect(jsonPath("$.price").value(300))
                .andExpect(jsonPath("$.message").value("MENU ITEM UPDATED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturn403WhenNonOwnerEditsItem() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(put("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer customer-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item_name\": \"X\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn404WhenOwnerEditsNonExistentRestaurant() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.editMenuItem(anyString(), any(), anyString(), anyString(), anyInt(), anyString()))
                .thenThrow(new RestaurantNotFoundException("RNF-001", "Restaurant not found"));

        mockMvc.perform(put("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item_name\": \"X\", \"price\": 100, \"type\": \"VEG\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn403WhenOwnerEditsAnotherOwnersItem() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.editMenuItem(anyString(), any(), anyString(), anyString(), anyInt(), anyString()))
                .thenThrow(new AuthorizationFailedException("AUT-001", "You are not authorized to perform this operation"));

        mockMvc.perform(put("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item_name\": \"X\", \"price\": 100, \"type\": \"VEG\"}"))
                .andExpect(status().isForbidden());
    }

    // ==================== DELETE /restaurant/{uuid}/item/{itemUuid} ====================

    @Test
    public void shouldDeleteMenuItemForOwner() throws Exception {
        CustomerEntity owner = buildOwner();
        ItemEntity item = buildItem("Pasta", 250, ItemType.VEG);

        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.deleteMenuItem(anyString(), any(), anyString())).thenReturn(item);

        String itemUuid = UUID.randomUUID().toString();
        mockMvc.perform(delete("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), itemUuid)
                        .header("authorization", "Bearer owner-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemUuid))
                .andExpect(jsonPath("$.message").value("MENU ITEM DELETED SUCCESSFULLY"));
    }

    @Test
    public void shouldReturn403WhenNonOwnerDeletesItem() throws Exception {
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER")))
                .thenThrow(new AuthorizationFailedException("AUT-001", "Not authorized"));

        mockMvc.perform(delete("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer customer-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn404WhenDeletingFromNonExistentRestaurant() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.deleteMenuItem(anyString(), any(), anyString()))
                .thenThrow(new RestaurantNotFoundException("RNF-001", "Restaurant not found"));

        mockMvc.perform(delete("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistentItem() throws Exception {
        CustomerEntity owner = buildOwner();
        when(mockRoleGuard.authorizeRole(anyString(), eq("RESTAURANT_OWNER"))).thenReturn(owner);
        when(mockOwnerService.deleteMenuItem(anyString(), any(), anyString()))
                .thenThrow(new ItemNotFoundException("INF-001", "Item not found"));

        mockMvc.perform(delete("/restaurant/{uuid}/item/{itemUuid}",
                        UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .header("authorization", "Bearer owner-token"))
                .andExpect(status().isNotFound());
    }
}
