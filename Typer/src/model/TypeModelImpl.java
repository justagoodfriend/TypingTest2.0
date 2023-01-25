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
//instead of defining as arrayList type maybe just do list, so its easier


public class TypeModelImpl implements TypeModel {
  //represents the amount of characters per line
  private static final int LINE_WIDTH = 65;
  //represents the list of current words for a particular line
  private List<String> currentLine;
  //list of characters matching the letters that the user has already typed, if we want to be specific, we can limit it
  private List<Character> createdWord;
  //total number of keys pressed which will be used to calculate the wpm
  private int keysPressed;
  //correct keys pressed which will be used to adjust wpm based on accuracy.
  private int correctKeysPressed;
  private boolean currentlyShifted;
  //represents in the list of words where we are at in that list.
  private int currentIndex;

  private RandCollection<ArrayList<String>> rc;
  private List<String> wrongChars;

  //maybe more constructors in the near future depending on the type of settings the user chooses, but for now just defaults
  public TypeModelImpl() {
    //im thinking about when choosing a language to type in, it loads it and throws it in here, so it would be passed as an argument
    this.rc = WordUtils.rcMaker();
    //generate two lines
    this.currentLine = generateWords();
    this.currentLine.addAll(generateWords()); //essentiallly creates two lines of text

    this.createdWord = new ArrayList<Character>();
    this.keysPressed = 0;
    this.correctKeysPressed = 0;
    this.wrongChars = new ArrayList<String>();
    this.currentlyShifted = false;
    this.currentIndex = 0;

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
     System.out.println("This method triggered1");
     this.currentlyShifted = true;
    }
   if(input.equals("unshift")){
     this.currentlyShifted = false;
   }
    else {
      char letter = input.charAt(0);
      //first make the character lowercase, because the key pressed only recognize uppercase
      if(Character.isDigit(letter) || Character.isLetter(letter)){
        char character = Character.toLowerCase(letter);
        if(this.currentlyShifted){
          character = Character.toUpperCase(character);
        }

        //can probably optimize this so that it doesn't keep checking through already existing characters, i.e if
        //the list is already wrong, adding another character will still keep the matching letters wrong, but for another day
        this.createdWord.add(character);
        System.out.println("This is currently our created word " + this.createdWord);
        return this.matchingLetters();
      } //else don't care for now about other keys/punctuation.
    }

    return false;
  }


  private boolean matchingLetters(){
    if(createdWord.size() > currentLine.get(currentIndex).length()){
      return false;
    }
    for(int i = 0; i < createdWord.size(); i ++){
      if(this.currentLine.get(currentIndex).charAt(i) != createdWord.get(i)){
        return false;
      }
    }
    return true;
  }

  //change the logic of this so that it only moves the index of the list.
  @Override
  public List<String> spaceUpdate() {
    this.calculateScoreForWord();

    System.out.println("Correct keys pressed so far " + correctKeysPressed);
    System.out.println("Total keys pressed including incorrect " + keysPressed);
    //clear the current word we made
    this.createdWord = new ArrayList<Character>();
    // i.e shift forward where our position is at in the list
    //we look forward to the next index, if it happens to be a "\n"
    if(this.currentLine.get(currentIndex + 1).equals("FLAG!!")){
      List<String> newInputs = this.currentLine.subList(currentIndex + 2, currentLine.size());
      System.out.println(newInputs);
      this.currentLine = newInputs;
      //once we detect that we have reached the end of the line by the flag,
      //we remove everything we had up to that point
      this.currentLine.addAll(this.generateWords());
      //and generate a new list
      //then we reset our index back to be at 0 saying we are back at the start of the string
      this.currentIndex = 0;
    } else {
      //else this not the end of the array, so we can increment where we are in the list
      this.currentIndex++;
    }

    System.out.println(this.currentLine);
    return this.currentLine;
  }

  /**
   Generates a new line of words, this will be used to represent the lines that a user is typing, and once a user finishes
   typing out a line it generates a new line.
   */
  @Override
  public List<String> generateWords(){
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
    newList.add("FLAG!!");
    //once done, add "\n" as a last argument kinda as a flag pretty much

    System.out.println(newList);
    return newList;
  }

  //needed for the model to communicate with the view
  @Override
  public List<String> getWordList() {
    return this.currentLine;
  }
  //again just used for keep the view up to date with where we are at
  public int getCurrentIndex() {return this.currentIndex;}

  //once we press enter, we need to determine how many of the keys were correctly typed.
  private void calculateScoreForWord(){
    this.keysPressed += this.createdWord.size();
    int minSize = Math.min(this.currentLine.get(currentIndex).length(), this.createdWord.size());
    for(int i = 0; i < minSize; i ++){
      if(this.createdWord.get(i).equals(currentLine.get(currentIndex).charAt(i))){
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
//TODO: WPM clearly off, and the fram keeps shifting with each row, just a little quirk
