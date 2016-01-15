package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.javafx.data.BookTo;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class BookSearch {

	private final StringProperty bookTitle = new SimpleStringProperty();
	private final ObjectProperty<String> bookAuthors = new SimpleObjectProperty<>();
	private final ListProperty<BookTo> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getBookTitle() {
		return bookTitle.get();
	}

	public final void setBookTitle(String value) {
		bookTitle.set(value);
	}

	public StringProperty bookTitleProperty() {
		return bookTitle;
	}

	public final String getBookAuthors() {
		return bookAuthors.get();
	}

	public final void setBookAuthors(String value) {
		bookAuthors.set(value);
	}

	public ObjectProperty<String> bookAuthorsProperty() {
		return bookAuthors;
	}

	public final List<BookTo> getResult() {
		return result.get();
	}

	public final void setResult(List<BookTo> value) {
		result.setAll(value);
	}

	public ListProperty<BookTo> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "BookSearch [bookTitle=" + bookTitle + ", bookAuthors=" + bookAuthors + ", result=" + result + "]";
	}

}
