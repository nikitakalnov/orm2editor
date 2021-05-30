package view.diagram.elements.core;

import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.DiagramNode;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_Subtyping;
import org.vstu.orm2diagram.model.ORM_ValueType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ORM Wrapper for DiagramNode
 * @author Nikita Kalnov
 */
public class OrmElement {
  private final DiagramNode node;
  private final ElementType type;

  public OrmElement(DiagramNode node) {
    ElementType elementType;
    this.node = node;

    try {
      elementType = ElementType.getByClass(node.getClass());
    } catch (ElementType.ElementNotExistException e) {
      elementType = null;
      JOptionPane.showMessageDialog(null, e.getMessage());
    }

    this.type = elementType;
  }

  public ElementType getType() {
    return type;
  }

  public DiagramNode getNode() {
    return node;
  }
}
