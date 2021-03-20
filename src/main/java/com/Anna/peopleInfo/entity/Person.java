package com.Anna.peopleInfo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private Date birthday;
    private Integer postalCode;

}
