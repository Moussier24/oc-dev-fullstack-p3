package com.ocangjava.portail.repository;

import com.ocangjava.portail.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByOwnerId(Long ownerId);
}