package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class CouponDao {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Method takes a coupon name and returns the matching CouponEntity
     *
     * @param couponName Coupon Name
     * @return CouponEntity
     */
    public CouponEntity getCouponByCouponName(final String couponName) {
        try {
            return entityManager.createNamedQuery("Coupon.ByName", CouponEntity.class)
                .setParameter("couponName", couponName)
                .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CouponEntity getCouponByCouponId(String uuid) {
        try {
            return entityManager.createNamedQuery("Coupon.ByUuid", CouponEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
