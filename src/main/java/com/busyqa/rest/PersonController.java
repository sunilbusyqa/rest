package com.busyqa.rest;

import com.busyqa.rest.domain.Person;
import com.busyqa.rest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> findAll(@RequestParam(required = false) Character startsWith) {
        if (startsWith == null) {
            return personService.findAll();
        } else {
            List<Person> persons = personService.findAll();
            return  persons.stream()
                    .filter(person -> person.getFirstName().startsWith(startsWith.toString()))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public Person findPerson(@PathVariable int id) {
        return personService.findById(id);
    }

    @PostMapping
    public void createPerson(@RequestBody Person person) {
        personService.save(person);
    }

    @PutMapping("/{id}")
    public void updatePerson(@RequestBody Person person, @PathVariable Integer id) {
        if (id == null) {
            throw new RuntimeException("ID must be provided to update a person");
        }
        Person currentPerson = personService.findById(id);
        if (currentPerson == null) {
            throw new RuntimeException("Person not found");
        }
        currentPerson.setFirstName(person.getFirstName());
        currentPerson.setLastName(person.getLastName());
        personService.save(currentPerson);
    }


    @PatchMapping("/{id}")
    public void patchPerson(@RequestBody Person person, @PathVariable Integer id) {
        if (id == null) {
            throw new RuntimeException("ID must be provided to update a person");
        }
        Person currentPerson = personService.findById(id);
        if (currentPerson == null) {
            throw new RuntimeException("Person not found");
        }

        if (person.getFirstName() != null) {
            currentPerson.setFirstName(person.getFirstName());
        }
        if (person.getLastName() != null) {
            currentPerson.setLastName(person.getLastName());
        }
        personService.save(currentPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Integer id) {
        if (id == null) {
            throw new RuntimeException("ID must be provided to update a person");
        }
        personService.delete(id);
    }
}
