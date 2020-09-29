package com.wine.to.up.demo.service.parser;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Parser {

    public Parser() {
    }

    public void parseTitle() throws IOException {
        File file = new File("file.txt");
        if(file.createNewFile()){
            System.out.println("file.txt файл создан в корневой директории проекта");
        }else System.out.println("file.txt файл уже существует в корневой директории проекта");
        FileWriter writer = new FileWriter("file.txt", true);

        Document doc = Jsoup.connect("https://lenta.com/catalog/alkogolnye-napitki/vino/").get();
        String title = doc.title();
        writer.write(title);
        System.out.println("Title : " + title);

        Document doc1 = Jsoup.connect("https://lenta.com/product/vino-abrau-kupazh-tjmnyjj-krasnoe-suh-rossiya-075l-379932/").get();
        String text = doc1.text();
        String[] words = text.split(" ");
        for (String word : words) {
            writer.write(word + '\n');
            System.out.println(word);
        }

    }
}
