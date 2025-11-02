package com.openclassrooms.rentalapi.mapper;

import org.mapstruct.Mapper;
import com.openclassrooms.rentalapi.dto.AppUserDto;
import com.openclassrooms.rentalapi.model.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto toDto(AppUser user);
}
