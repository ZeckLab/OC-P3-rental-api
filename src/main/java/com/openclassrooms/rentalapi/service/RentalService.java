package com.openclassrooms.rentalapi.service;

import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.RentalRepository;
import com.openclassrooms.rentalapi.dto.RentalCreateDto;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final FileStorageService fileStorageService;

    public Rental createRental(RentalCreateDto dto, AppUser owner) {
        String imageUrl = fileStorageService.store(dto.getPicture());

        Rental rental = new Rental(
            null,
            dto.getName(),
            dto.getSurface(),
            dto.getPrice(),
            imageUrl,
            dto.getDescription(),
            owner,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        return rentalRepository.save(rental);
    }
}

