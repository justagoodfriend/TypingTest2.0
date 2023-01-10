package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//import model.wordlists.*;



public class TypeModelImpl implements TypeModel {
  //represents the amount of characters per line
  private static final int LINE_WIDTH = 65;
  //represents the list of current words for a particular line
  private ArrayList<String> currentLine;
  //list of characters matching the letters that the user has already typed, if we want to be specific, we can limit it
  private ArrayList<Character> createdWord;
  //total number of keys pressed which will be used to calculate the wpm
  private int keysPressed;
  //correct keys pressed which will be used to adjust wpm based on accuracy.
  private int correctKeysPressed;

  private ArrayList<String> testwordlist = new ArrayList<String>(Arrays.asList("about", "above", "add", "after", "again", "air", "all", "almost", "along"));

  //maybe more constructors in the near future depending on the type of settings the user chooses, but for now just defaults
  public TypeModelImpl() throws FileNotFoundException {
    //im thinking about when choosing a language to type in, it loads it and throws it in here, so it would be passed as an argument
    this.currentLine = generateWords();
    this.createdWord = new ArrayList<Character>();
    this.keysPressed = 0;
    this.correctKeysPressed = 0;

  }

  //want the abiltiy to allow for mistakes/no mistakes, ie can keep typing when keys are wrong, or just can't progress
  //until the word is correct like type racer, maybe that can be in a separate model.

  @Override
  public boolean charValidation(char input) {
    return false;

  }

  //the action listeners may make the parameters useless.
  @Override
  public void spaceUpdate(char space) {
    //once spaces are coming in and we create a new word, we remove from our wordlist, to update where we are within the list
    //(issue is this doesn't allow for backspaces after typed words)



    //once the space key comes in, needs to determine if this is the start of a new line and needs to generate another line


    //otherwise shifts where we are within our list by one, and then adds the amount of resulting correct keys to our total pressed.

  }

  /**
   Generates a new line of words, this will be used to represent the lines that a user is typing, and once a user finishes
   typing out a line it generates a new line.
   */
  @Override
  public ArrayList<String> generateWords() throws FileNotFoundException {
    //will update this later to actually read a file, for some reason not finding the file locally.
    Random randomizer = new Random();
    ArrayList<String> newList = new ArrayList<String>();
    int charCount = 0;

    while(charCount < LINE_WIDTH){
      //pulled this right off stack exchange, and should randomly select a word from a list.
      String selectedWord = testwordlist.get(randomizer.nextInt(testwordlist.size()));
      if(selectedWord.length() + charCount > LINE_WIDTH){
        //once we find a word that is longer than our expected line width we exit and don't add the word
        break;
      }
      //else we add the word, and note the character count.
      newList.add(selectedWord);
      charCount+= selectedWord.length();
    }

    System.out.println(newList);
    return newList;
  }

  //don't think I need this initially created this for when I have more than 1 line, but can come back to it.
  @Override
  public void createNewLine() {

  }

  private int calculateScore(int timeElapsed){
    return 0;
  }
}
