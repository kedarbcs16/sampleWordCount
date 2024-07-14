package org.example;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WordCount {

    public static void main(String[] args) {
        if(args.length < 3) {
            System.err.println("Usage: WordCount <input path> <input wordlists path> <output path>");
            System.exit(1);
        }

        String inputFilepath = args[0];
        String inputWordListpath = args[1];
        String outputFilepath = args[2];

        Set<String> words = readWordList(inputWordListpath);
        countWordsAndUpdateFile(inputFilepath, words, outputFilepath);
    }

    public static Set<String> readWordList(String inputWordListpath) {
        Set<String> words = new HashSet<>();
        try (FileInputStream inputStream = new FileInputStream(inputWordListpath);
            Scanner sc = new Scanner(inputStream, "UTF-8")) {
            while (sc.hasNext()) {
                words.add(sc.next().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

    public static Map<String, Integer> countWords(String inputFilepath, Set<String> words) {
        Map<String, Integer> wordCounts = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(inputFilepath);
            Scanner sc = new Scanner(inputStream, "UTF-8")) {
            while (sc.hasNext()) {
                String word = sc.next().toLowerCase();
                if (words.contains(word)) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wordCounts;
    }

    public static void countWordsAndUpdateFile(String inputFilepath, Set<String> words, String outputFilePath) {
        Map<String, Integer> map = countWords(inputFilepath, words);
        updateFile(map,  outputFilePath);
    }

    public static void updateFile(Map<String, Integer> map, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("HashMap written to file successfully!");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
