package com.lms.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lms.dto.BookDto;
import com.lms.entity.Book;
import com.lms.entity.User;
import com.lms.exception.BookNotFoundException;
import com.lms.exception.UserNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.UserRepository;
import com.lms.service.IBookService;
import com.lms.util.LibraryMapper;

@Service
public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryMapper mapper;
    
  
    
    


    // =====================================================
    // SAVE BOOK
    // =====================================================

    @Override
    public ResponseEntity<BookDto> saveBook(BookDto bookDto) {

        // Convert DTO to Entity

        Book book =
                mapper.convertBookDtoToEntity(bookDto);


        // Save book first

        Book savedBook =
                bookRepository.save(book);


        // Assign users if userIds are provided

        if (bookDto.getUserIds() != null) {

            List<User> users =
                    new ArrayList<>();


            for (Integer userId :
                    bookDto.getUserIds()) {

                Optional<User> optionalUser =
                        userRepository.findById(userId);


                if (optionalUser.isEmpty()) {

                    throw new UserNotFoundException(
                            "User with id "
                            + userId
                            + " not found");
                }

                User user =
                        optionalUser.get();

                user.getBooks()
                        .add(savedBook);

                userRepository.save(user);

                users.add(user);
            }


            savedBook.setUsers(users);
        }


        // Convert Entity to DTO

        BookDto savedDto =
                convertToDto(savedBook);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDto);
    }


    // =====================================================
    // FIND BOOK BY ID
    // =====================================================

    @Override
    public ResponseEntity<BookDto> findBookById(int bookId) {

        Optional<Book> optionalBook =
                bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {

            throw new BookNotFoundException(
                    "Book for this id not found");
        }

        Book book =
                optionalBook.get();

        BookDto bookDto =
                convertToDto(book);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDto);
    }
    // =====================================================
    // FIND ALL BOOKS
    // =====================================================

    @Override
    public ResponseEntity<List<BookDto>> findAllBooks() {

        List<Book> books =
                bookRepository.findAll();


        List<BookDto> bookDtos =
                new ArrayList<>();


        for (Book book : books) {

            bookDtos.add(
                    convertToDto(book));
        }


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDtos);
    }


    // =====================================================
    // UPDATE BOOK
    // =====================================================

    @Override
    public ResponseEntity<BookDto> updateBook(
            BookDto bookDto) {

        // ==========================================
        // 1. FIND BOOK
        // ==========================================

        Optional<Book> optionalBook =
                bookRepository.findById(
                        bookDto.getBookId());

        if (optionalBook.isEmpty()) {

            throw new BookNotFoundException(
                    "Book for this id not found");
        }

        Book book =
                optionalBook.get();


        // ==========================================
        // 2. UPDATE BOOK DETAILS
        // ==========================================

        book.setTitle(
                bookDto.getTitle());

        book.setAuthor(
                bookDto.getAuthor());

        book.setCategory(
                bookDto.getCategory());

        book.setBorrowTime(
                bookDto.getBorrowTime());

        book.setReturnTime(
                bookDto.getReturnTime());

        book.setNumberOfCopy(
                bookDto.getNumberOfCopy());


        // ==========================================
        // 3. UPDATE USER RELATIONSHIP
        // ==========================================

        if (bookDto.getUserIds() != null) {

            // --------------------------------------
            // Remove this book from old users
            // --------------------------------------

            if (book.getUsers() != null) {

                for (User oldUser :
                        new ArrayList<>(book.getUsers())) {

                    oldUser.getBooks()
                            .remove(book);

                    userRepository.save(oldUser);
                }

                // Clear old users from book
                book.getUsers().clear();
            }


            // --------------------------------------
            // Add book to new users
            // --------------------------------------

            for (Integer userId :
                    bookDto.getUserIds()) {

                Optional<User> optionalUser =
                        userRepository.findById(userId);

                if (optionalUser.isPresent()) {

                    User newUser =
                            optionalUser.get();

                    // User is the owning side
                    newUser.getBooks()
                            .add(book);

                    userRepository.save(newUser);

                    // Maintain both sides in memory
                    book.getUsers()
                            .add(newUser);
                }
            }
        }


        // ==========================================
        // 4. SAVE UPDATED BOOK
        // ==========================================

        Book updatedBook =
                bookRepository.save(book);


        // ==========================================
        // 5. CONVERT ENTITY TO DTO
        // ==========================================

        BookDto updatedDto =
                convertToDto(updatedBook);


        // ==========================================
        // 6. RETURN RESPONSE
        // ==========================================

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDto);
    }


    // =====================================================
    // DELETE BOOK
    // =====================================================

    @Override
    public ResponseEntity<String> deleteBook(
            int bookId) {

        Optional<Book> optionalBook =
                bookRepository.findById(bookId);


        if (optionalBook.isPresent()) {

            Book book =
                    optionalBook.get();


            // ---------------------------------------------
            // Remove ManyToMany relationship
            // ---------------------------------------------

            if (book.getUsers() != null) {

                for (User user :
                        new ArrayList<>(book.getUsers())) {

                    user.getBooks()
                            .remove(book);

                    userRepository.save(user);
                }
            }


            // ---------------------------------------------
            // Delete Book
            // ---------------------------------------------

            bookRepository.deleteById(bookId);


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Book Deleted Successfully");
        }


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book Not Found");
    }


    // =====================================================
    // ENTITY → DTO
    // =====================================================

    private BookDto convertToDto(Book book) {

        BookDto dto =
                new BookDto();


        dto.setBookId(
                book.getBookId());


        dto.setTitle(
                book.getTitle());


        dto.setAuthor(
                book.getAuthor());


        dto.setCategory(
                book.getCategory());


        dto.setBorrowTime(
                book.getBorrowTime());


        dto.setReturnTime(
                book.getReturnTime());


        dto.setNumberOfCopy(
                book.getNumberOfCopy());


        // ---------------------------------------------
        // Convert Users → User IDs
        // ---------------------------------------------

        if (book.getUsers() != null) {

            List<Integer> userIds =
                    new ArrayList<>();


            for (User user :
                    book.getUsers()) {

                userIds.add(
                        user.getUserId());
            }


            dto.setUserIds(userIds);
        }


        return dto;
    }
    
    @Override
    public ResponseEntity<BookDto> findBookByTitle(String title){
    	 Optional<Book> optionalBook =
                 bookRepository.findByTitle(title);
    	 
    	 HttpHeaders headers= new HttpHeaders() ;

         if (optionalBook.isPresent()) {
        	 
        	 headers.add("info", "Book Found Successfully");
              Book dbBook=optionalBook.get();
        	 
        	 BookDto bookDto=mapper.convertBookToDto(dbBook);
        	 return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookDto);
         }

         else {
         
        	 throw new BookNotFoundException("Book for this title not found");
         }
        
    }

    @Override
    public ResponseEntity<List<BookDto>> findBookByAuthor(String author) {

        List<Book> books =
                bookRepository.findByAuthor(author);

        if (books.isEmpty()) {

            throw new BookNotFoundException(
                    "No books found for this author"
            );
        }

        List<BookDto> bookDtos =
                new ArrayList<>();

        for (Book book : books) {

            bookDtos.add(
                    convertToDto(book)
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDtos);
    }


    @Override
    public ResponseEntity<BookDto> findBookByTitleAndAuthor(
            String title,
            String author) {

        Optional<Book> optionalBook =
                bookRepository.findByTitleAndAuthor(
                        title,
                        author
                );

        if (optionalBook.isEmpty()) {

            throw new BookNotFoundException(
                    "Book for this title and author not found"
            );
        }

        Book book =
                optionalBook.get();

        BookDto bookDto =
                convertToDto(book);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDto);
    }


	@Override
	public ResponseEntity<List<BookDto>> findBookByCategory(
	        String category) {

	    List<Book> books =
	            bookRepository.findByCategory(category);

	    if (books.isEmpty()) {

	        throw new BookNotFoundException(
	                "No books found for this category"
	        );
	    }

	    List<BookDto> bookDtos =
	            new ArrayList<>();

	    for (Book book : books) {

	        bookDtos.add(
	                convertToDto(book)
	        );
	    }

	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(bookDtos);
	}
	
	@Override
	public ResponseEntity<BookDto> borrowBook(int bookId, int userId) {

	    Optional<Book> optionalBook =
	            bookRepository.findById(bookId);

	    if (optionalBook.isEmpty()) {
	        throw new BookNotFoundException(
	                "Book with id " + bookId + " not found");
	    }

	    Optional<User> optionalUser =
	            userRepository.findById(userId);

	    if (optionalUser.isEmpty()) {
	        throw new UserNotFoundException(
	                "User with id " + userId + " not found");
	    }

	    Book book = optionalBook.get();
	    User user = optionalUser.get();

	    int numberOfCopy = book.getNumberOfCopy();

	    if (numberOfCopy <= 0) {
	        throw new RuntimeException(
	                "Book is not available. No copies remaining");
	    }

	    // Decrease available copies
	    book.setNumberOfCopy(numberOfCopy - 1);

	    // Set borrow time
	    book.setBorrowTime(LocalDateTime.now());

	    // Add book to user
	    List<Book> userBookList = user.getBooks();

	    if (userBookList == null) {
	        userBookList = new ArrayList<>();
	    }

	    userBookList.add(book);
	    user.setBooks(userBookList);

	    // Add user to book
	    List<User> bookUserList = book.getUsers();

	    if (bookUserList == null) {
	        bookUserList = new ArrayList<>();
	    }

	    bookUserList.add(user);
	    book.setUsers(bookUserList);

	    // Save both
	    userRepository.save(user);
	    bookRepository.save(book);

	    // Response header
	    HttpHeaders headers = new HttpHeaders();

	    headers.add(
	            "info",
	            "Book Borrowed By User Successfully");

	    // Convert Entity → DTO
	    BookDto bookDto =
	            mapper.convertBookToDto(book);

	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .headers(headers)
	            .body(bookDto);
	}
	
	@Override
	public ResponseEntity<BookDto> returnBook(int bookId, int userId) {

	    // ==========================================
	    // 1. FIND BOOK
	    // ==========================================

	    Optional<Book> optionalBook =
	            bookRepository.findById(bookId);

	    if (optionalBook.isEmpty()) {

	        throw new BookNotFoundException(
	                "Book with id " + bookId + " not found");
	    }


	    // ==========================================
	    // 2. FIND USER
	    // ==========================================

	    Optional<User> optionalUser =
	            userRepository.findById(userId);

	    if (optionalUser.isEmpty()) {

	        throw new UserNotFoundException(
	                "User with id " + userId + " not found");
	    }


	    Book book = optionalBook.get();

	    User user = optionalUser.get();


	    // ==========================================
	    // 3. CHECK WHETHER USER HAS THIS BOOK
	    // ==========================================

	    if (user.getBooks() == null ||
	            !user.getBooks().contains(book)) {

	        throw new RuntimeException(
	                "This user has not borrowed this book");
	    }


	    // ==========================================
	    // 4. INCREASE AVAILABLE COPY
	    // ==========================================

	    int numberOfCopy =
	            book.getNumberOfCopy();

	    book.setNumberOfCopy(
	            numberOfCopy + 1);


	    // ==========================================
	    // 5. SET RETURN TIME
	    // ==========================================

	    book.setReturnTime(
	            LocalDateTime.now());


	    // ==========================================
	    // 6. REMOVE BOOK FROM USER
	    // ==========================================

	    user.getBooks().remove(book);


	    // ==========================================
	    // 7. REMOVE USER FROM BOOK
	    // ==========================================

	    if (book.getUsers() != null) {

	        book.getUsers().remove(user);
	    }


	    // ==========================================
	    // 8. SAVE USER
	    // ==========================================

	    userRepository.save(user);


	    // ==========================================
	    // 9. SAVE BOOK
	    // ==========================================

	    bookRepository.save(book);


	    // ==========================================
	    // 10. RESPONSE HEADER
	    // ==========================================

	    HttpHeaders headers =
	            new HttpHeaders();

	    headers.add(
	            "info",
	            "Book Returned By User Successfully");


	    // ==========================================
	    // 11. ENTITY → DTO
	    // ==========================================

	    BookDto bookDto =
	            mapper.convertBookToDto(book);


	    // ==========================================
	    // 12. RETURN RESPONSE
	    // ==========================================

	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .headers(headers)
	            .body(bookDto);
	}
}