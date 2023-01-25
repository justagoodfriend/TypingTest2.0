package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface TypeModel {
  boolean charValidation(String input);

  List<String> spaceUpdate();

  List<String> generateWords();

  List<String> getWordList();
  int getCurrentIndex();
  int calculateWPM(int seconds);

  double calculateAccuracy();


}
