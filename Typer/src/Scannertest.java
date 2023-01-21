import java.io.IOException;

import model.TypeModel;
import model.TypeModelImpl;
import view.TypeView;
import view.TypeViewImpl;

public class Scannertest {
  public static void main(String args[]) throws IOException {
    //maybe in here load the file and then determine what sort of language we are using
    TypeModel testModel = new TypeModelImpl();
    //timer representing the amount of time you want the test to last.
    TypeView testView = new TypeViewImpl(testModel, 15);


  }
}
