package com.lms.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lms.dto.AddressDto;
import com.lms.dto.BookDto;
import com.lms.dto.LibraryDto;
import com.lms.dto.UserDto;
import com.lms.entity.Address;
import com.lms.entity.Book;
import com.lms.entity.Library;
import com.lms.entity.User;

@Component
public class LibraryMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public Address convertAddressDtoToEntity(AddressDto addressDto) {
		return modelMapper.map(addressDto,Address.class);
	}
	
	public AddressDto convertAddressToDto(Address address) {
		return modelMapper.map(address,AddressDto.class);
	}
	
	public Library convertLibraryDtoToEntity(LibraryDto libraryDto) {
		return modelMapper.map(libraryDto,Library.class);
	}
	
	public LibraryDto convertLibraryToDto(Library library) {
		return modelMapper.map(library,LibraryDto.class);
	}
	
	public Book convertBookDtoToEntity(BookDto bookDto) {
		return modelMapper.map(bookDto,Book.class);
	}
	
	public BookDto convertBookToDto(Book book) {
		return modelMapper.map(book,BookDto.class);
	}
	
	public User convertUserDtoToEntity(UserDto userDto) {
		return modelMapper.map(userDto,User.class);
	}
	
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user,UserDto.class);
	}
	
	
	
}
