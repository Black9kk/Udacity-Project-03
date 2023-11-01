package com.udacity.jdnd.course3.critter.entity;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Nationalized;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nationalized
	private String name;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills = new HashSet<>();

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysAvailable = new HashSet<>();

}
