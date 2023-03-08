package br.com.antunes.gustavo.pcpartsproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.pcpartsproject.dto.ComputerDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.ApiErrorResponse;
import br.com.antunes.gustavo.pcpartsproject.exception.CustomException;
import br.com.antunes.gustavo.pcpartsproject.service.ComputerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/computer")
public class ComputerController {

	@Autowired
	private ComputerService computerService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		ComputerDTO computerDTO;
		try {
			computerDTO = computerService.mapToDTO(computerService.getById(id));
		} catch (CustomException e) {
			return handleCustomException(e);
		}
		return ResponseEntity.ok(computerDTO);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ComputerDTO computerDTO) {
		ComputerDTO computer = computerService.mapToDTO(computerService.create(computerDTO));
		return ResponseEntity.ok(computer);
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody ComputerDTO computerDTO) {
		ComputerDTO computer;
		try {
			computer = computerService.mapToDTO(computerService.update(computerDTO));
			return ResponseEntity.ok(computer);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id){
		try {
			computerService.delete(id);
			return ResponseEntity.ok().build();
		} catch (CustomException e) {
			return handleCustomException(e);
		}
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentNotValidException ex) {
		LocalDateTime timestamp = LocalDateTime.now();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		String message = "Invalid parameters: ";
		for (FieldError fieldError : fieldErrors) {
			message += "{" + fieldError.getField() + "=" + fieldError.getRejectedValue() + "}";
		}

		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), message);

		return ResponseEntity.badRequest().body(errorResponse);
	}

}

