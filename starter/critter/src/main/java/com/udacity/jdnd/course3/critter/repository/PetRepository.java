package com.udacity.jdnd.course3.critter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
