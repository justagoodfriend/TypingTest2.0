package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface TypeModel {
  boolean charValidation(char input);

  void spaceUpdate(char space);

  ArrayList<String> generateWords() throws FileNotFoundException;

  void createNewLine();


}
