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

import br.com.antunes.gustavo.pcpartsproject.dto.ComponentDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.ApiErrorResponse;
import br.com.antunes.gustavo.pcpartsproject.exception.CustomException;
import br.com.antunes.gustavo.pcpartsproject.service.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/component")
@Tag(name = "Component API", description = "Endpoints for managing components")
public class ComponentController {

	@Autowired
	private ComponentService componentService;

	@Operation(description = "Get a component object by ID", responses = {
				@ApiResponse(responseCode = "200", description = "Successfully retrieved a component!", content = @Content(mediaType = "application/json")) })
	@Parameter(name = "id", description = "ID of the component to retrieve", required = true, example = "1", in = ParameterIn.PATH)
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		ComponentDTO componentDTO;
		try {
			componentDTO = componentService.mapToDTO(componentService.getById(id));
		} catch (CustomException e) {
			return handleCustomException(e);
		}
		return ResponseEntity.ok(componentDTO);
	}
	
	@Operation(description = "Get all components in the database", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all components!", content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<ComponentDTO> components;
		try {
			components = componentService.mapToDTOList(componentService.getAllComponents());
		} catch (CustomException e) {
			return handleCustomException(e);
		}
		return ResponseEntity.ok(components);
	}

	@Operation(description = "Creates a component object", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully created a component!", content = @Content(mediaType = "application/json")) })
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ComponentDTO componentDTO) {
		ComponentDTO component = componentService.mapToDTO(componentService.create(componentDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(component);
	}
	
	@Operation(description = "Creates multiple component objects", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully created all components!", content = @Content(mediaType = "application/json")) })
	@PostMapping("/multiple")
	public ResponseEntity<?> create(@Valid @RequestBody List<ComponentDTO> componentDTOs) {
		for (ComponentDTO componentDTO : componentDTOs) {
			componentService.create(componentDTO);			
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(description = "Updates a component object", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the component!", content = @Content(mediaType = "application/json")) })
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody ComponentDTO componentDTO) {
		ComponentDTO component;
		try {
			component = componentService.mapToDTO(componentService.update(componentDTO));
			return ResponseEntity.ok(component);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
	}
	
	@Operation(description = "Deletes a component object", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the component!", content = @Content(mediaType = "application/json")) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id){
		try {
			componentService.delete(id);
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
