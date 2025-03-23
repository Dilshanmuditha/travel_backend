package com.travel.agency.controller;

import com.travel.agency.dto.VehicleDto;
import com.travel.agency.models.Vehicle;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping({"","/"})
    public ResponseEntity<List<Vehicle>> showVehiclesList(Model model){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showVehicleDetails(@PathVariable int id){
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVehicle(@Valid @ModelAttribute VehicleDto vehicleDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Check if the image is empty
        if (vehicleDto.getImage().isEmpty()) {
            result.addError(new FieldError("vehicleDto", "image", "Image is required"));
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        MultipartFile image = vehicleDto.getImage();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to disk
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

            // Create and save the vehicle
            Vehicle vehicle = new Vehicle();
            vehicle.setCreated_at(createdAt);
            vehicle.setBrand(vehicleDto.getBrand());
            vehicle.setCategory(vehicleDto.getCategory());
            vehicle.setDescription(vehicleDto.getDescription());
            vehicle.setImage(storageFileName);
            vehicle.setDriver_email(vehicleDto.getDriver_email());
            vehicle.setDriver_mobile(vehicleDto.getDriver_mobile());
            vehicle.setPassword(vehicleDto.getPassword());
            vehicle.setPrice_perday(vehicleDto.getPrice_perday());
            vehicleRepository.save(vehicle);

            return ResponseEntity.ok(vehicle);

        } catch (IOException ex) {
            // Handle file saving exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving the file: " + ex.getMessage());
        } catch (Exception ex) {
            // Catch any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editVehicle(@PathVariable int id, @Valid @ModelAttribute VehicleDto vehicleDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            // Check if the vehicle exists
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
            if (optionalVehicle.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
            }

            Vehicle vehicle = optionalVehicle.get();
            vehicle.setBrand(vehicleDto.getBrand());
            vehicle.setCategory(vehicleDto.getCategory());
            vehicle.setDescription(vehicleDto.getDescription());
            vehicle.setDriver_email(vehicleDto.getDriver_email());
            vehicle.setDriver_mobile(vehicleDto.getDriver_mobile());
            vehicle.setPassword(vehicleDto.getPassword());
            vehicle.setPrice_perday(vehicleDto.getPrice_perday());

            // Handle image update if a new image is provided
            if (!vehicleDto.getImage().isEmpty()) {
                MultipartFile image = vehicleDto.getImage();
                String storageFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }

                // Set new image
                vehicle.setImage(storageFileName);
            }

            vehicleRepository.save(vehicle);
            return ResponseEntity.ok(vehicle);

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving the file: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

}
