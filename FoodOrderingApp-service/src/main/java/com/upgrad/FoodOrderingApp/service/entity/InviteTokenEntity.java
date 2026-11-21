package com.upgrad.FoodOrderingApp.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "invite_tokens")
@NamedQueries({
    @NamedQuery(name = "InviteToken.byToken", query = "SELECT t FROM InviteTokenEntity t WHERE t.token = :token")
})
public class InviteTokenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "token")
    @NotNull
    @Size(max = 200)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @NotNull
    private RestaurantEntity restaurant;

    @Column(name = "expires_at")
    @NotNull
    private LocalDateTime expiresAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_by", referencedColumnName = "id")
    private CustomerEntity usedBy;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public RestaurantEntity getRestaurant() { return restaurant; }
    public void setRestaurant(RestaurantEntity restaurant) { this.restaurant = restaurant; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }

    public CustomerEntity getUsedBy() { return usedBy; }
    public void setUsedBy(CustomerEntity usedBy) { this.usedBy = usedBy; }
}
