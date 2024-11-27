package de.telran.restapitestwork.service;

import de.telran.restapitestwork.dto.UserRequestDto;
import de.telran.restapitestwork.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(UserRequestDto userRequestDto);

    void updateAvatar(Long id, String avatarUrl);

    User update(Long id, UserRequestDto userRequestDto);

    Optional<User> getById(Long id);
    Optional<User> getByName(String name);

    User getByPhone(String phone);
    void delete(Long id);

    User findById(Long id);

    Long getCurrentUserId();
}
