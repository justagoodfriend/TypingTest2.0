package controller;

import model.TypeModel;
import model.TypeModelImpl;
//this class revolves around Jframes and what not which is perfect...


//da brains
public class TypeControllerImpl implements TypeController {
  TypeModel model;

  public TypeControllerImpl(TypeModel model){
    this.model = model;
  }

  //need to listen to keyEvents...
  @Override
  public void playGame() {
    //have a field for Quit/exit early, look at the initial video
    //also need to have options for them to retry the game

    //main keys, all on the keyboard
    //if return or some weird line break, don't care
    //space create a new word
    //key pressed, add to the current processing list.

  }
}
