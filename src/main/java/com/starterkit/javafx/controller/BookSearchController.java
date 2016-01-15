package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.data.BookTo;
import com.starterkit.javafx.datahandler.DataHandler;
import com.starterkit.javafx.model.BookSearch;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

	private final DataHandler dataHandler = DataHandler.INSTANCE;

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

				Collection<BookTo> result = dataHandler.findBooks(model.getBookTitle());

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

		LOG.debug("call() called");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/starterkit/javafx/view/book-add.fxml"),
				resources);
		Parent root;
		try {
			root = (Parent) loader.load();
			Scene scene = new Scene(root, 450, 200);
			Stage stage = new Stage();
			stage.setScene(scene);
			loader.getController().hashCode();
			stage.show();
		} catch (IOException ex) {
			LOG.debug("Could not load add-book window ", ex);
		}

	}
}
