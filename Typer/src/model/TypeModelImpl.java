package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
  private boolean currentlyShifted;

  private RandCollection<ArrayList<String>> rc;
  private ArrayList<String> wrongChars;

  //maybe more constructors in the near future depending on the type of settings the user chooses, but for now just defaults
  public TypeModelImpl() {
    //im thinking about when choosing a language to type in, it loads it and throws it in here, so it would be passed as an argument
    this.rc = WordUtils.rcMaker();
    this.currentLine = generateWords();
    //this.upcomingLine = generateWords();
    this.createdWord = new ArrayList<Character>();
    this.keysPressed = 0;
    this.correctKeysPressed = 0;
    this.wrongChars = new ArrayList<String>();
    this.currentlyShifted = false;

  }


  @Override
  //this should be a void
  public boolean charValidation(String input) {
    //cases for backspace, if we have room we remove else we just do nothing
    if(input.equals("backspace")) {
      if(createdWord.size() == 0){
        return true;
      }
      //remove the last character if there is space
      createdWord.remove(createdWord.size() - 1);
      return this.matchingLetters();
    }
   if(input.equals("shift")){
     this.currentlyShifted = true;
    }

    else {
      char letter = input.charAt(0);
      //first make the character lowercase, because the key pressed only recognize uppercase
      if(Character.isDigit(letter) || Character.isLetter(letter)){
        char character = Character.toLowerCase(letter);
        //depending on if we are already shifted we

        //can probably optimize this so that it doesn't keep checking through already existing characters, i.e if
        //the list is already wrong, adding another character will still keep the matching letters wrong, but for another day
        this.createdWord.add(character);
        System.out.println("This is currently our created word " + this.createdWord);
        return this.matchingLetters();
      } //else don't care for now about other keys/punctuation.
    }

    return false;
  }

  //method to toggle when we are shifted

  private boolean matchingLetters(){
    if(createdWord.size() > currentLine.get(0).length()){
      return false;
    }
    for(int i = 0; i < createdWord.size(); i ++){
      if(this.currentLine.get(0).charAt(i) != createdWord.get(i)){
        return false;
      }
    }
    return true;
  }

  //change the logic of this so that it only moves the index of the list.
  @Override
  public ArrayList<String> spaceUpdate() {
    this.calculateScoreForWord();

    System.out.println("Correct keys pressed so far " + correctKeysPressed);
    System.out.println("Total keys pressed including incorrect " + keysPressed);
    //clear the current word we made
    this.createdWord = new ArrayList<Character>();
    //remove the element we were typing in, i.e shift forward
    this.currentLine.remove(0);
    //if we have no more characters after we remove, create another new line.
    if(currentLine.size() == 0){
      this.currentLine = generateWords();
    }

    return this.currentLine;

  }

  /**
   Generates a new line of words, this will be used to represent the lines that a user is typing, and once a user finishes
   typing out a line it generates a new line.
   */
  @Override
  public ArrayList<String> generateWords(){
    //will update this later to actually read a file, for some reason not finding the file locally.
    Random randomizer = new Random();
    ArrayList<String> newList = new ArrayList<String>();
    int charCount = 0;

    while(charCount < LINE_WIDTH){
      //pulled this right off stack exchange, and should randomly select a word from a list.
      String selectedWord = rc.next().get(randomizer.nextInt(rc.next().size()));
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

  //needed for the model to communicate with the view
  @Override
  public ArrayList<String> getWordList() {
    return this.currentLine;
  }

  //once we press enter, we need to determine how many of the keys were correctly typed.
  private void calculateScoreForWord(){
    this.keysPressed += this.createdWord.size();
    int minSize = Math.min(this.currentLine.get(0).length(), this.createdWord.size());
    for(int i = 0; i < minSize; i ++){
      if(this.createdWord.get(i).equals(currentLine.get(0).charAt(i))){
        correctKeysPressed +=1;
      }else{
        //adds the incorrect characters to list to generate incorrect answers.
        //this.wrongChars.add(this.createdWord.get(i));
      }
    }
    System.out.println("this is currently how many keys we have pressed:" + keysPressed);
    System.out.println("this is how many correct keys we ave pressed" + correctKeysPressed);
  }

  @Override
  public int calculateWPM(int seconds){
    //how to typically resolve floating point context errors
    double timeInMinutes = (double)seconds/60;

    int basicWPM = (int) ((keysPressed/5) / timeInMinutes);

    return (int)(basicWPM * this.calculateAccuracy())/100;
  }

  @Override
  public double calculateAccuracy(){
    return (double) this.correctKeysPressed/ (double) this.keysPressed * 100;
  }
}
