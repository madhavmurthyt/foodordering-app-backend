package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Method takes a Customer Id as input and list all orders made by the customer
     *
     * @param customerId Customer Id
     * @return List of OrderEntity
     */
    public List<OrderEntity> getOrdersForCustomer(final String customerId) {
        return entityManager.createNamedQuery("Orders.ByCustomer", OrderEntity.class).setParameter("customerId", customerId).getResultList();
    }
    
	/**
	*
	*Methods saves all the order details
	**/
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }


    public OrderItemEntity saveOrderItem(OrderItemEntity orderedItem) {
        entityManager.persist(orderedItem);
        return orderedItem;
    }
    /**
	*List all the Order based on Restaurant
	*
	**/
    public List<OrderEntity> getOrdersByRestaurant(RestaurantEntity restaurant) {
        try {
            return entityManager.createNamedQuery("fetchOrdersByRestaurant", OrderEntity.class)
                .setParameter("restaurant", restaurant).getResultList();
        } catch (NoResultException nre) {
            System.out.printf("GetOrderRestaurant");
            return null;
        }
    }

    public long countAllOrders() {
        return (Long) entityManager.createQuery("SELECT COUNT(o) FROM OrderEntity o").getSingleResult();
    }

    public Double sumAllRevenue() {
        return (Double) entityManager.createQuery("SELECT SUM(o.bill) FROM OrderEntity o").getSingleResult();
    }

    public List<OrderEntity> getAllOrders(int page, int size) {
        return entityManager.createQuery("SELECT o FROM OrderEntity o ORDER BY o.date DESC", OrderEntity.class)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .getResultList();
    }
}
