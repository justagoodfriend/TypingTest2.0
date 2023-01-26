package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.TypeModel;
import model.TypeModelImpl;

//maybe have different views, ie have one the monkey type way, where it shows progress with the keys
//the other way is the TFF way where theres a text bar of what you are typing that shows.
public class TypeViewImpl extends JFrame implements TypeView {
  private JButton restartButton, exitButton;
  private JTextField wordBox;
  private JLabel textDisplay, resultBox;;
  private boolean startedTyping = false;
  private Timer timer;

  public TypeViewImpl(TypeModel model){
    //upon initialization, create the first list, and then once new things come in redisplay the lines...
    //the model must not be null, add in this exception then
    super();
    this.setSize(500,500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    this.initShell();

    this.setResizable(false);

  }


  @Override
  public void addFeatures(Features features){
    this.exitButton.addActionListener(evt -> features.exitProgram());
    //this does not work quite yet...
    this.restartButton.addActionListener(evt -> features.restartProgram());
    this.addWordBoxListener(features);
  }

  @Override
  public void setWordBox(String text) {
    this.wordBox.setText(text);
  }

  private void initShell(){
    //this label will be used to display the list of strings given from the model, and will update onc
    this.textDisplay = new JLabel();
    this.add(textDisplay);

    this.resultBox = new JLabel();
    this.add(resultBox);

    this.wordBox = new JTextField(10);
    this.add(wordBox);

    this.restartButton = new JButton("Restart");
    this.add(restartButton);

    this.exitButton = new JButton("Exit");
    this.add(exitButton);
  }



  @Override
  public void DisplayWords(List<String> modelWords, boolean color, int index){
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
      else if(modelWords.get(i).equals("FLAG!!")){
        result.append("<br/>");
      }
      else{
        result.append(modelWords.get(i)).append(" ");
      }

      String finalResult = result.toString();
      this.textDisplay.setText(finalResult);
    }
  }

  private void addWordBoxListener(Features features){
    this.wordBox.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        //this method is never used, but have to import it anyways :)))
      }
      @Override
      public void keyPressed(KeyEvent e) {
        features.charUpdate(e);
      }
      @Override
      public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
          features.shiftUpdate("unshift");
        }
      }
    });

  }

  @Override
  public void displayResults(int time, int wpm, double accuracy) {
    this.textDisplay.setText("End of Typing test! Press the Restart Button if you wish to continue");
    this.resultBox.setText("This is the WPM you typed: " + wpm + ", and the accuracy: " + accuracy);
    for(KeyListener kl: wordBox.getKeyListeners()){
      wordBox.removeKeyListener(kl);
    }
  }

  //used for when we reset, clear our textboxes and resulting boxes, and add back our KeyListeners
  @Override
  public void resetFields(Features features) {
    this.wordBox.setText("");
    this.addWordBoxListener(features);
    this.wordBox.requestFocus();
    this.resultBox.setText("");
  }

}
