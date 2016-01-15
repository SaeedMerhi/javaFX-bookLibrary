package com.starterkit.javafx.controller;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.data.BookTo;
import com.starterkit.javafx.datahandler.DataHandler;
import com.starterkit.javafx.model.BookAdd;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookAddController {

	private static final Logger LOG = Logger.getLogger(BookAddController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private TextField bookAddTitleField;

	@FXML
	private TextField bookAddAuthorsField;

	@FXML
	private Button saveBookButton;

	private final DataHandler dataHandler = DataHandler.INSTANCE;

	// TODO RSmolka change model
	private final BookAdd model = new BookAdd();

	public BookAddController() {
		LOG.debug("Constructor: bookTitleField = " + bookAddTitleField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): bookTitleField = " + bookAddTitleField);

		bookAddTitleField.textProperty().bindBidirectional(model.bookAddTitleProperty());
		bookAddAuthorsField.textProperty().bindBidirectional(model.bookAddAuthorsProperty());

		BooleanBinding bookAddTitleFiledEmpty = bookAddTitleField.textProperty().isEmpty();
		BooleanBinding bookAddAuthorFiledEmpty = bookAddAuthorsField.textProperty().isEmpty();
		saveBookButton.disableProperty().bind(bookAddAuthorFiledEmpty.or(bookAddTitleFiledEmpty));
	}

	@FXML
	private BookTo saveButtonAction(ActionEvent event) {
		LOG.debug("'Save' button clicked");

		BookTo result = dataHandler.addBook(model.getBookAddTitle(), model.getBookAddAuthors());

		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

		return result;
	};

}
