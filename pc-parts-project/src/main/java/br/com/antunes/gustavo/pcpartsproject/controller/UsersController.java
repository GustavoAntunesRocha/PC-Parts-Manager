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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Create a user", description = "Creates a new user and returns the created user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UsersDTO> createUser(@RequestBody @Valid UsersDTO userDTO,
                                                @RequestParam @Parameter(description = "User password") String password) {
        Users user = modelMapper.map(userDTO, Users.class);
        user.setPassword(password);
        UsersDTO createdUserDTO = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UsersDTO> getUserById(@PathVariable @Parameter(description = "User ID") int id)
            throws UserNotFoundException {
        UsersDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a user by email", description = "Returns a user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable @Parameter(description = "User email") String email)
            throws UserNotFoundException {
        UsersDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsersDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody UsersDTO userDTO)
            throws UserNotFoundException {
        Users user = modelMapper.map(userDTO, Users.class);
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Deletes an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
