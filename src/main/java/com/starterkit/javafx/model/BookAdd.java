package com.starterkit.javafx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookAdd {

	private final StringProperty bookAddTitle = new SimpleStringProperty();
	private final StringProperty bookAddAuthors = new SimpleStringProperty();

	public final String getBookAddTitle() {
		return bookAddTitle.get();
	}

	public final void setBookAddTitle(String value) {
		bookAddTitle.set(value);
	}

	public StringProperty bookAddTitleProperty() {
		return bookAddTitle;
	}

	public final String getBookAddAuthors() {
		return bookAddAuthors.get();
	}

	public final void setBookAddAuthors(String value) {
		bookAddAuthors.set(value);
	}

	public StringProperty bookAddAuthorsProperty() {
		return bookAddAuthors;
	}

	@Override
	public String toString() {
		return "BookSearch [bookTitle=" + bookAddTitle + ", bookAuthors=" + bookAddAuthors + "]";
	}

}
