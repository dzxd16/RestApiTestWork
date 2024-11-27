package de.telran.restapitestwork.service.impl;

import de.telran.restapitestwork.dto.UserRequestDto;
import de.telran.restapitestwork.exception.InvalidPrincipalException;
import de.telran.restapitestwork.exception.UserAlreadyExistsException;
import de.telran.restapitestwork.exception.UserNotAuthenticatedException;
import de.telran.restapitestwork.exception.UserNotFoundException;
import de.telran.restapitestwork.model.entity.user.User;
import de.telran.restapitestwork.repository.UserRepository;
import de.telran.restapitestwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User register(UserRequestDto userRequestDto) {

        if (userRepository.existsByPhone(userRequestDto.getPhone())) {
            throw new UserAlreadyExistsException("User already exists with email " + userRequestDto.getPhone() + "!");
        }
        User user = User.builder()
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .jobTitle(userRequestDto.getJobTitle())
                .phone(userRequestDto.getPhone())
                .address(userRequestDto.getAddress())
                .interests(userRequestDto.getInterests())
                .links(userRequestDto.getLinks())
                .avatar(userRequestDto.getAvatar())
                .profile(userRequestDto.getProfile())
                .build();
        return userRepository.save(user);
    }

    public void updateAvatar(Long id, String avatarUrl) {
        Optional<User> user = userRepository.findById(id);
        User existingUser = user.get();
        existingUser.setAvatar(avatarUrl);
        userRepository.save(existingUser);
    }

    @Override
    public User update(Long id, UserRequestDto userRequestDto) {

        Optional<User> user = userRepository.findById(id);

        User existingUser = user.get();
        existingUser.setName(userRequestDto.getName());
        existingUser.setSurname(userRequestDto.getSurname());
        existingUser.setJobTitle(userRequestDto.getJobTitle());
        existingUser.setPhone(userRequestDto.getPhone());
        existingUser.setAddress(userRequestDto.getAddress());
        existingUser.setInterests(userRequestDto.getInterests());
        existingUser.setLinks(userRequestDto.getLinks());
        existingUser.setProfile(userRequestDto.getProfile());
        return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public User getByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("User with phone " + phone + " does not exist!"));

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found with id " + id));
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("User is not authenticated!");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return getByPhone(userDetails.getUsername()).getId();
        } else {
            throw new InvalidPrincipalException("The primary authentication object cannot be use");
        }
    }
}
