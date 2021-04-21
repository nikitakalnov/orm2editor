package view.diagram.graph.connect.anchor.shape;

import org.netbeans.api.visual.anchor.AnchorShape;

public class OrmAnchorShapeFactory {
  private static AnchorShape MANDATORY = new MandatoryAnchorShape();

  public static AnchorShape getMandatory() {
    return MANDATORY;
  }
}
