package br.com.antunes.gustavo.pcpartsproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.antunes.gustavo.pcpartsproject.dto.UsersDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.UserNotFoundException;
import br.com.antunes.gustavo.pcpartsproject.model.Users;
import br.com.antunes.gustavo.pcpartsproject.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersDTO createUser(Users user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UsersDTO.class);
    }

    public UsersDTO getUserById(int id) throws UserNotFoundException {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UsersDTO.class);
        } else {
            throw new UserNotFoundException("User not found with ID " + id);
        }
    }

    public UsersDTO getUserByEmail(String email) throws UserNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email " + email);
        }
        return modelMapper.map(user, UsersDTO.class);
    }
    
    public Users findUserByEmail(String email) throws UserNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email " + email);
        }
        return user;
    }

    public List<UsersDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }

    public UsersDTO updateUser(Users user) throws UserNotFoundException {
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            Users savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UsersDTO.class);
        } else {
            throw new UserNotFoundException("User not found with ID " + user.getId());
        }
    }

    public void deleteUserById(int id) throws UserNotFoundException {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with ID " + id);
        }
    }

    public String getUserDTOAsJson(int id) throws UserNotFoundException, JsonProcessingException {
        UsersDTO userDTO = getUserById(id);
        return objectMapper.writeValueAsString(userDTO);
    }

    public String getAllUsersAsJson() throws JsonProcessingException {
        List<UsersDTO> userDTOs = getAllUsers();
        return objectMapper.writeValueAsString(userDTOs);
    }
}
