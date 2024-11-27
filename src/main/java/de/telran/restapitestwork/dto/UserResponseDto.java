package de.telran.restapitestwork.dto;

public record UserResponseDto (
        long id,
        String name,
        String surname,
        String jobTitle,
        String phone,
        String address,
        String[] interests,
        String links,
        String avatar,
        de.telran.restapitestwork.model.entity.user.profile profile
){
}
