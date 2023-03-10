package br.com.antunes.gustavo.pcpartsproject.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.pcpartsproject.dto.UsersDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.UserNotFoundException;
import br.com.antunes.gustavo.pcpartsproject.model.Users;
import br.com.antunes.gustavo.pcpartsproject.service.UsersService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO userDTO, @RequestParam String password) {
        Users user = modelMapper.map(userDTO, Users.class);
        user.setPassword(password);
        UsersDTO createdUserDTO = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable int id) throws UserNotFoundException {
        UsersDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        UsersDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody UsersDTO userDTO)
            throws UserNotFoundException {
        Users user = modelMapper.map(userDTO, Users.class);
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
