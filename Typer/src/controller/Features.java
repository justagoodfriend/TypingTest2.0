package controller;

import java.awt.event.KeyEvent;

//class for representing the available functions/listeners that the controller will be waiting for
//then eventually parsing the information to the model
public interface Features {
  //cases for space, shift, any other character,
  void charUpdate(KeyEvent e);
  //usage is a bit weird because shift needs to be recognized when pressed and when released
  void shiftUpdate(String str);

  void exitProgram();


  //getting the results together for the view to display

  void restartProgram();

  //support for exit and reset
  //some way to remove a feature/function while waiting for results ??

}
