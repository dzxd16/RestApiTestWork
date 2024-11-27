package de.telran.restapitestwork.mapper;

import de.telran.restapitestwork.dto.UserResponseDto;
import de.telran.restapitestwork.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResponseDto>{
    @Override
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getJobTitle(),
                user.getPhone(),
                user.getAddress(),
                user.getInterests(),
                user.getLinks(),
                user.getAvatar(),
                user.getProfile()
        );
    }

    @Override
    public Set<UserResponseDto> toDtoSet(Set<User> users) {
        return null;
    }

    @Override
    public User toEntity(UserResponseDto userResponseDto) {
        return null;
    }

    @Override
    public Set<User> toEntitySet(Set<UserResponseDto> userResponseDtos) {
        return null;
    }
}
