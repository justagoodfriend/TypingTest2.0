package view;

import javax.swing.*;

//maybe have different views, ie have one the monkey type way, where it shows progress with the keys
//the other way is the TFF way where theres a text bar of what you are typing that shows.
public class TypeViewImpl extends JFrame implements TypeView  {

  public TypeViewImpl(){
   super();
   this.setTitle("Typing Test Application");
   this.setSize(500, 500);
  }

}

//also at the very end, want to include a histogram