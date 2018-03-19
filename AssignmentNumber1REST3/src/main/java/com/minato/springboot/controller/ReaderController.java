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

import com.minato.springboot.entity.Reader;
import com.minato.springboot.service.ReaderService;

/**
 * @author Minato
 *
 */
@Controller
@RequestMapping("api")
public class ReaderController {

	@Autowired
	private ReaderService readerService;

	@GetMapping("reader/{id}")
	public ResponseEntity<Reader> getReaderById(@PathVariable("id") Integer id) {
		Reader reader = readerService.getReaderById(id);
		return new ResponseEntity<Reader>(reader, HttpStatus.OK);
	}

	@GetMapping("reader")
	public ResponseEntity<List<Reader>> getAllReaders() {
		List<Reader> list = readerService.getAllReaders();
		return new ResponseEntity<List<Reader>>(list, HttpStatus.OK);
	}

	@PostMapping("reader")
	public ResponseEntity<?> addReaders(Reader reader, UriComponentsBuilder builder) {

		int checkGetId = readerService.addReader(reader);

		if (checkGetId == 0)
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/reader/{id}").buildAndExpand(reader.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("reader")
	public ResponseEntity<Reader> updateReader(Reader reader) {
		readerService.updateReader(reader);
		return new ResponseEntity<Reader>(reader, HttpStatus.OK);
	}

	@DeleteMapping("reader/{id}")
	public ResponseEntity<Void> deleteReaderById(@PathVariable("id") Integer id) {
		readerService.deleteReaderById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("reader/all")
	public ResponseEntity<Void> deleteAllReaders() {
		readerService.deleteAllReader();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
