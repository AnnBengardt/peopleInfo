package com.Anna.peopleInfo.service;

import com.Anna.peopleInfo.entity.Person;
import com.Anna.peopleInfo.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepo personRepo;

    public void create(Person person){
        personRepo.save(person);
    }

    public void update(Person person) { personRepo.save(person); }

    public void delete(Person person) { personRepo.delete(person); }

    public List<Person> findAll(){
        return personRepo.findAll();
    }

    public Optional<Person> find(Long id){
        return personRepo.findById(id);
    }

}
