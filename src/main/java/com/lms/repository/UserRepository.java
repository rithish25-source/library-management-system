package com.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer>{

	Optional<User> findByAddress_AddressId(int addressId);
}
