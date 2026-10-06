package com.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Book;

@Repository
public interface BookRepository  extends JpaRepository<Book,Integer>{

	Optional<Book> findByTitle(String title);
	List<Book> findByAuthor(String author);
	Optional<Book> findByTitleAndAuthor(String tile,String author);
	
	List<Book> findByCategory(String category);
}
