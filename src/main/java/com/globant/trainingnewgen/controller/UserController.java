package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.model.UserDto;
import com.globant.trainingnewgen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<UserDto> getUserByName(@RequestParam String name) {
        UserDto userDto = userService.getUserByName(name);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(String.format("User with id %s deleted successfully", id));
    }



}

