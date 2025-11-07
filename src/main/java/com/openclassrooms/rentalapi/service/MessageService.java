package com.openclassrooms.rentalapi.service;

import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.rentalapi.dto.MessageRequestDto;
import com.openclassrooms.rentalapi.exception.ResourceNotFoundException;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.model.Message;
import com.openclassrooms.rentalapi.repository.RentalRepository;
import com.openclassrooms.rentalapi.repository.MessageRepository;
import com.openclassrooms.rentalapi.mapper.MessageMapper;
import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final RentalRepository rentalRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    /**
     * Creates and saves a message sent by a user to a rental.
     *
     * @param dto the message data
     * @param sender the authenticated user
     * @throws ResourceNotFoundException if the rental is not found
     * @throws AccessDeniedException if the userId in the DTO doesn't match the authenticated user
     */
    public void createMessage(MessageRequestDto dto, AppUser sender) {
        if (!dto.getUserId().equals(sender.getId())) {
            log.warn("Access denied – identity mismatch: DTO userId = {}, Authenticated userId = {}",
                    dto.getUserId(), sender.getId());
            throw new AccessDeniedException(IDENTITY_MISMATCH);
        }

        Rental rental = rentalRepository.findById(dto.getRentalId())
            .orElseThrow(() -> new ResourceNotFoundException(RENTAL_NOT_FOUND + dto.getRentalId()));

        Message message = messageMapper.toEntity(dto, sender, rental);
        messageRepository.save(message);
        log.info("Message saved: user {} → rental {} | content: \"{}\"",
                sender.getName(), rental.getName(), message.getMessage());

    }
}

