package br.com.antunes.gustavo.pcpartsproject.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Component {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String name;
	
	private String description;
	
	private String manufacturer;
	
	private String version;
	
	private Date fabricationDate;
	
	private String serial;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name = "computer_component",
			joinColumns = @JoinColumn(name = "component_id", referencedColumnName="id"),
	        inverseJoinColumns = @JoinColumn(name = "computer_id", referencedColumnName="id")
			)
	private Computer computer;
}
