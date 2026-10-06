package com.lms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lms.dto.BookDto;

public interface IBookService {

    ResponseEntity<BookDto> saveBook(
            BookDto bookDto);

    ResponseEntity<BookDto> findBookById(
            int bookId);

    ResponseEntity<List<BookDto>> findAllBooks();

    ResponseEntity<BookDto> updateBook(
            BookDto bookDto);

    ResponseEntity<String> deleteBook(
            int bookId);
    //custom
    ResponseEntity<BookDto> findBookByTitle(String title);
    ResponseEntity<List<BookDto>> findBookByAuthor(String author);
    ResponseEntity<List<BookDto>> findBookByCategory(String category);
    ResponseEntity<BookDto> findBookByTitleAndAuthor(String title,String author);
    ResponseEntity<BookDto> borrowBook(int bookId,int userId);
    ResponseEntity<BookDto> returnBook(int bookId, int userId);
}