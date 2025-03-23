package com.travel.agency.service;

import com.travel.agency.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository <Vehicle, Integer> {

}
