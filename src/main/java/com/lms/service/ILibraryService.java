package com.lms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lms.dto.LibraryDto;

public interface ILibraryService {

    ResponseEntity<LibraryDto> saveLibrary(
            LibraryDto libraryDto,
            int addressId);

    ResponseEntity<LibraryDto> findLibraryById(
            int libraryId);

    ResponseEntity<List<LibraryDto>> findAllLibraries();

    ResponseEntity<LibraryDto> updateLibrary(
            LibraryDto libraryDto);

    ResponseEntity<String> deleteLibrary(
            int libraryId);
    ResponseEntity<LibraryDto> addBookToLibrary(
            int libraryId, int bookId);
}