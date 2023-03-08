package br.com.antunes.gustavo.pcpartsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.pcpartsproject.model.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Integer>{
	
	Computer findByComponentsId(int id);
	
}
