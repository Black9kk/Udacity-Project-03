package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToMany
	private List<Employee> employees = new ArrayList<>();

	@ManyToMany
	private List<Pet> pets = new ArrayList<>();

	private LocalDate date;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> activities;
}
