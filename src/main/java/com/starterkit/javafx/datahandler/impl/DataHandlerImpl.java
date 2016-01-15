package com.starterkit.javafx.datahandler.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starterkit.javafx.data.BookTo;
import com.starterkit.javafx.datahandler.DataHandler;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DataHandlerImpl implements DataHandler {

	public Collection<BookTo> findBooks(String bookTitle) {
		try {

			Client restClient = Client.create();
			WebResource restWebResource = restClient.resource("http://localhost:9721/workshop/books-by-title");

			ClientResponse restClientResponse = restWebResource.queryParam("titlePrefix", bookTitle)
					.accept("application/json").get(ClientResponse.class);

			if (restClientResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restClientResponse.getStatus());
			}

			String jsonBooks = restClientResponse.getEntity(String.class);

			Gson jsonConverter = new Gson();
			ArrayList<BookTo> books = jsonConverter.fromJson(jsonBooks, new TypeToken<ArrayList<BookTo>>() {
			}.getType());

			return books;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public BookTo addBook(String bookTitle, String bookAuthors) {
		try {

			BookTo bookToAdd = new BookTo(bookTitle, bookAuthors);
			Gson jsonConverter = new Gson();

			Client restClient = Client.create();
			WebResource restWebResource = restClient.resource("http://localhost:9721/workshop/book");
			String jsonBook = jsonConverter.toJson(bookToAdd);

			ClientResponse restClientResponse = restWebResource.type("application/json").post(ClientResponse.class,
					jsonBook);

			if (restClientResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restClientResponse.getStatus());
			}

			return bookToAdd;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}
}
