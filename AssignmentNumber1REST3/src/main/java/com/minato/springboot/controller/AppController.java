/**
 * 
 */
package com.minato.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Minato
 *
 */
@Controller
public class AppController {

	@RequestMapping("/")
	String welcome() {
		return "index";
	}

	@RequestMapping("book")
	String book() {
		return "book";
	}

	@RequestMapping("borrowing")
	String borrowing() {
		return "borrowing";
	}

	@RequestMapping("reader")
	String reader() {
		return "reader";
	}

}