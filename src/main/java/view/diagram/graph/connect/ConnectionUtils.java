package view.diagram.graph.connect;

import org.vstu.nodelinkdiagram.DiagramEdge;
import org.vstu.orm2diagram.model.ORM_RoleAssociation;
import org.vstu.orm2diagram.model.ORM_Subtyping;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ConnectionUtils {
  static class ConnectionType {
    private final ElementType sourceType;
    private final ElementType targetType;

    public ConnectionType(ElementType sourceType, ElementType targetType) {
      this.sourceType = sourceType;
      this.targetType = targetType;
    }

    @Override
    public boolean equals(Object obj) {
      if(obj instanceof ConnectionType) {
        ConnectionType other = (ConnectionType)obj;

        return
                other.getSourceType().equals(sourceType) && other.getTargetType().equals(targetType)
                || other.getTargetType().equals(sourceType) && other.getSourceType().equals(targetType);
      }
      else
        return false;
    }

    public ElementType getSourceType() {
      return sourceType;
    }

    public ElementType getTargetType() {
      return targetType;
    }

    @Override
    public int hashCode() {
      return Objects.hash(sourceType, targetType) + Objects.hash(targetType, sourceType);
    }
  }

  private static final Map<ConnectionType, Class<? extends DiagramEdge>> EDGE_TYPES = new HashMap<>();

  static {
    EDGE_TYPES.put(new ConnectionType(ElementType.ENTITY, ElementType.ENTITY), ORM_Subtyping.class);
    EDGE_TYPES.put(new ConnectionType(ElementType.ENTITY, ElementType.ROLE), ORM_RoleAssociation.class);
    EDGE_TYPES.put(new ConnectionType(ElementType.VALUE, ElementType.ROLE), ORM_RoleAssociation.class);
  }

  public static Class<? extends DiagramEdge> getType(OrmElement source, OrmElement target) {
    return Optional
            .ofNullable(EDGE_TYPES.get(new ConnectionType(source.getType(), target.getType())))
            .orElseThrow(() -> new RuntimeException("Can not connect " + source.toString() + " and " + target.toString()));
  }
}
