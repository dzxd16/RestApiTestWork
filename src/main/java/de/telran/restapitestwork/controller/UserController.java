package de.telran.restapitestwork.controller;

import de.telran.restapitestwork.dto.UserRequestDto;
import de.telran.restapitestwork.dto.UserResponseDto;
import de.telran.restapitestwork.mapper.UserMapper;
import de.telran.restapitestwork.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;
    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping("/upload-avatar")
    @PermitAll

    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }
            long maxFileSize = 5 * 1024 * 1024; // 5 MB
            if (file.getSize() > maxFileSize) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File size exceeds the limit of 5 MB");
            }
            String fileExtension = getFileExtension(file.getOriginalFilename());
            if (fileExtension == null || !isAllowedExtension(fileExtension)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Supported formats: .jpg, .jpeg, .png");
            }
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path path = Paths.get(uploadFolder + File.separator + uniqueFileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            String fileUrl = "/uploaded_avatars/" + uniqueFileName;
            userService.updateAvatar(userId, fileUrl);

            return ResponseEntity.status(HttpStatus.OK).body(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    private boolean isAllowedExtension(String extension) {
        return extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg");
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
//    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(userService.register(userRequestDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto userRequestDto, @PathVariable long id) {
//    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.update(id, userRequestDto)));
    }

    @DeleteMapping()
    public ResponseEntity delete() {
        Long id = userService.getCurrentUserId();
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<UserResponseDto> getByEmail(@PathVariable String phone) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.getByPhone(phone)));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(userService.findById(id)));
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapper.toDto(userService.getByName(userDetails.getUsername()).get()));
        }
        return null;
    }
}
