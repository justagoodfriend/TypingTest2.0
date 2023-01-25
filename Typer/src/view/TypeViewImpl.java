package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.TypeModel;
import model.TypeModelImpl;

//maybe have different views, ie have one the monkey type way, where it shows progress with the keys
//the other way is the TFF way where theres a text bar of what you are typing that shows.
public class TypeViewImpl extends JFrame implements TypeView, KeyListener, ActionListener {
  private TypeModel model;
  private JButton restartButton, exitButton;
  private JTextField wordBox;
  private JLabel textDisplay, resultBox;;
  private boolean startedTyping = false;
  private Timer timer;

  public TypeViewImpl(TypeModel model, int seconds){
    //upon initialization, create the first list, and then once new things come in redisplay the lines...
    //the model must not be null, add in this exception then
    super();
    this.model = model;
    this.setSize(500,500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    this.initShell();
    this.setVisible(true);

    this.setResizable(false);

    this.timer = new Timer(1000 * seconds, new ActionListener() {
      public void actionPerformed(ActionEvent e){
        //note that the time has stopped
        textDisplay.setText("End of Typing test! Press the Restart Button if you wish to continue");
        //display the WPM and accuracy stats
        int result = model.calculateWPM(seconds);
        resultBox.setText("This is the WPM you typed:" + result + ", and the accuracy: " + model.calculateAccuracy());
        //stop the timer from continuing
        timer.stop();
        //remove the key listener to force the user to stop typing.
        for(KeyListener kl: wordBox.getKeyListeners()){
          wordBox.removeKeyListener(kl);
        }
      }
    });

  }

  private void initShell(){
    //this label will be used to display the list of strings given from the model, and will update onc
    this.textDisplay = new JLabel();
    this.add(textDisplay);
    this.DisplayWords(this.model.getWordList(), true, 0);

    this.resultBox = new JLabel();
    this.add(resultBox);

    this.wordBox = new JTextField(10);
    this.wordBox.addKeyListener(this);
    this.add(wordBox);

    this.restartButton = new JButton("Restart");
    this.restartButton.setActionCommand("Restart Test");
    this.restartButton.addActionListener(this);
    this.add(restartButton);

    this.exitButton = new JButton("Exit");
    this.exitButton.setActionCommand("Exit Program");
    this.exitButton.addActionListener(this);
    this.add(exitButton);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(!this.startedTyping){
      this.timer.start();
      this.startedTyping = true;
    }

    switch(e.getKeyCode()) {
      //each switch statement needs their own scope:
      case KeyEvent.VK_BACK_SPACE: {
        List<String> result = this.model.getWordList();
        boolean correct = this.model.charValidation("backspace");
        int index = this.model.getCurrentIndex();
        this.DisplayWords(result, correct, index);
        break;
      }
      case KeyEvent.VK_SPACE: {
        List<String> result = this.model.spaceUpdate();
        int index = this.model.getCurrentIndex();
        this.DisplayWords(result, true, index);
        this.wordBox.setText("");
        break;
      }
      case KeyEvent.VK_SHIFT: {
        System.out.println("Made it into this part");
        List<String> result = this.model.getWordList();
        boolean correct = this.model.charValidation("shift");
        int index = this.model.getCurrentIndex();
        this.DisplayWords(result, correct, index);
        break;
      }
      default: {
        List<String> result = this.model.getWordList();
        boolean correct = this.model.charValidation(KeyEvent.getKeyText(e.getKeyCode()));
        int index = this.model.getCurrentIndex();
        System.out.println("Is this correct?" + correct);
        this.DisplayWords(result, correct, index);

        //worrying about only numbers and characters,
        //annoyingly this only produces uppercase letters
        //System.out.println("I wonder what this produces" + KeyEvent.getKeyText(e.getKeyCode()));
        break;
      }
    }
  }

  private void DisplayWords(List<String> modelWords, boolean color, int index){
    StringBuilder result = new StringBuilder();
    result.append("<html>");
    for(int i = 0; i < modelWords.size(); i ++) {
      //are we at the index with the model?
      if(i == index){
        if(!color){
          result.append("<font color=\"red\">").append(modelWords.get(i)).append("</font>").append(" ");
        }else{
          result.append("<font color=\"gray\">").append(modelWords.get(i)).append("</font>").append(" ");
        }
      }
      //did we find the flag for the new line
      else if(modelWords.get(i).equals("\n")){
        result.append("<br/>");
      }
      else{
        result.append(modelWords.get(i)).append(" ");
      }

      String finalResult = result.toString();
      this.textDisplay.setText(finalResult);
    }
  }


  @Override
  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_SHIFT){
      List<String> result = this.model.getWordList();
      boolean correct = this.model.charValidation("unshift");
      int index = this.model.getCurrentIndex();
      this.DisplayWords(result, correct, index);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    switch(e.getActionCommand()){
      case "Restart Test":
        System.out.println("made it into this function");
        this.model = new TypeModelImpl();
        this.DisplayWords(this.model.getWordList(), true, 0);
        this.wordBox.setText("");
        this.resultBox.setText("");
        this.startedTyping = false;
        this.wordBox.addKeyListener(this);
        break;

      case "Exit Program":
        System.out.println("I am in the other function");
        System.exit(0);
        break;

    }
  }
}
