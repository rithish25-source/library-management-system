package com.lms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lms.dto.UserDto;
import com.lms.entity.Address;
import com.lms.entity.Book;
import com.lms.entity.Library;
import com.lms.entity.User;
import com.lms.exception.AddressAlreadyAssignedException;
import com.lms.exception.AddressNotFoundException;
import com.lms.exception.BookNotFoundException;
import com.lms.exception.UserNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.repository.BookRepository;
import com.lms.repository.LibraryRepository;
import com.lms.repository.UserRepository;
import com.lms.service.IUserService;
import com.lms.util.LibraryMapper;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryMapper mapper;
    @Autowired
    LibraryRepository libraryRepository;

    // =====================================================
    // SAVE USER
    // =====================================================

    @Override
    public ResponseEntity<UserDto> saveUser(
            UserDto userDto,
            int addressId) {

        // Find Address
        Optional<Address> optionalAddress =
                addressRepository.findById(addressId);

        if (optionalAddress.isEmpty()) {

            throw new AddressNotFoundException(
                    "Address for this id not found");
        }

        Address address =
                optionalAddress.get();

        Optional<Library> optionalLibrary =
                libraryRepository.findByAddress_AddressId(addressId);

        if (optionalLibrary.isPresent()) {

            Library existingLibrary = optionalLibrary.get();

            throw new AddressAlreadyAssignedException(
                    "Library is located at this address. libraryId: "
                    + existingLibrary.getLibraryId()
                    + ". Cannot assign this address to a user."
            );
        }
        // Convert DTO to Entity
        User user =
                mapper.convertUserDtoToEntity(userDto);

        // Set Address
        user.setAddress(address);


        // ---------------------------------------------
        // Assign Books
        // ---------------------------------------------

        if (userDto.getBookIds() != null) {

            List<Book> books =
                    new ArrayList<>();

            for (Integer bookId :
                    userDto.getBookIds()) {

                Optional<Book> optionalBook =
                        bookRepository.findById(bookId);

                if (optionalBook.isEmpty()) {

                    throw new BookNotFoundException(
                            "Book with id "
                            + bookId
                            + " not found");
                }

                books.add(
                        optionalBook.get());
            }

            user.setBooks(books);
        }


        // Save User
        User savedUser =
                userRepository.save(user);


        // Convert Entity to DTO
        UserDto savedDto =
                convertToDto(savedUser);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDto);
    }


    // =====================================================
    // FIND USER BY ID
    // =====================================================

    @Override
    public ResponseEntity<UserDto> findUserById(
            int userId) {

        Optional<User> optionalUser =
                userRepository.findById(userId);

        if (optionalUser.isEmpty()) {

            throw new UserNotFoundException(
                    "User for this id not found");
        }

        User user =
                optionalUser.get();

        UserDto userDto =
                convertToDto(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }


    // =====================================================
    // FIND ALL USERS
    // =====================================================

    @Override
    public ResponseEntity<List<UserDto>> findAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<UserDto> userDtos =
                new ArrayList<>();

        for (User user :
                users) {

            userDtos.add(
                    convertToDto(user));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }


    // =====================================================
    // UPDATE USER
    // =====================================================

    @Override
    public ResponseEntity<UserDto> updateUser(
            UserDto userDto) {

        Optional<User> optionalUser =
                userRepository.findById(
                        userDto.getUserId());

        if (optionalUser.isEmpty()) {

            throw new UserNotFoundException(
                    "User for this id not found");
        }

        User user =
                optionalUser.get();


        // ---------------------------------------------
        // Update basic details
        // ---------------------------------------------

        user.setUserName(
                userDto.getUserName());

        user.setPhoneNumber(
                userDto.getPhoneNumber());

        user.setEmail(
                userDto.getEmail());


        // ---------------------------------------------
        // Update Address
        // ---------------------------------------------

        if (userDto.getAddressId() != null) {

            Optional<Address> optionalAddress =
                    addressRepository.findById(
                            userDto.getAddressId());

            if (optionalAddress.isEmpty()) {

                throw new AddressNotFoundException(
                        "Address for this id not found");
            }

            user.setAddress(
                    optionalAddress.get());
        }


        // ---------------------------------------------
        // Update Books
        // ---------------------------------------------

        if (userDto.getBookIds() != null) {

            List<Book> books =
                    new ArrayList<>();

            for (Integer bookId :
                    userDto.getBookIds()) {

                Optional<Book> optionalBook =
                        bookRepository.findById(bookId);

                if (optionalBook.isEmpty()) {

                    throw new BookNotFoundException(
                            "Book with id "
                            + bookId
                            + " not found");
                }

                books.add(
                        optionalBook.get());
            }

            user.setBooks(books);
        }


        // Save updated User
        User updatedUser =
                userRepository.save(user);


        // Convert Entity to DTO
        UserDto updatedDto =
                convertToDto(updatedUser);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDto);
    }


    // =====================================================
    // DELETE USER
    // =====================================================

    @Override
    public ResponseEntity<String> deleteUser(
            int userId) {

        Optional<User> optionalUser =
                userRepository.findById(userId);

        if (optionalUser.isEmpty()) {

            throw new UserNotFoundException(
                    "User for this id not found");
        }

        User user =
                optionalUser.get();


        // Remove ManyToMany relationship
        user.getBooks().clear();

        userRepository.save(user);


        // Delete User
        userRepository.deleteById(userId);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User Deleted Successfully");
    }


    // =====================================================
    // ENTITY → DTO
    // =====================================================

    private UserDto convertToDto(
            User user) {

        UserDto dto =
                new UserDto();

        dto.setUserId(
                user.getUserId());

        dto.setUserName(
                user.getUserName());

        dto.setPhoneNumber(
                user.getPhoneNumber());

        dto.setEmail(
                user.getEmail());


        // Address
        if (user.getAddress() != null) {

            dto.setAddressId(
                    user.getAddress()
                            .getAddressId());
        }


        // Books
        if (user.getBooks() != null) {

            List<Integer> bookIds =
                    new ArrayList<>();

            for (Book book :
                    user.getBooks()) {

                bookIds.add(
                        book.getBookId());
            }

            dto.setBookIds(bookIds);
        }

        return dto;
    }
}