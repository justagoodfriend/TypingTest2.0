package controller;

import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;

import model.TypeModel;
import model.TypeModelImpl;
import view.TypeView;


//da brains
public class TypeControllerImpl implements TypeController, Features {
  TypeModel model;
  TypeView view;
  private boolean startedTyping = false;
  Timer timer;


  public TypeControllerImpl(TypeModel model, TypeView view, int seconds)
  { //pretty much establish items, and then set the view to be visible
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);

    this.timer = new Timer(1000 * seconds, e -> {
      double accuracy = Math.round(this.model.calculateAccuracy() * 100.0)/ 100.0;
      int WPM = this.model.calculateWPM(seconds);

      this.view.displayResults(seconds, WPM, accuracy );
      //in the future add a way to display a histogram/bar graph,
      //have tons of space at the bottom just generate a new JLabel with it
      timer.stop();
    });
  }

  @Override
  public void playGame() {
    //also grab the stuff from the model and then set the Display so we can begin
    this.view.DisplayWords(this.model.getWordList(), true, 0);
    this.view.setVisible(true);
  }

  //helper method for creating the resuling arrayList of Words for the view to output, i.e a helper method
  //to soon abstract this code.
  private void createResult(){

  }

  @Override
  public void charUpdate(KeyEvent e) {
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
        this.view.DisplayWords(result, correct, index);
        break;
      }
      case KeyEvent.VK_SPACE: {
        List<String> result = this.model.spaceUpdate();
        int index = this.model.getCurrentIndex();
        this.view.DisplayWords(result, true, index);
        //have to have access to the text box after a space since I need to reset it, easiest way is a getter
        //which is kinda lame, but think of a more clever way later...
        this.view.setWordBox("");
        break;
      }
      case KeyEvent.VK_SHIFT: {
        System.out.println("Made it into this part");
        List<String> result = this.model.getWordList();
        boolean correct = this.model.charValidation("shift");
        int index = this.model.getCurrentIndex();
        this.view.DisplayWords(result, correct, index);
        break;
      }
      default: {
        List<String> result = this.model.getWordList();
        boolean correct = this.model.charValidation(KeyEvent.getKeyText(e.getKeyCode()));
        int index = this.model.getCurrentIndex();
        System.out.println("Is this correct?" + correct);
        this.view.DisplayWords(result, correct, index);

        break;

      }
    }
  }

  //Special Functionality only for the shift key because we have to keep track of when they release
  //the key as well.
  @Override
  public void shiftUpdate(String str) {
    List<String> result = this.model.getWordList();
    boolean correct = this.model.charValidation("unshift");
    int index = this.model.getCurrentIndex();
    System.out.println("Is this correct?" + correct);
    this.view.DisplayWords(result, correct, index);

  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void restartProgram() {
    this.model = new TypeModelImpl();
    this.view.resetFields(this);
    this.view.DisplayWords(this.model.getWordList(), true, 0);
    this.startedTyping = false;
    //clear our models and views and just grab new ones, maybe there's a better way of doing this

  }
}
