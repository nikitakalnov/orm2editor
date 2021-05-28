package view.diagram.elements.graphics;

import view.diagram.elements.graphics.shapes.ShapeStrategy;

import javax.swing.*;
import java.awt.*;

public class SwingAbstractBox extends JPanel {
  private final ShapeStrategy shape;

  public SwingAbstractBox(ShapeStrategy shape) {
    this.shape = shape;

    setPreferredSize(shape.getShapeSize());
  }

  @Override
  protected void paintComponent(Graphics g) {
    shape.draw((Graphics2D)g);
  }
}
