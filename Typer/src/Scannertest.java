import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

import controller.TypeController;
import controller.TypeControllerImpl;
import model.TypeModel;
import model.TypeModelImpl;
import view.TypeView;
import view.TypeViewImpl;

public class Scannertest {
  //eventually these will be accepted on the command-line, also should be able to be adjusted from the window
  public static void main(String args[]) throws IOException {
    //maybe in here load the file and then determine what sort of language we are using
    TypeModel testmodel = new TypeModelImpl();
    //timer reprsenting the amount of time you want the test to last.
    TypeView testview = new TypeViewImpl(testmodel);
    TypeController controller = new TypeControllerImpl(testmodel, testview, 5);
    controller.playGame();


  }
}
