package com.openclassrooms.rentalapi.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import com.openclassrooms.rentalapi.dto.RentalCreateDto;
import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.dto.RentalDto;
import com.openclassrooms.rentalapi.dto.RentalUpdateDto;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    // Maps a {@link Rental} entity to a {@link RentalDto} object (record type) for Api exposure.
    @Mapping(source = "owner.id", target = "owner_id")
    @Mapping(source = "createdAt", target = "created_at", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updated_at", dateFormat = "yyyy/MM/dd")
    @Mapping(target = "picture", expression = "java(buildImageUrl(baseUrl, rental.getPicture()))")
    RentalDto toDto(Rental rental, @Context String baseUrl);

    // Helper method to build full image URLs
    default String buildImageUrl(String baseUrl, String picture) {
        return baseUrl + picture;
    }

    // Maps a {@link RentalCreateDto} object to a {@link Rental} entity during creation.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "picture", ignore = true)
    Rental toEntity(RentalCreateDto dto);

    // Updates an existing {@link Rental} entity from a {@link RentalUpdateDto} object.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateEntityFromDto(RentalUpdateDto dto, @MappingTarget Rental entity);

    /**
     * Maps a list of {@link Rental} entities to a list of {@link RentalDto}
     * objects (record type).
     * <p>
     * The provided {@code baseUrl} is injected via {@link @Context} and used to
     * resolve image URLs or other absolute paths within each DTO.
     *
     * @param rentals the list of rental entities to convert
     * @param baseUrl the base URL used for constructing full image paths
     * @return a list of mapped {@link RentalDto} objects
     */
    default List<RentalDto> toDtoList(List<Rental> rentals, @Context String baseUrl) {
        return rentals.stream()
                .map(rental -> toDto(rental, baseUrl))
                .toList();
    }
}
