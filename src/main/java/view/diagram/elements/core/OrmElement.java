package view.diagram.elements.core;

import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.DiagramNode;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_Subtyping;
import org.vstu.orm2diagram.model.ORM_ValueType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ORM Wrapper for DiagramNode
 * @author Nikita Kalnov
 */
public class OrmElement {
  private static Map<Class<? extends DiagramElement>, ElementType> TYPES = new HashMap<>();

  static {
    TYPES.put(ORM_EntityType.class, ElementType.ENTITY);
    TYPES.put(ORM_ValueType.class, ElementType.VALUE);
    TYPES.put(ORM_Subtyping.class, ElementType.SUBTYPING);
  }

  private final DiagramNode node;
  private final ElementType type;

  public OrmElement(DiagramNode node) {
    this.node = node;
    this.type = Optional
            .ofNullable(TYPES.get(node.getClass()))
            .orElseThrow(() -> new RuntimeException("Unknown ORM element: " + node.toString()));
  }

  public ElementType getType() {
    return type;
  }

  public DiagramNode getNode() {
    return node;
  }
}
