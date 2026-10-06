package com.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Integer> {

	 Optional<Library> findByAddress_AddressId(int addressId);
}
