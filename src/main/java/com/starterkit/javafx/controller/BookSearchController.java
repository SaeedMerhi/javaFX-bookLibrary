package com.starterkit.javafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.data.BookTo;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.model.BookSearch;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookSearchController {

	private static final Logger LOG = Logger.getLogger(BookSearchController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField bookTitleField;

	@FXML
	private Button searchButton;

	@FXML
	Button addBookButton;

	@FXML
	private TableView<BookTo> resultTable;

	@FXML
	private TableColumn<BookTo, String> bookTitleColumn;

	@FXML
	private TableColumn<BookTo, String> bookAuthorsColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final BookSearch model = new BookSearch();

	public BookSearchController() {
		LOG.debug("Constructor: bookTitleField = " + bookTitleField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): bookTitleField = " + bookTitleField);

		initializeResultTable();

		bookTitleField.textProperty().bindBidirectional(model.bookTitleProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		searchButton.disableProperty().bind(bookTitleField.textProperty().isEmpty());
	}

	private void initializeResultTable() {
		bookTitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		bookAuthorsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthors()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonAction();
	}

	private void searchButtonAction() {

		Task<Collection<BookTo>> backgroundTask = new Task<Collection<BookTo>>() {

			@Override
			protected Collection<BookTo> call() throws Exception {
				LOG.debug("call() called");

				Collection<BookTo> result = dataProvider.findBooks(model.getBookTitle());

				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				Collection<BookTo> result = getValue();

				model.setResult(new ArrayList<BookTo>(result));

				resultTable.getSortOrder().clear();
			}
		};

		new Thread(backgroundTask).start();
	}

	@FXML
	private void addBookButtonAction(ActionEvent event) {
		LOG.debug("'Add book' button clicked");

		addBookButtonAction();
	}

	private void addBookButtonAction() {

		Task<Void> backgroundTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				LOG.debug("call() called");

				Collection<BookTo> result = dataProvider.findBooks(model.getBookTitle());

				return null;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				// TODO RSmolka show messagebox book has been added
			}
		};

		new Thread(backgroundTask).start();
	}
}
