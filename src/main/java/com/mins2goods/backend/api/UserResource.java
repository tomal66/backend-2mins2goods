package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.UserDto;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
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
