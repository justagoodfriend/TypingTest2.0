import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

import model.TypeModel;
import model.TypeModelImpl;
import view.TypeView;
import view.TypeViewImpl;

public class Scannertest {
  public static void main(String args[]) throws IOException {
    //maybe in here load the file and then determine what sort of language we are using
    TypeModel testmodel = new TypeModelImpl();
    //timer reprsenting the amount of time you want the test to last.
    TypeView testview = new TypeViewImpl(testmodel, 60);


  }
}
