package com.travel.agency.service;

import com.travel.agency.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer id);
    List<Order> findByVehicleId(Integer id);
}
