package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.UserDto;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getUsers()
    {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        User user = userService.getUser(username);
        UserDto userDto = userService.convertToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{username}/active")
    public ResponseEntity<UserDto> toggleUserActiveStatus(@PathVariable String username) {
        try {
            User user = userService.getUser(username);
            boolean newActiveStatus = !user.isActive(); // Toggle the active status
            user.setActive(newActiveStatus);
            UserDto userDto = userService.convertToUserDto(user);
            UserDto updatedUser = userService.updateUser(user.getUsername(), userDto); // Save the updated user
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            // Handle other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto.getUsername(), userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user)
    {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }
}
