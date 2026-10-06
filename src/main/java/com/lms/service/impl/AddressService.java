package com.lms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.dto.AddressDto;
import com.lms.entity.Address;
import com.lms.entity.Library;
import com.lms.entity.User;
import com.lms.exception.AddressNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.repository.LibraryRepository;
import com.lms.repository.UserRepository;
import com.lms.service.IAddressService;
import com.lms.util.LibraryMapper;

@Service
public class AddressService implements IAddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryMapper mapper;


    // ==========================================
    // SAVE ADDRESS
    // ==========================================

    @Override
    public ResponseEntity<AddressDto> saveAddress(AddressDto addressDto) {

        Address address =
                mapper.convertAddressDtoToEntity(addressDto);

        Address savedAddress =
                addressRepository.save(address);

        AddressDto savedAddressDto =
                mapper.convertAddressToDto(savedAddress);

        return ResponseEntity.ok(savedAddressDto);
    }


    // ==========================================
    // UPDATE ADDRESS
    // ==========================================

    @Override
    public void updateAddress(AddressDto addressDto) {

        Optional<Address> optionalAddress =
                addressRepository.findById(
                        addressDto.getAddressId());

        if (optionalAddress.isEmpty()) {

            throw new AddressNotFoundException(
                    "Address for this id not found");
        }

        Address address =
                optionalAddress.get();

        address.setHouseNumber(
                addressDto.getHouseNumber());

        address.setArea(
                addressDto.getArea());

        address.setCity(
                addressDto.getCity());

        address.setState(
                addressDto.getState());

        address.setCountry(
                addressDto.getCountry());

        address.setPinCode(
                addressDto.getPinCode());

        addressRepository.save(address);
    }


    // ==========================================
    // DELETE ADDRESS
    // ==========================================

    @Override
    @Transactional
    public void deleteAddress(int addressId) {

        // --------------------------------------
        // 1. Check whether Address exists
        // --------------------------------------

        Optional<Address> optionalAddress =
                addressRepository.findById(addressId);

        if (optionalAddress.isEmpty()) {

            throw new AddressNotFoundException(
                    "Address for this id not found");
        }


        // --------------------------------------
        // 2. Check User relationship
        // --------------------------------------

        Optional<User> optionalUser =
                userRepository.findByAddress_AddressId(
                        addressId);

        if (optionalUser.isPresent()) {

            User user =
                    optionalUser.get();

            // Remove address from User
            user.setAddress(null);

            userRepository.save(user);
        }


        // --------------------------------------
        // 3. Check Library relationship
        // --------------------------------------

        Optional<Library> optionalLibrary =
                libraryRepository.findByAddress_AddressId(
                        addressId);

        if (optionalLibrary.isPresent()) {

            Library library =
                    optionalLibrary.get();

            // Remove address from Library
            library.setAddress(null);

            libraryRepository.save(library);
        }


        // --------------------------------------
        // 4. Delete Address
        // --------------------------------------

        addressRepository.deleteById(addressId);
    }


    // ==========================================
    // FIND ADDRESS BY ID
    // ==========================================

    @Override
    public Address findAddressById(int addressId) {

        Optional<Address> optionalAddress =
                addressRepository.findById(addressId);

        if (optionalAddress.isPresent()) {

            return optionalAddress.get();
        }

        throw new AddressNotFoundException(
                "Address for this id not found");
    }


    // ==========================================
    // FIND ALL ADDRESS
    // ==========================================

    @Override
    public List<Address> findAllAddress() {

        return addressRepository.findAll();
    }
}