package com.openclassrooms.rentalapi.service;

import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.RentalRepository;
import com.openclassrooms.rentalapi.dto.RentalCreateDto;
import com.openclassrooms.rentalapi.dto.RentalDto;
import com.openclassrooms.rentalapi.dto.RentalUpdateDto;
import com.openclassrooms.rentalapi.exception.ResourceNotFoundException;
import com.openclassrooms.rentalapi.mapper.RentalMapper;
import static com.openclassrooms.rentalapi.constants.ErrorMessages.RENTAL_NOT_FOUND;

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
     * Retrieves a rental by its ID.
     *
     * If no rental is found, throws a ResourceNotFoundException.
     * Maps the entity to a DTO using the current host as base URL.
     *
     * @param id the rental ID
     * @return the mapped RentalDto
     * @throws ResourceNotFoundException if no rental is found
     */
    public RentalDto getRentalById(Long id) {
        log.info("Fetching rental with ID: {}", id);

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rental not found with ID: {}", id);
                    return new ResourceNotFoundException(RENTAL_NOT_FOUND + id);
                });

        return rentalMapper.toDto(rental, getBaseUrl());
    }

    /**
     * Updates a rental by ID using data from the update DTO.
     * <p>
     * This method does not handle image updates — only name, surface, price, and
     * description are modified.
     *
     * @param id  rental ID to update
     * @param dto updated rental data (excluding image)
     * @return updated RentalDto
     * @throws ResourceNotFoundException if the rental is not found
     */
    public RentalDto updateRental(Long id, RentalUpdateDto dto) {
        log.info("[updateRental] Fetching rental with ID: {}", id);

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[updateRental] Rental not found with ID: {}", id);
                    return new ResourceNotFoundException(RENTAL_NOT_FOUND + id);
                });

        log.debug("[updateRental] Applying updates from DTO to rental entity");
        rentalMapper.updateEntityFromDto(dto, rental);

        Rental saved = rentalRepository.save(rental);
        log.info("[updateRental] Rental updated and saved with ID: {}", saved.getId());

        return rentalMapper.toDto(saved, getBaseUrl());
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
