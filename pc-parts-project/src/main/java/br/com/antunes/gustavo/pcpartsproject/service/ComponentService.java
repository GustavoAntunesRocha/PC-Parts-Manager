package br.com.antunes.gustavo.pcpartsproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.pcpartsproject.dto.ComponentDTO;
import br.com.antunes.gustavo.pcpartsproject.dto.ComputerDTO;
import br.com.antunes.gustavo.pcpartsproject.exception.CustomException;
import br.com.antunes.gustavo.pcpartsproject.model.Component;
import br.com.antunes.gustavo.pcpartsproject.repository.ComponentRepository;
import jakarta.transaction.Transactional;

@Service
public class ComponentService {
	
	@Autowired
	private ComponentRepository componentRepository;
	
	public Component getById(int id) {
		Optional<Component> optionalObject = null;
		try {
			optionalObject = componentRepository.findById(id);
			if(optionalObject.isEmpty()) {
				throw new CustomException("Component with id {" + id + "} not found!");
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionalObject.get();
	}
	
	public Component create(ComponentDTO dto) {
		Component component = new Component();
		component.setName(dto.getName());
		component.setDescription(dto.getDescription());
		component.setFabricationDate(dto.getFabricationDate());
		component.setManufacturer(dto.getManufacturer());
		component.setSerial(dto.getSerial());
		component.setVersion(dto.getVersion());
		try {
			componentRepository.save(component);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		return component;
	}
	
	@Transactional
	public Component update(ComponentDTO dto) {
		Optional<Component> optionalObject = componentRepository.findById(dto.getId());
		if(optionalObject.isEmpty()) {
        	throw new CustomException("Component with the id '" + dto.getId() +"' not found in the database!");
        }
		Component component = optionalObject.get();
		component.setName(dto.getName());
		component.setDescription(dto.getDescription());
		component.setFabricationDate(dto.getFabricationDate());
		component.setManufacturer(dto.getManufacturer());
		component.setSerial(dto.getSerial());
		component.setVersion(dto.getVersion());
		try {
			componentRepository.save(component);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		return component;
	}
	
	public void delete(int id) {
		try {
			Component component = exists(id);
			componentRepository.delete(component);
		} catch (CustomException e) {
			// TODO: handle exception
		}
	}
	
	public Component exists(int id) {
		Optional<Component> optionalObject = componentRepository.findById(id);
		if(optionalObject.isEmpty()) {
        	throw new CustomException("Component with the id '" + id +"' not found in the database!");
        }
		return optionalObject.get();
	}
	
	public List<Component> getByComputer(ComputerDTO computerDTO){
		List<Component> components = new ArrayList<>();
		if(!Integer.toString(computerDTO.getId()).isEmpty()) {
			components = componentRepository.findByComputerId(computerDTO.getId());
			return components;
		} else if(!computerDTO.getName().isEmpty()) {
			components = componentRepository.findByComputerName(computerDTO.getName());
			return components;
		}
		throw new CustomException("Computer id or name must be provided!");
	}
	
	public List<Component> getAllComponents(){
		return componentRepository.findAll();
	}
	
	public ComponentDTO mapToDTO(Component component) {
		ComponentDTO componentDTO = new ComponentDTO();
		componentDTO.setId(component.getId());
		componentDTO.setName(component.getName());
		componentDTO.setDescription(component.getDescription());
		componentDTO.setFabricationDate(component.getFabricationDate());
		componentDTO.setManufacturer(component.getManufacturer());
		componentDTO.setSerial(component.getSerial());
		componentDTO.setVersion(component.getVersion());
		if(component.getComputer() != null) {
			ComputerDTO computerDTO = new ComputerDTO();
			computerDTO.setId(component.getComputer().getId());
			computerDTO.setName(component.getComputer().getName());
			computerDTO.setDescription(component.getComputer().getDescription());
			computerDTO.setAssemblyDate(component.getComputer().getAssemblyDate());
			computerDTO.setLastMaintenance(component.getComputer().getLastMaintenance());
			computerDTO.setLocation(component.getComputer().getLocation());
			
			componentDTO.setComputerDTO(computerDTO);
		}
		return componentDTO;
	}
	
	public List<ComponentDTO> mapToDTOList(Set<Component> components) {
		List<ComponentDTO> componentDTOList = new ArrayList<>();
		for (Component component : components) {
			ComponentDTO componentDTO = new ComponentDTO();
			componentDTO.setId(component.getId());
			componentDTO.setName(component.getName());
			componentDTO.setDescription(component.getDescription());
			componentDTO.setFabricationDate(component.getFabricationDate());
			componentDTO.setManufacturer(component.getManufacturer());
			componentDTO.setSerial(component.getSerial());
			componentDTO.setVersion(component.getVersion());
			
			componentDTOList.add(componentDTO);
		}
		return componentDTOList;
	}
	
	public List<ComponentDTO> mapToDTOList(List<Component> components) {
		List<ComponentDTO> componentDTOList = new ArrayList<>();
		for (Component component : components) {
			ComponentDTO componentDTO = new ComponentDTO();
			componentDTO.setId(component.getId());
			componentDTO.setName(component.getName());
			componentDTO.setDescription(component.getDescription());
			componentDTO.setFabricationDate(component.getFabricationDate());
			componentDTO.setManufacturer(component.getManufacturer());
			componentDTO.setSerial(component.getSerial());
			componentDTO.setVersion(component.getVersion());
			
			componentDTOList.add(componentDTO);
		}
		return componentDTOList;
	}
	
	public Component mapFromDTO(ComponentDTO dto) {
		Component component = new Component();
		component.setName(dto.getName());
		component.setDescription(dto.getDescription());
		component.setFabricationDate(dto.getFabricationDate());
		component.setManufacturer(dto.getManufacturer());
		component.setSerial(dto.getSerial());
		component.setVersion(dto.getVersion());
		return component;
	}

}
