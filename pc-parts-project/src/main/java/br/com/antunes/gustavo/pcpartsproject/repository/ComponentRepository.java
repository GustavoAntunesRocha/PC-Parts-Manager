package br.com.antunes.gustavo.pcpartsproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.pcpartsproject.model.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer>{
	
	List<Component> findByComputerId(int id);
	
	List<Component> findByComputerName(String name);

}
