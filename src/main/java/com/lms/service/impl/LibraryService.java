package com.lms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lms.dto.LibraryDto;
import com.lms.entity.Address;
import com.lms.entity.Book;
import com.lms.entity.Library;
import com.lms.entity.User;
import com.lms.exception.AddressAlreadyAssignedException;
import com.lms.exception.AddressNotFoundException;
import com.lms.exception.BookNotFoundException;
import com.lms.exception.LibraryNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.repository.BookRepository;
import com.lms.repository.LibraryRepository;
import com.lms.repository.UserRepository;
import com.lms.service.ILibraryService;
import com.lms.util.LibraryMapper;

@Service
public class LibraryService
        implements ILibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryMapper mapper;
    
    @Autowired
    UserRepository userRepository;


    // =====================================================
    // SAVE LIBRARY
    // =====================================================

    @Override
    public ResponseEntity<LibraryDto> saveLibrary(
            LibraryDto libraryDto,
            int addressId) {


        // ---------------------------------------------
        // Find Address
        // ---------------------------------------------

        Optional<Address> optionalAddress =
                addressRepository.findById(addressId);

        if (optionalAddress.isEmpty()) {

            throw new AddressNotFoundException(
                    "Address for this id not found");
        }

        Address address =
                optionalAddress.get();


        Optional<User> optionalUser =
                userRepository.findByAddress_AddressId(addressId);

        if (optionalUser.isPresent()) {

            User existingUser = optionalUser.get();

            throw new AddressAlreadyAssignedException(
                    "Address already connected to userId "
                    + existingUser.getUserId()
                    + ". Cannot assign this address to a library."
            );}
            
        // ---------------------------------------------
        // Convert DTO → Entity
        // ---------------------------------------------

        Library library =
                mapper.convertLibraryDtoToEntity(
                        libraryDto);


        // Set Address
        library.setAddress(address);


        // ---------------------------------------------
        // Assign Books
        // ---------------------------------------------

        if (libraryDto.getBookIds() != null) {

            List<Book> books =
                    new ArrayList<>();

            for (Integer bookId :
                    libraryDto.getBookIds()) {

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

            library.setBooks(books);
        }


        // ---------------------------------------------
        // Save Library
        // ---------------------------------------------

        Library savedLibrary =
                libraryRepository.save(library);
        
        


        // ---------------------------------------------
        // Convert Entity → DTO
        // ---------------------------------------------

        LibraryDto savedDto =
                convertToDto(savedLibrary);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDto);
    }

    // =====================================================
    // FIND LIBRARY BY ID
    // =====================================================

    @Override
    public ResponseEntity<LibraryDto> findLibraryById(
            int libraryId) {

        Optional<Library> optionalLibrary =
                libraryRepository.findById(libraryId);

        if (optionalLibrary.isEmpty()) {

            throw new LibraryNotFoundException(
                    "Library for this id not found");
        }

        Library library =
                optionalLibrary.get();

        LibraryDto libraryDto =
                convertToDto(library);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(libraryDto);
    }


    // =====================================================
    // FIND ALL LIBRARIES
    // =====================================================

    @Override
    public ResponseEntity<List<LibraryDto>>
            findAllLibraries() {

        List<Library> libraries =
                libraryRepository.findAll();

        List<LibraryDto> libraryDtos =
                new ArrayList<>();


        for (Library library :
                libraries) {

            libraryDtos.add(
                    convertToDto(library));
        }


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(libraryDtos);
    }


    // =====================================================
    // UPDATE LIBRARY
    // =====================================================

    @Override
    public ResponseEntity<LibraryDto> updateLibrary(
            LibraryDto libraryDto) {


        Optional<Library> optionalLibrary =
                libraryRepository.findById(
                        libraryDto.getLibraryId());

        if (optionalLibrary.isEmpty()) {

            throw new LibraryNotFoundException(
                    "Library for this id not found");
        }

        Library library =
                optionalLibrary.get();


        // ---------------------------------------------
        // Update basic details
        // ---------------------------------------------

        library.setLibraryName(
                libraryDto.getLibraryName());

        library.setPhoneNumber(
                libraryDto.getPhoneNumber());


        // ---------------------------------------------
        // Update Address
        // ---------------------------------------------

        if (libraryDto.getAddressId() != null) {

            Optional<Address> optionalAddress =
                    addressRepository.findById(
                            libraryDto.getAddressId());

            if (optionalAddress.isEmpty()) {

                throw new AddressNotFoundException(
                        "Address for this id not found");
            }

            library.setAddress(
                    optionalAddress.get());
        }


        // ---------------------------------------------
        // Update Books
        // ---------------------------------------------

        if (libraryDto.getBookIds() != null) {

            List<Book> books =
                    new ArrayList<>();

            for (Integer bookId :
                    libraryDto.getBookIds()) {

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

            library.setBooks(books);
        }


        // ---------------------------------------------
        // Save updated Library
        // ---------------------------------------------

        Library updatedLibrary =
                libraryRepository.save(library);


        // ---------------------------------------------
        // Convert Entity → DTO
        // ---------------------------------------------

        LibraryDto updatedDto =
                convertToDto(updatedLibrary);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDto);
    }


    // =====================================================
    // DELETE LIBRARY
    // =====================================================

    @Override
    public ResponseEntity<String> deleteLibrary(
            int libraryId) {

        Optional<Library> optionalLibrary =
                libraryRepository.findById(
                        libraryId);

        if (optionalLibrary.isEmpty()) {

            throw new LibraryNotFoundException(
                    "Library for this id not found");
        }

        Library library =
                optionalLibrary.get();


        // ---------------------------------------------
        // Remove Books from Library
        // ---------------------------------------------

        if (library.getBooks() != null) {

            library.getBooks().clear();

            libraryRepository.save(library);
        }


        // ---------------------------------------------
        // Delete Library
        // ---------------------------------------------

        libraryRepository.deleteById(libraryId);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Library Deleted Successfully");
    }


    // =====================================================
    // ENTITY → DTO
    // =====================================================

    private LibraryDto convertToDto(
            Library library) {

        LibraryDto dto =
                new LibraryDto();


        dto.setLibraryId(
                library.getLibraryId());

        dto.setLibraryName(
                library.getLibraryName());

        dto.setPhoneNumber(
                library.getPhoneNumber());


        // ---------------------------------------------
        // Address
        // ---------------------------------------------

        if (library.getAddress() != null) {

            dto.setAddressId(
                    library.getAddress()
                            .getAddressId());
        }


        // ---------------------------------------------
        // Books
        // ---------------------------------------------

        if (library.getBooks() != null) {

            List<Integer> bookIds =
                    new ArrayList<>();

            for (Book book :
                    library.getBooks()) {

                bookIds.add(
                        book.getBookId());
            }

            dto.setBookIds(bookIds);
        }


        return dto;
    }
    
    @Override
    public ResponseEntity<LibraryDto> addBookToLibrary(int libraryId, int bookId){
    	
    	Optional<Book> optionalBook = bookRepository.findById(bookId);
    	Optional<Library> optionalLibrary = libraryRepository.findById(libraryId);
    	
    	if(optionalBook.isPresent() && optionalLibrary.isPresent()) {
    		//get entity from optional
    		
    		Book book = optionalBook.get();
    		Library library = optionalLibrary.get();
    		//for library old list of books might be there get that
    		List<Book> bookList = library.getBooks();
    		//if old list is not there create new list
    		if(bookList==null) {
    			bookList=new ArrayList<>();
    		}
    		//to the list add current book
    		bookList.add(book);
    		
    		//set the list to library
    		library.setBooks(bookList);
    		//update library
    		libraryRepository.save(library);
    		LibraryDto libraryDto=mapper.convertLibraryToDto(library);
    		return ResponseEntity.status(HttpStatus.OK).body(libraryDto);
    		
    	}
    	else {
    		return null;
    	}
    }
}