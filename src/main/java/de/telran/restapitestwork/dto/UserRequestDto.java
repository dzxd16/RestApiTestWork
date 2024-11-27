package de.telran.restapitestwork.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Invalid name : Empty name")
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters")
    @Pattern(
      regexp = "^[a-zA-Z ]+$",
      message = "The name must be between 2 and 50 characters long and include only letters and spaces."
    )
    private String name;

    @NotBlank(message = "Invalid surname : Empty surname")
    @Size(min = 2, max = 50, message = "surname must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "The surname must be between 2 and 50 characters long and include only letters and spaces."
    )
    private String surname;

    @Size(max = 100, message = "jobTitle must be no more than 100 characters")
    @Pattern(
            regexp = "^[0-9a-zA-Z ]+$",
            message = "The surname must be between 2 and 50 characters long and include only letters and spaces."
    )
    private String jobTitle;

    @NotBlank(message = "Invalid phone : Empty phone")
    @Size(min = 10, max = 15, message = "phone must be between 10 and 15 characters")
    @Pattern(
            regexp = "^\\+[\\d]{10,15}$",
            message = "The phone number must be between 10 and 15 characters long and contain only numbers and start with +."
    )
    private String phone;

    @Size(max = 200, message = "address must be no more than 200 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9,.\\- ]*$",
            message = "The address must be no more than 200 characters long and include only letters, numbers, spaces and symbols like commas or periods."
    )
    private String address;

    @Pattern(
            regexp = "^[a-zA-Z0-9 ,.]{1,30}$",
            message = "The address must be no more than 200 characters long and include only letters, numbers, spaces and symbols like commas or periods."
    )
    private String[] interests;

    @Size(max = 200, message = "links must be no more than 200 characters")
    @Pattern(
            regexp = "^(https?://).{1,198}$",
            message = "The links must be no more than 200 characters long and include https or http."
    )
    private String links;

    private String avatar;

    private de.telran.restapitestwork.model.entity.user.profile profile;

}
