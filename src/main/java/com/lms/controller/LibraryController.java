package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.LibraryDto;
import com.lms.service.ILibraryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/library")
@Tag(
    name = "Library APIs",
    description = "APIs related to Library Management"
)
public class LibraryController {

    @Autowired
    ILibraryService libraryService;


    // =================================================
    // SAVE LIBRARY
    // =================================================

    @Operation(
        operationId = "CreateLibrary",
        summary = "Adding Library",
        description = "This REST endpoint is used to create a library along with an existing address"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Library saved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid library details"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Address not found"
        )
    })
    @PostMapping("/addressId/{addressId}")
    public ResponseEntity<LibraryDto> saveLibrary(
            @Valid
            @RequestBody LibraryDto libraryDto,
            @PathVariable int addressId) {

        return libraryService.saveLibrary(
                libraryDto,
                addressId);
    }


    // =================================================
    // FIND LIBRARY BY ID
    // =================================================

    @Operation(
        operationId = "FetchLibrary",
        summary = "Fetch One Library",
        description = "This REST endpoint is used to fetch one library based on library ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Library fetched successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Library for this ID not found"
        )
    })
    @GetMapping("/{libraryId}")
    public ResponseEntity<LibraryDto> getLibraryById(
            @PathVariable int libraryId) {

        return libraryService.findLibraryById(
                libraryId);
    }


    // =================================================
    // FIND ALL LIBRARIES
    // =================================================

    @Operation(
        operationId = "FetchAllLibraries",
        summary = "Fetch All Libraries",
        description = "This REST endpoint is used to fetch all libraries"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All libraries fetched successfully"
        )
    })
    @GetMapping
    public ResponseEntity<List<LibraryDto>>
            getAllLibraries() {

        return libraryService.findAllLibraries();
    }


    // =================================================
    // UPDATE LIBRARY
    // =================================================

    @Operation(
        operationId = "UpdateLibrary",
        summary = "Update Library",
        description = "This REST endpoint is used to update an existing library"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Library updated successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Library not found"
        )
    })
    @PutMapping
    public ResponseEntity<LibraryDto> updateLibrary(
            @RequestBody LibraryDto libraryDto) {

        return libraryService.updateLibrary(
                libraryDto);
    }


    // =================================================
    // DELETE LIBRARY
    // =================================================

    @Operation(
        operationId = "DeleteLibrary",
        summary = "Delete Library",
        description = "This REST endpoint is used to delete a library using library ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Library deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Library not found"
        )
    })
    @DeleteMapping("/{libraryId}")
    public ResponseEntity<String> deleteLibrary(
            @PathVariable int libraryId) {

        return libraryService.deleteLibrary(
                libraryId);
    }


    // =================================================
    // ADD BOOK TO LIBRARY
    // =================================================

    @Operation(
        operationId = "AddBookToLibrary",
        summary = "Add Book To Library",
        description = "This REST endpoint is used to add a book to a library using library ID and book ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book added to library successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Library or Book not found"
        )
    })
    @PutMapping("/libraryId/{libraryId}/bookId/{bookId}")
    public ResponseEntity<LibraryDto> addBookToLibrary(
            @PathVariable int libraryId,
            @PathVariable int bookId) {

        return libraryService.addBookToLibrary(
                libraryId,
                bookId);
    }

}