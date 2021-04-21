import view.diagram.DiagramScene;

import javax.swing.*;

public class TestExample {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(DiagramScene::new);
  }
}
