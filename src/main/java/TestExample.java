import view.diagram.Diagram;

import javax.swing.*;

public class TestExample {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Diagram::new);
  }
}
