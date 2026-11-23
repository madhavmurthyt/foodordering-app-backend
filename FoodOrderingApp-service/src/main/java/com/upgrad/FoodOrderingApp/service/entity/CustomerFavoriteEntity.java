package com.upgrad.FoodOrderingApp.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_favorite")
@NamedQueries({
    @NamedQuery(name = "Favorite.byCustomer", query = "SELECT f FROM CustomerFavoriteEntity f WHERE f.customer.id = :customerId ORDER BY f.createdAt DESC"),
    @NamedQuery(name = "Favorite.find", query = "SELECT f FROM CustomerFavoriteEntity f WHERE f.customer.id = :customerId AND f.restaurant.id = :restaurantId")
})
public class CustomerFavoriteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @NotNull
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @NotNull
    private RestaurantEntity restaurant;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public CustomerEntity getCustomer() { return customer; }
    public void setCustomer(CustomerEntity customer) { this.customer = customer; }

    public RestaurantEntity getRestaurant() { return restaurant; }
    public void setRestaurant(RestaurantEntity restaurant) { this.restaurant = restaurant; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
