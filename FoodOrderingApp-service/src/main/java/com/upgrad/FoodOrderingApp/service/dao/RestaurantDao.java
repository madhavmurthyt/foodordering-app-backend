package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<RestaurantEntity> restaurantsByRating() {
        return entityManager.createNamedQuery("Restaurants.fetchAll").getResultList();
    }

    public List<RestaurantEntity> restaurantsByName(String name) {
        return entityManager.createNamedQuery("Restaurants.getByName").setParameter("name", "%" + name.toLowerCase() + "%").getResultList();
    }

    public RestaurantEntity getRestaurantByID(String restaurantId) {
        try {
            return entityManager.createNamedQuery("Restaurants.getById", RestaurantEntity.class).setParameter("id", restaurantId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RestaurantEntity> restaurantByCategory(CategoryEntity categoryEntity) {
        try {
            return entityManager.createNamedQuery("RestaurantCategoryEntity.getRestaurantByCategory", RestaurantEntity.class).setParameter("category", categoryEntity).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurant) {
        try {
            entityManager.merge(restaurant);
            return restaurant;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RestaurantEntity> getAllRestaurants(int page, int size) {
        return entityManager.createNamedQuery("Restaurants.fetchAllAdmin", RestaurantEntity.class)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .getResultList();
    }

    public long countAllRestaurants() {
        return (Long) entityManager.createNamedQuery("Restaurants.countAll").getSingleResult();
    }

    public RestaurantEntity getRestaurantByUuid(String uuid) {
        try {
            return entityManager.createNamedQuery("Restaurants.getById", RestaurantEntity.class)
                .setParameter("id", uuid)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RestaurantEntity updateRestaurant(RestaurantEntity restaurant) {
        return entityManager.merge(restaurant);
    }
}