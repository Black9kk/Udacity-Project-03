package com.udacity.jdnd.course3.critter.dto;

import java.time.DayOfWeek;
import java.util.Set;

import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
public class EmployeeDTO {
    private long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
}