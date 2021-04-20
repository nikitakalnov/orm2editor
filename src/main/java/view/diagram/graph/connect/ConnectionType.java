package view.diagram.graph.connect;

import view.diagram.elements.ElementType;

public class ConnectionType {
  protected ElementType source;
  protected ElementType target;

  public boolean contains(ElementType element) {
    return source.equals(element) || target.equals(element);
  }

  protected ConnectionType(ElementType source, ElementType target) {
    this.source = source;
    this.target = target;
  }

  public static ConnectionType ENTITY_ROLE = new ConnectionType(ElementType.ENTITY, ElementType.ROLE);
  public static ConnectionType ENTITY_VALUE = new ConnectionType(ElementType.ENTITY, ElementType.VALUE);
  public static ConnectionType ROLE_RESTRICTION = new ConnectionType(ElementType.ROLE, ElementType.CONSTRAINT);

  @Override
  public boolean equals(Object obj) {
    boolean result = false;

    if(obj instanceof ConnectionType) {
      ConnectionType type = (ConnectionType)obj;

      result = this.source == type.source && this.target == type.target;
    }

    return result;
  }

  @Override
  public int hashCode() {
    return 397 * source.hashCode() * target.hashCode();
  }
}
