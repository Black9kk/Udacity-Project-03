package com.udacity.jdnd.course3.critter.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServices {

	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeDTO getEmployeeById(Long id) {
		Employee empoloyee = employeeRepository.findById(id).orElse(new Employee());
		return convertEntityToDto(empoloyee);
	}

	public void setDayAvaiable(Set<DayOfWeek> dayOfWeeksAvaiable, Long id) {
		Employee employee = employeeRepository.findById(id).orElse(new Employee());
		employee.setDaysAvailable(dayOfWeeksAvaiable);
		employeeRepository.save(employee);
	}

	public EmployeeDTO saveEmployee(EmployeeDTO employeeDto) {
		Employee employee = employeeRepository.save(convertDtoToEntity(employeeDto));
		return convertEntityToDto(employee);
	}

	public List<EmployeeDTO> findByDayAvaiable(EmployeeRequestDTO employeeRequestDTO) {
		List<EmployeeDTO> employeeDtoList = new ArrayList<>();
		Set<EmployeeSkill> employeeSkills = employeeRequestDTO.getSkills();
		DayOfWeek daysAvailable = employeeRequestDTO.getDate().getDayOfWeek();
		List<Employee> employeeList = employeeRepository.findByDaysAvailable(daysAvailable);
		for (Employee employee : employeeList) {
			if (employee.getSkills().containsAll(employeeSkills)) {
				employeeDtoList.add(convertEntityToDto(employee));
			}
		}
		return employeeDtoList;
	}

	private EmployeeDTO convertEntityToDto(Employee employee) {
		EmployeeDTO employeeDto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDto);
		return employeeDto;
	}

	public Employee convertDtoToEntity(EmployeeDTO employeeDto) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDto, employee);
		return employee;
	}

}
