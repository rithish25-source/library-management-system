package com.lms.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "libraryid_generator")
    @SequenceGenerator(name = "libraryid_generator",
            initialValue = 501, allocationSize = 1)
    private Integer libraryId; // pk - non primitive datatype

    private String libraryName;

    @Column(unique = true)
    private long phoneNumber;

    @OneToOne
    @JoinColumn(name = "address_id") // fk col name
    private Address address;

    @OneToMany
    @JoinColumn(name = "library_id") // avoid third table
    private List<Book> books;
}