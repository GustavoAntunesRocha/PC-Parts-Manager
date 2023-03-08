package br.com.antunes.gustavo.pcpartsproject.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentDTO {

	private int id;
	
	@NotNull
	private String name;
	
	private String description;
	
	private String manufacturer;
	
	private String version;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date fabricationDate;
	
	private String serial;
	
	private ComputerDTO computerDTO;
}
