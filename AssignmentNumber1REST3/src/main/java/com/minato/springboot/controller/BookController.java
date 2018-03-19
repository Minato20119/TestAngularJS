/**
 * 
 */
package com.minato.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.minato.springboot.entity.Book;
import com.minato.springboot.service.BookService;

/**
 * @author Minato
 *
 */
@Controller
@RequestMapping("api")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) {
		Book book = bookService.getBookById(id);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@GetMapping("book")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> list = bookService.getAllBooks();
		return new ResponseEntity<List<Book>>(list, HttpStatus.OK);
	}

	@PostMapping("book")
	public ResponseEntity<?> addBooks(Book book, UriComponentsBuilder builder) {

		boolean flag = bookService.addBooks(book);

		if (flag == false)
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/book/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("book")
	public ResponseEntity<Book> updateBook(Book book) {
		bookService.updateBooks(book);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@DeleteMapping("book/{id}")
	public ResponseEntity<Void> deleteBookById(@PathVariable("id") Integer id) {
		bookService.deleteBookById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("book/all")
	public ResponseEntity<Void> deleteAllBooks() {
		bookService.deleteAllBooks();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
