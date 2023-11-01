package com.udacity.jdnd.course3.critter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@Service
@Transactional
public class PetServices {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PetRepository petRepository;

	public PetDTO savePet(PetDTO petdto) {
		Pet savePet = petRepository.save(convertDtoToEntity(petdto));
		Customer customer = savePet.getCustomer();
		List<Pet> pets = customer.getPets();
		if (!pets.contains(savePet)) {
			pets.add(savePet);
		}
		return convertEntityToDto(savePet);
	}

	public PetDTO getPetByID(Long id) {
		Pet pet = petRepository.findById(id).orElse(new Pet());
		return convertEntityToDto(pet);
	}

	public List<PetDTO> getAllPet() {
		List<PetDTO> petDtoList = new ArrayList<>();
		List<Pet> petList = petRepository.findAll();
		for (Pet pet : petList) {
			petDtoList.add(convertEntityToDto(pet));
		}
		return petDtoList;
	}

	public List<PetDTO> getPetsByCustomerId(Long customerId) {
		List<PetDTO> petDtoList = new ArrayList<>();
		Customer customer = customerRepository.findById(customerId).orElse(new Customer());
		List<Pet> petList = customer.getPets();
		if (petList != null && !petList.isEmpty()) {
			for (Pet pet : petList) {
				petDtoList.add(convertEntityToDto(pet));
			}
		}
		return petDtoList;
	}

	public List<Pet> getPetListById(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(new Customer());
		List<Pet> petList = customer.getPets();
		return petList;
	}

	public Pet convertDtoToEntity(PetDTO petDTO) {
		Pet pet = new Pet();
		Customer customer = customerRepository.findById(petDTO.getOwnerId()).orElse(new Customer());
		BeanUtils.copyProperties(petDTO, pet);
		pet.setCustomer(customer);
		return pet;
	}

	private PetDTO convertEntityToDto(Pet pet) {
		PetDTO petDTO = new PetDTO();
		BeanUtils.copyProperties(pet, petDTO);
		Customer customer = pet.getCustomer();
		petDTO.setOwnerId(customer.getId());
		return petDTO;
	}
}
