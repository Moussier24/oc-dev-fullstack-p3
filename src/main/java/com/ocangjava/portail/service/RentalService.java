package com.ocangjava.portail.service;

import com.ocangjava.portail.dto.MessageResponse;
import com.ocangjava.portail.dto.RentalDto;
import com.ocangjava.portail.dto.RentalResponse;
import com.ocangjava.portail.dto.RentalsResponse;
import com.ocangjava.portail.model.Rental;
import com.ocangjava.portail.model.User;
import com.ocangjava.portail.repository.RentalRepository;
import com.ocangjava.portail.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

        private final RentalRepository rentalRepository;
        private final SecurityUtils securityUtils;

        @Value("${app.upload.dir}")
        private String uploadDir;

        @Value("${app.base.url}")
        private String baseUrl;

        public RentalsResponse getAllRentals() {
                List<RentalResponse> rentalResponses = rentalRepository.findAll().stream()
                                .map(this::mapToRentalResponse)
                                .collect(Collectors.toList());

                return RentalsResponse.builder()
                                .rentals(rentalResponses)
                                .build();
        }

        public RentalResponse getRental(Long id) {
                Rental rental = rentalRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Location non trouvée"));
                return mapToRentalResponse(rental);
        }

        private String formatPictureUrl(String picture) {
                if (picture == null || picture.trim().isEmpty()) {
                        return null;
                }

                if (picture.startsWith("http://") || picture.startsWith("https://")) {
                        return picture;
                }

                String normalizedPicture = picture.startsWith("/") ? picture : "/" + picture;
                return baseUrl + normalizedPicture;
        }

        private String uploadPicture(MultipartFile file) {
                if (file == null || file.isEmpty()) {
                        return null;
                }

                try {
                        Path uploadPath = Paths.get(uploadDir);
                        if (!Files.exists(uploadPath)) {
                                Files.createDirectories(uploadPath);
                        }

                        String originalFilename = file.getOriginalFilename();
                        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String newFilename = UUID.randomUUID().toString() + extension;

                        Path filePath = uploadPath.resolve(newFilename);
                        Files.copy(file.getInputStream(), filePath);

                        return uploadDir + "/" + newFilename;
                } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de l'upload de l'image", e);
                }
        }

        private User getCurrentUser() {
                return securityUtils.getCurrentUser()
                                .orElseThrow(() -> new RuntimeException("Utilisateur non connecté"));
        }

        public MessageResponse createRental(RentalDto rentalDto, MultipartFile picture) {
                String picturePath = uploadPicture(picture);

                Rental rental = Rental.builder()
                                .name(rentalDto.getName())
                                .surface(rentalDto.getSurface())
                                .price(rentalDto.getPrice())
                                .picture(formatPictureUrl(picturePath))
                                .description(rentalDto.getDescription())
                                .owner(getCurrentUser())
                                .build();

                rentalRepository.save(rental);

                return MessageResponse.builder()
                                .message("Rental created !")
                                .build();
        }

        public MessageResponse updateRental(RentalDto rentalDto) {
                Rental rental = rentalRepository.findById(rentalDto.getId())
                                .orElseThrow(() -> new RuntimeException("Location non trouvée"));

                rental.setName(rentalDto.getName());
                rental.setSurface(rentalDto.getSurface());
                rental.setPrice(rentalDto.getPrice());
                rental.setDescription(rentalDto.getDescription());

                if (rentalDto.getPicture() != null) {
                        rental.setPicture(formatPictureUrl(rentalDto.getPicture()));
                }

                rentalRepository.save(rental);

                return MessageResponse.builder()
                                .message("Rental updated !")
                                .build();
        }

        private RentalResponse mapToRentalResponse(Rental rental) {
                return RentalResponse.builder()
                                .id(rental.getId())
                                .name(rental.getName())
                                .surface(rental.getSurface())
                                .price(rental.getPrice())
                                .picture(rental.getPicture())
                                .description(rental.getDescription())
                                .ownerId(rental.getOwner().getId())
                                .createdAt(rental.getCreatedAt())
                                .updatedAt(rental.getUpdatedAt())
                                .build();
        }
}