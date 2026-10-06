package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.BookDto;
import com.lms.service.IBookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
@Tag(
    name = "Book APIs",
    description = "APIs related to Book Management"
)
public class BookController {


    @Autowired
    IBookService bookService;


    // =================================================
    // SAVE BOOK
    // =================================================

    @Operation(
        operationId = "CreateBook",
        summary = "Adding Book",
        description = "This REST endpoint is used to create a new book"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book saved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid book details"
        )
    })
    @PostMapping
    public ResponseEntity<BookDto> saveBook(
           @Valid @RequestBody BookDto bookDto) {

        return bookService.saveBook(bookDto);
    }


    // =================================================
    // FIND BOOK BY ID
    // =================================================

    @Operation(
        operationId = "FetchBook",
        summary = "Fetch One Book",
        description = "This REST endpoint is used to fetch one book based on book ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book fetched successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book for this ID not found"
        )
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(
            @PathVariable int bookId) {

        return bookService.findBookById(bookId);
    }


    // =================================================
    // FIND ALL BOOKS
    // =================================================

    @Operation(
        operationId = "FetchAllBooks",
        summary = "Fetch All Books",
        description = "This REST endpoint is used to fetch all books"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All books fetched successfully"
        )
    })
    @GetMapping
    public ResponseEntity<List<BookDto>>
            getAllBooks() {

        return bookService.findAllBooks();
    }


    // =================================================
    // UPDATE BOOK
    // =================================================

    @Operation(
        operationId = "UpdateBook",
        summary = "Update Book",
        description = "This REST endpoint is used to update an existing book"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book updated successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book not found"
        )
    })
    @PutMapping
    public ResponseEntity<BookDto> updateBook(
            @RequestBody BookDto bookDto) {

        return bookService.updateBook(
                bookDto);
    }


    // =================================================
    // DELETE BOOK
    // =================================================

    @Operation(
        operationId = "DeleteBook",
        summary = "Delete Book",
        description = "This REST endpoint is used to delete a book using book ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book not found"
        )
    })
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(
            @PathVariable int bookId) {

        return bookService.deleteBook(
                bookId);
    }


    // =================================================
    // FIND BOOK BY TITLE
    // =================================================

    @Operation(
        operationId = "FindBookByTitle",
        summary = "Find Book By Title",
        description = "This REST endpoint is used to find a book based on its title"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book found successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book for this title not found"
        )
    })
    @GetMapping("/title/{title}")
    public ResponseEntity<BookDto> findBookByTitle(
            @PathVariable String title) {

        return bookService.findBookByTitle(title);

    }


    // =================================================
    // FIND BOOK BY AUTHOR
    // =================================================

    @Operation(
        operationId = "FindBookByAuthor",
        summary = "Find Books By Author",
        description = "This REST endpoint is used to find books based on author name"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Books found successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No books found for this author"
        )
    })
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDto>> findBookByAuthor(
            @PathVariable String author) {

        return bookService.findBookByAuthor(author);
    }


    // =================================================
    // FIND BOOK BY CATEGORY
    // =================================================

    @Operation(
        operationId = "FindBookByCategory",
        summary = "Find Books By Category",
        description = "This REST endpoint is used to find books based on category"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Books found successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No books found for this category"
        )
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookDto>> findBookByCategory(
            @PathVariable String category) {

        return bookService.findBookByCategory(category);
    }


    // =================================================
    // FIND BOOK BY TITLE AND AUTHOR
    // =================================================

    @Operation(
        operationId = "FindBookByTitleAndAuthor",
        summary = "Find Book By Title And Author",
        description = "This REST endpoint is used to find a book using both title and author"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book found successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book for the given title and author not found"
        )
    })
    @GetMapping("/title/{title}/author/{author}")
    public ResponseEntity<BookDto> findBookByTitleAndAuthor(
            @PathVariable String title,
            @PathVariable String author) {

        return bookService.findBookByTitleAndAuthor(
                title,
                author);
    }


    // =================================================
    // BORROW BOOK
    // =================================================

    @Operation(
        operationId = "BorrowBook",
        summary = "Borrow Book",
        description = "This REST endpoint is used to borrow a book using book ID and user ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Book borrowed successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Book is not available"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Book or User not found"
        )
    })
    @PatchMapping("/bookId/{bookId}/userId/{userId}")
    public ResponseEntity<BookDto> borrowBook(
            @PathVariable int bookId,
            @PathVariable int userId) {

        return bookService.borrowBook(bookId, userId);

    }
    
    @Operation(
    	    operationId = "ReturnBook",
    	    summary = "Return Book",
    	    description = "This REST endpoint is used to return a borrowed book by a user"
    	)
    	@ApiResponses(value = {
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Book returned successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Book or User not found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "User has not borrowed this book"
    	    )
    	})
    @PatchMapping("/return/bookId/{bookId}/userId/{userId}")
    public ResponseEntity<BookDto> returnBook(
            @PathVariable int bookId,
            @PathVariable int userId) {

        return bookService.returnBook(
                bookId,
                userId);
    }

}