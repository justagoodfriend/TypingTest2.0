package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface TypeModel {
  boolean charValidation(String input);

  ArrayList<String> spaceUpdate();

  ArrayList<String> generateWords();

  ArrayList<String> getWordList();

  int calculateWPM(int seconds);

  double calculateAccuracy();


}
