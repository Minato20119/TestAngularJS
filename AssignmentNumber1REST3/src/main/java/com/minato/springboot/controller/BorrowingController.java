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

import com.minato.springboot.entity.Borrowing;
import com.minato.springboot.service.BookService;
import com.minato.springboot.service.BorrowingService;
import com.minato.springboot.service.ReaderService;

/**
 * @author Minato
 *
 */
@Controller
@RequestMapping("api")
public class BorrowingController {

	@Autowired
	private BorrowingService borrowingService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;

	@GetMapping("borrowing/{id}")
	public ResponseEntity<Borrowing> getBorrowingById(@PathVariable("id") Integer id) {
		Borrowing borrowing = borrowingService.getBorrowingById(id);
		return new ResponseEntity<Borrowing>(borrowing, HttpStatus.OK);
	}

	@GetMapping("borrowing")
	public ResponseEntity<List<Borrowing>> getAllBorrowings() {
		List<Borrowing> list = borrowingService.getAllBorrowing();
		return new ResponseEntity<List<Borrowing>>(list, HttpStatus.OK);
	}

	@PostMapping("borrowing")
	public ResponseEntity<?> addBorrowings(Borrowing borrowing, UriComponentsBuilder builder) {

		// Postman(borrowedDay, reimbursedDay, reader, book)
		int readerId = borrowing.getReader().getId();
		int bookId = borrowing.getBook().getId();

		Borrowing borrowing2 = new Borrowing(borrowing.getBorrowedDay(), borrowing.getReimbursedDay(),
				readerService.getReaderById(readerId), bookService.getBookById(bookId));

		int checkId = borrowingService.addBorrowing(borrowing2);

		if (checkId == 0)
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/borrowing/{id}").buildAndExpand(borrowing2.getIdBorrowing()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("borrowing")
	public ResponseEntity<Borrowing> updateBorrowing(Borrowing borrowing) {

		// Postman(borrowedDay, reimbursedDay, reader, book, id)
		int readerId = borrowing.getReader().getId();
		int bookId = borrowing.getBook().getId();

		Borrowing borrowing2 = new Borrowing(borrowing.getIdBorrowing(), borrowing.getBorrowedDay(),
				borrowing.getReimbursedDay(), readerService.getReaderById(readerId), bookService.getBookById(bookId));

		borrowingService.updateBorrowing(borrowing2);
		return new ResponseEntity<Borrowing>(borrowing2, HttpStatus.OK);
	}

	@DeleteMapping("borrowing/{id}")
	public ResponseEntity<Void> deleteBorrowingById(@PathVariable("id") Integer id) {
		borrowingService.deleteBorrowingById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("borrowing/all")
	public ResponseEntity<Void> deleteAllBorrowings() {
		borrowingService.deleteAllBorrowing();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
