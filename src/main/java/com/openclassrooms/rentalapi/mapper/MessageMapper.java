package com.openclassrooms.rentalapi.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.rentalapi.dto.MessageRequestDto;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.model.Message;

@Component
public class MessageMapper {

    // Convert MessageRequestDto to Message entity
    public Message toEntity(MessageRequestDto dto, AppUser sender, Rental rental){
        Message message = new Message();
        message.setSender(sender);
        message.setRental(rental);
        message.setMessage(dto.getMessage());
        return message;
    }

}
