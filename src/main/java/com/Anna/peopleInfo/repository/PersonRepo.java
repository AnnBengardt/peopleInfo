package com.Anna.peopleInfo.repository;

import com.Anna.peopleInfo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
}
