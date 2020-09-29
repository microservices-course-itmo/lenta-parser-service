package com.wine.to.up.demo.service.parser;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;

public class Parser {

    public Parser() {
    }

    public void parseTitle() throws IOException {
        Document doc = Jsoup.connect("https://lenta.com/catalog/alkogolnye-napitki/vino/").get();
        String title = doc.title();
        System.out.println("Title : " + title);
    }

    public void parseText() throws IOException {
        Document doc1 = Jsoup.connect("https://lenta.com/product/vino-abrau-kupazh-tjmnyjj-krasnoe-suh-rossiya-075l-379932/").get();
        String text = doc1.text();
        String[] words = text.split(" ");
        for (String word : words) {
            System.out.println(word);
        }
    }
}
