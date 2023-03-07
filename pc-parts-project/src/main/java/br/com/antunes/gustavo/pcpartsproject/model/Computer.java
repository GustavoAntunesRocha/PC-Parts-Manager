package br.com.antunes.gustavo.pcpartsproject.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@NotNull
	private String name;
	
	private String description;
	
	private String location;
	
	@NotNull
	private Date assemblyDate;
	
	private Date lastMaintenance;
	
	@OneToMany(mappedBy = "computer")
	private Set<Component> components;
}
