package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerFavoriteEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerFavoriteDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CustomerFavoriteEntity> getFavoritesByCustomer(Integer customerId) {
        return entityManager.createNamedQuery("Favorite.byCustomer", CustomerFavoriteEntity.class)
            .setParameter("customerId", customerId)
            .getResultList();
    }

    public CustomerFavoriteEntity findFavorite(Integer customerId, Integer restaurantId) {
        try {
            return entityManager.createNamedQuery("Favorite.find", CustomerFavoriteEntity.class)
                .setParameter("customerId", customerId)
                .setParameter("restaurantId", restaurantId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomerFavoriteEntity save(CustomerFavoriteEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(CustomerFavoriteEntity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
