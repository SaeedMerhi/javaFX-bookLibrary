package com.starterkit.javafx.datahandler;

import java.util.Collection;

import com.starterkit.javafx.data.BookTo;
import com.starterkit.javafx.datahandler.impl.DataHandlerImpl;

public interface DataHandler {

	DataHandler INSTANCE = new DataHandlerImpl();

	Collection<BookTo> findBooks(String bookTitle);

	BookTo addBook(String bookTitle, String bookAuthors);
}
