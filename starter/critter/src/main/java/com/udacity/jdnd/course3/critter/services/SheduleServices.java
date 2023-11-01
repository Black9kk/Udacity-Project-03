package com.udacity.jdnd.course3.critter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class SheduleServices {

	@Autowired
	PetServices petService;

	@Autowired
	EmployeeServices employeeService;

	@Autowired
	CustomerServices customerService;

	@Autowired
	private ScheduleRepository scheduleRepository;

	public List<ScheduleDTO> getAllShedule() {
		List<ScheduleDTO> scheduleDtoList = new ArrayList<>();
		List<Schedule> scheduleList = scheduleRepository.findAll();
		if (scheduleList != null && !scheduleList.isEmpty()) {
			for (Schedule sche : scheduleList) {
				scheduleDtoList.add(convertEntityToDto(sche));
			}
		}
		return scheduleDtoList;
	}

	public ScheduleDTO saveSchedule(ScheduleDTO scheduleDto) {
		Schedule schedule = scheduleRepository.save(convertDtoToEntity(scheduleDto));
		return convertEntityToDto(schedule);
	}

	public List<ScheduleDTO> getSchedulebyEmployeeId(Long id) {
		List<ScheduleDTO> scheduleDtoList = new ArrayList<>();
		List<Schedule> scheduleList = scheduleRepository.findSchedulesByEmployeesId(id);
		if (scheduleList != null && !scheduleList.isEmpty()) {
			for (Schedule sche : scheduleList) {
				scheduleDtoList.add(convertEntityToDto(sche));
			}
		}
		return scheduleDtoList;
	}

	public List<ScheduleDTO> getSchedulebyPetId(Long id) {
		List<ScheduleDTO> scheduleDtoList = new ArrayList<>();
		List<Schedule> scheduleList = scheduleRepository.findSchedulesByPetsId(id);
		if (scheduleList != null && !scheduleList.isEmpty()) {
			for (Schedule sche : scheduleList) {
				scheduleDtoList.add(convertEntityToDto(sche));
			}
		}
		return scheduleDtoList;
	}

	public List<ScheduleDTO> getSchedulebyCustomerId(Long customerId) {
		Customer customer = customerService.findCustomerById(customerId);
		List<ScheduleDTO> scheduleDtoList = new ArrayList<>();
		if (customer != null) {
			for (Pet pet : customer.getPets()) {
				scheduleDtoList.addAll(getSchedulebyPetId(pet.getId()));
			}
		}
		return scheduleDtoList;
	}

	public Schedule convertDtoToEntity(ScheduleDTO scheduleDto) {
		Schedule schedule = new Schedule();
		BeanUtils.copyProperties(scheduleDto, schedule);

		List<Pet> petList = new ArrayList<>();
		if (scheduleDto.getPetIds() != null && scheduleDto.getPetIds().size() > 0) {
			for (Long petId : scheduleDto.getPetIds()) {
				petList.add(petService.convertDtoToEntity(petService.getPetByID(petId)));
			}
		}

		List<Employee> employees = new ArrayList<>();
		if (scheduleDto.getEmployeeIds() != null && scheduleDto.getEmployeeIds().size() > 0) {
			for (Long emplId : scheduleDto.getEmployeeIds()) {
				employees.add(employeeService.convertDtoToEntity(employeeService.getEmployeeById(emplId)));
			}
		}
		schedule.setPets(petList);
		schedule.setEmployees(employees);

		return schedule;
	}

	public ScheduleDTO convertEntityToDto(Schedule schedule) {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		BeanUtils.copyProperties(schedule, scheduleDTO);
		List<Long> petIDsList = new ArrayList<>();

		if (schedule.getPets() != null && schedule.getPets().size() > 0) {
			for (Pet pet : schedule.getPets()) {
				petIDsList.add(pet.getId());
			}
		}

		List<Long> employeeIDslist = new ArrayList<>();
		if (schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
			for (Employee empl : schedule.getEmployees()) {
				employeeIDslist.add(empl.getId());
			}
		}

		scheduleDTO.setEmployeeIds(employeeIDslist);
		scheduleDTO.setPetIds(petIDsList);
		scheduleDTO.setDate(schedule.getDate());
		scheduleDTO.setActivities(schedule.getActivities());

		return scheduleDTO;
	}

}
