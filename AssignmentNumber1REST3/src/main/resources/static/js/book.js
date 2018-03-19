var app = angular.module("BookManage", []);

// Controller Part
app.controller("BookController", function($scope, $http) {
	
	$scope.books = [];
	$scope.bookForm = {
		id: 1,
		nameBook: "",
		publisher: "",
		publishingYear: ""
	};
	
	// Load the date from server
	_refreshBookData();
	
	// HTTP POST/PUT methods for add/edit book
	// Call localhost:8080/api/book
	$scope.submitBook = function() {
		
		var method = "";
		var url = "";
		
		if($scope.bookForm.id == -1) {
			method = "POST";
			url = '/api/book';
		} else {
			method = "PUT";
			url = 'api/book';
		}
		
		$http({
			method: method,
			url: url,
			data: angular.toJson($scope.bookForm),
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(_success, _error);
	};
	
	$scope.createBook = function() {
		_clearFormData();
	}
	
	// HTTP DELETE - delete book by Id
	// Call localhost:8080/api/book{id}
	$scope.deleteBook = function(book) {
		$http({
			method: 'DELETE',
			url: '/api/book/' + book.id
		}).then(_success, _error);
	};
	
	// In case of edit
	$scope.editBook = function(book) {
		$scope.bookForm.id = book.id;
		$scope.bookForm.nameBook = book.nameBook;
		$scope.bookForm.publisher = book.publisher;
		$scope.bookForm.publishingYear = book.publishingYear;
	};
	
	// Private Method
	// HTTP GET - get all book collection
	// Call localhost:8080/api/book
	function _refreshBookData() {
		$http({
			method: 'GET',
			url: '/api/book'
		}).then(
				function(res) { // success
					$scope.books = res.data;
				},
				
				function(res) { // error
					console.log("Error: " + res.status + ":" + res.data);
				}
		);
	}
	
	function _success(res) {
		_refreshBookData();
		_clearFormData();
	}
	
	function _error(res) {
		var data = res.data;
		var status = res.status;
		var header = res.header;
		var config = res.config;
		alert("Error: " + status + ":" + data);
	}
	
	// Clear the form
	function _clearFormData() {
		$scope.bookForm.id = -1;
		$scope.bookForm.nameBook = "";
		$scope.bookForm.publisher = "";
		$scope.bookForm.publishingYear = "";
	};
	
})