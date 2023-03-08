package br.com.antunes.gustavo.pcpartsproject.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.pcpartsproject.dto.ComponentDTO;
import br.com.antunes.gustavo.pcpartsproject.dto.ComputerDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.CustomException;
import br.com.antunes.gustavo.pcpartsproject.model.Component;
import br.com.antunes.gustavo.pcpartsproject.model.Computer;
import br.com.antunes.gustavo.pcpartsproject.repository.ComputerRepository;
import jakarta.transaction.Transactional;

@Service
public class ComputerService {

	@Autowired
	private ComputerRepository computerRepository;
	
	@Autowired
	private ComponentService componentService;
	
	public Computer getById(int id) {
		Optional<Computer> optionalObject = null;
		try {
			optionalObject = computerRepository.findById(id);
			if(optionalObject.isEmpty()) {
				throw new CustomException("Computer with id {" + id + "} not found!");
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionalObject.get();
	}
	
	public Computer create(ComputerDTO dto) {
		Computer computer = new Computer();
		computer = mapFromDTO(dto);
		if(!dto.getComponentList().isEmpty()) {
			for (ComponentDTO componentDTO : dto.getComponentList()) {
				computer.addComponent(componentService.getById(componentDTO.getId()));
			}
		}
		
		try {
			computerRepository.save(computer);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		return computer;
	}
	
	@Transactional
	public Computer update(ComputerDTO dto) {
		Optional<Computer> optionalObject = computerRepository.findById(dto.getId());
		if(optionalObject.isEmpty()) {
        	throw new CustomException("Computer with the id '" + dto.getId() +"' not found in the database!");
        }
		Computer computer = optionalObject.get();
		computer = mapFromDTO(dto);
		try {
			computerRepository.save(computer);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		return computer;
	}
	
	public void delete(int id) {
		try {
			Computer computer = exists(id);
			computerRepository.delete(computer);
		} catch (CustomException e) {
			// TODO: handle exception
		}
	}
	
	public Computer exists(int id) {
		Optional<Computer> optionalObject = computerRepository.findById(id);
		if(optionalObject.isEmpty()) {
        	throw new CustomException("Computer with the id '" + id +"' not found in the database!");
        }
		return optionalObject.get();
	}
	
	public Computer getByComponent(ComponentDTO componentDTO){
		Computer computer = null;
		if(!Integer.toString(componentDTO.getId()).isEmpty()) {
			computer = computerRepository.findByComponentsId(componentDTO.getId());
			return computer;
		}
		throw new CustomException("Computer id or name must be provided!");
	}
	
	public List<Computer> getAllComputers(){
		return computerRepository.findAll();
	}
	
	public ComputerDTO mapToDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setDescription(computer.getDescription());
		computerDTO.setAssemblyDate(computer.getAssemblyDate());
		computerDTO.setLastMaintenance(computer.getLastMaintenance());
		computerDTO.setLocation(computer.getLocation());
		List<ComponentDTO> componentDTOList = componentService.mapToDTOList(computer.getComponents());
		computerDTO.setComponentList(componentDTOList);
		return computerDTO;
	}
	
	public Computer mapFromDTO(ComputerDTO dto) {
		Computer computer = new Computer();
		computer.setName(dto.getName());
		computer.setDescription(dto.getDescription());
		computer.setAssemblyDate(dto.getAssemblyDate());
		computer.setLocation(dto.getLocation());
		computer.setLastMaintenance(dto.getLastMaintenance());
		Set<Component> components = new HashSet<>();
		for (ComponentDTO componentDTO : dto.getComponentList()) {
			components.add(componentService.mapFromDTO(componentDTO));
		}
		computer.setComponents(components);
		return computer;
	}
}
