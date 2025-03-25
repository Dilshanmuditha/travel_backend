package com.travel.agency.controller;

import com.travel.agency.dto.OrderDto;
import com.travel.agency.dto.VehicleDto;
import com.travel.agency.models.Order;
import com.travel.agency.models.User;
import com.travel.agency.models.Vehicle;
import com.travel.agency.service.OrderRepository;
import com.travel.agency.service.UserRepository;
import com.travel.agency.service.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping({"","/"})
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();

            List<Map<String, Object>> orderDetailsList = orders.stream().map(order -> {
                Map<String, Object> orderDetails = new HashMap<>();
                orderDetails.put("order", order);

                // Fetch vehicle details
                Optional<Vehicle> vehicle = vehicleRepository.findById(order.getVehicleId());
                vehicle.ifPresent(v -> orderDetails.put("vehicle", v));

                // Fetch customer details
                Optional<User> customer = userRepository.findById(order.getCustomerId());
                customer.ifPresent(c -> orderDetails.put("customer", c));

                return orderDetails;
            }).toList();

            return ResponseEntity.ok(orderDetailsList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showOrderDetails(@PathVariable int id){
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable int id) {
        List<Order> orders = orderRepository.findByCustomerId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<?> getOrdersByDriverId(@PathVariable int id) {
        List<Order> orders = orderRepository.findByVehicleId(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Date createdAt = new Date();
        try {
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(orderDto.getCustomerId());
            Optional<User> optionalUser = userRepository.findById(orderDto.getCustomerId());
            if (optionalVehicle.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
            }

            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            Vehicle vehicle = optionalVehicle.get();

            // Calculate the difference between start and end date
            long diffInMillies = Math.abs(orderDto.getEnd_date().getTime() - orderDto.getStart_date().getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            // Ensure at least 1 day is counted
            long totalDays = Math.max(diffInDays, 1);
            double totalAmount = totalDays * vehicle.getPrice_perday();

            // Create and save the order
            Order order = new Order();
            order.setCreated_at(createdAt);
            order.setCustomerId(orderDto.getCustomerId());
            order.setVehicleId(orderDto.getVehicleId());
            order.setStart_date(orderDto.getStart_date());
            order.setEnd_date(orderDto.getEnd_date());
            order.setTotal_amount(totalAmount);
            order.setPick_location(orderDto.getPick_location());
            orderRepository.save(order);

            return ResponseEntity.ok(order);

        } catch (Exception ex) {
            // Catch any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }



}
