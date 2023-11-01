package com.udacity.jdnd.course3.critter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServices {

	@Autowired
	private PetServices petServices;

	@Autowired
	private CustomerRepository customerRepository;

	public List<CustomerDTO> getAllCustomers() {
		List<CustomerDTO> customerDtoList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findAll();
		for (Customer customer : customerList) {
			customerDtoList.add(convertEntityToDto(customer));
		}
		return customerDtoList;
	}

	public CustomerDTO getCustomerByPetId(Long petId) {
		Pet pet = petServices.convertDtoToEntity(petServices.getPetByID(petId));
		return convertEntityToDto(pet.getCustomer());
	}

	public CustomerDTO saveCustomer(CustomerDTO customerDto) {
		Customer customer = customerRepository.save(convertDtoToEntity(customerDto));
		return convertEntityToDto(customer);
	}

	public Customer findCustomerById(Long id) {
		return customerRepository.findById(id).orElse(new Customer());
	}

	private Customer convertDtoToEntity(CustomerDTO customerDto) {
		Customer customer = new Customer();
		List<Pet> petList = petServices.getPetListById(customerDto.getId());
		BeanUtils.copyProperties(customerDto, customer);
		if (petList != null && !petList.isEmpty()) {
			customer.setPets(petList);
		} else {
			customer.setPets(new ArrayList<>());
		}

		return customer;
	}

	private CustomerDTO convertEntityToDto(Customer customer) {
		List<Long> petIdList = new ArrayList<>();
		List<Pet> petList = customer.getPets();

		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);

		customerDTO.setPetIds(petIdList);
		if (petList != null && !petList.isEmpty()) {
			for (Pet pet : petList) {
				customerDTO.getPetIds().add(pet.getId());
			}
		}
		return customerDTO;
	}
}
