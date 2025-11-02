package com.openclassrooms.rentalapi.service;

import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.RentalRepository;
import com.openclassrooms.rentalapi.dto.RentalCreateDto;
import com.openclassrooms.rentalapi.dto.RentalDto;
import com.openclassrooms.rentalapi.mapper.RentalMapper;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final FileStorageService fileStorageService;
    private final HttpServletRequest request;

    /**
     * Retrieves all rentals from the database and maps them to DTOs,
     * including the base URL for image resolution.
     *
     * @return a list of {@link RentalDto} representing all rentals
     */
    public List<RentalDto> getAllRentals() {
        String baseUrl = getBaseUrl();
        return rentalMapper.toDtoList(rentalRepository.findAll(), baseUrl);
    }

    /**
     * Creates a new rental from the given DTO and owner.
     * <p>
     * This method stores the uploaded image, maps the DTO to a Rental entity,
     * sets the owner and timestamps, and persists the rental in the database.
     *
     * @param dto   the rental creation data
     * @param owner the user who owns the rental
     * @return the persisted {@link Rental} entity
     */
    public Rental createRental(RentalCreateDto dto, AppUser owner) {
        // Store the uploaded image and retrieve its URL
        String imageUrl = fileStorageService.store(dto.getPicture());
        log.info("Image successfully stored: {}", imageUrl);

        Rental rental = rentalMapper.toEntity(dto);
        rental.setPicture(imageUrl);
        rental.setOwner(owner);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

    /**
     * Get the base URL from the current request
     * 
     * @return
     */
    private String getBaseUrl() {
        return ServletUriComponentsBuilder.fromRequest(request)
                .replacePath(null)
                .build()
                .toUriString();
    }
}
