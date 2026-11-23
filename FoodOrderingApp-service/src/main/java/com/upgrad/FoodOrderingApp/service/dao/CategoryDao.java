package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method returns all categories
     *
     * @return CategoryEntity list
     */
    public List<CategoryEntity> getAllCategories() {
        try {
            return entityManager.createNamedQuery("Category.fetchAllCategories", CategoryEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method takes a category uuid and returns the matching CategoryEntity
     *
     * @param categoryId category uuid
     * @return CategoryEntity
     */
    public CategoryEntity getCategoryById(final String categoryId) {
        try {
            return entityManager.createNamedQuery("Category.fetchCategoryItem", CategoryEntity.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method takes a restaurantEntity and returns the matching CategoryEntity List
     *
     * @param restaurantEntity RestaurantEntity
     * @return CategoryEntity list
     */
    public CategoryEntity getCategoryByName(String name) {
        try {
            return entityManager.createQuery(
                "SELECT c FROM CategoryEntity c WHERE LOWER(c.categoryName) = :name", CategoryEntity.class)
                .setParameter("name", name.toLowerCase().trim())
                .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CategoryEntity> getCategoriesByRestaurant(RestaurantEntity restaurantEntity) {
        try {
            return entityManager.createNamedQuery("RestaurantCategoryEntity.getCategoryByRestaurant", CategoryEntity.class).setParameter("restaurant", restaurantEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
