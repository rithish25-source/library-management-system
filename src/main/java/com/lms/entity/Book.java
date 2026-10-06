package com.lms.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "bookid_generator")
    @SequenceGenerator(
            name = "bookid_generator",
            initialValue = 301,
            allocationSize = 1
    )
    private Integer bookId;

    private String title;

    private String author;

    private String category;

    private LocalDateTime borrowTime;

    private LocalDateTime returnTime;

    private int numberOfCopy;


    // User <----> Book

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

}