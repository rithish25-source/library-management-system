package com.lms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lms.dto.AddressDto;
import com.lms.entity.Address;

public interface IAddressService {

	public ResponseEntity<AddressDto> saveAddress(AddressDto addressDto);
	public void updateAddress(AddressDto addressDto);
	public void deleteAddress(int addressId);
	public Address findAddressById(int addressId);
	public List<Address> findAllAddress();
}
