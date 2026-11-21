package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.InviteTokenEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class InviteTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    public InviteTokenEntity findByToken(String token) {
        try {
            return entityManager.createNamedQuery("InviteToken.byToken", InviteTokenEntity.class)
                .setParameter("token", token)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public InviteTokenEntity save(InviteTokenEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public InviteTokenEntity update(InviteTokenEntity entity) {
        return entityManager.merge(entity);
    }
}
