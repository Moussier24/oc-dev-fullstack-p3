package com.ocangjava.portail.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ocangjava.portail.dto.MessageResponse;
import com.ocangjava.portail.dto.RentalDto;
import com.ocangjava.portail.dto.RentalResponse;
import com.ocangjava.portail.dto.RentalsResponse;
import com.ocangjava.portail.service.RentalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRental(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            @RequestParam("description") String description) {
        RentalDto rentalDto = new RentalDto();
        rentalDto.setName(name);
        rentalDto.setSurface(BigDecimal.valueOf(surface));
        rentalDto.setPrice(BigDecimal.valueOf(price));
        rentalDto.setDescription(description);

        return ResponseEntity.ok(rentalService.createRental(rentalDto, picture));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(id);
        rentalDto.setName(name);
        rentalDto.setSurface(BigDecimal.valueOf(surface));
        rentalDto.setPrice(BigDecimal.valueOf(price));
        rentalDto.setDescription(description);

        return ResponseEntity.ok(rentalService.updateRental(rentalDto));
    }
}