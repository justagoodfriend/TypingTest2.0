package view;

import java.util.List;

import controller.Features;

public interface TypeView {
  void setVisible(boolean b);

  void DisplayWords(List<String> modelWords, boolean color, int index);
  void displayResults(int time, int wpm, double accuracy );

  //reset wordlist
  void addFeatures(Features features);
  void setWordBox(String text);
  void resetFields(Features features);
}
