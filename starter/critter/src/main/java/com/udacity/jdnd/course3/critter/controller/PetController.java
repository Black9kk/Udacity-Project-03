package com.udacity.jdnd.course3.critter.controller;

import java.util.List;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.udacity.jdnd.course3.critter.services.PetServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
	private PetServices petService;

	@PostMapping
	public PetDTO savePet(@RequestBody PetDTO petDTO) {
		petDTO = petService.savePet(petDTO);
		return petDTO;
	}

	@GetMapping("/{petId}")
	public PetDTO getPet(@PathVariable long petId) {
		return petService.getPetByID(petId);
	}

	@GetMapping
	public List<PetDTO> getPets() {
		return petService.getAllPet();
	}

	@GetMapping("/owner/{ownerId}")
	public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
		return petService.getPetsByCustomerId(ownerId);
	}
}
