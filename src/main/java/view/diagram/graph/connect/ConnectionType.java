package view.diagram.graph.connect;

import view.diagram.elements.core.ElementType;

public class ConnectionType {
  protected ElementType source;
  protected ElementType target;

  public boolean contains(ElementType element) {
    return source.equals(element) || target.equals(element);
  }

  public boolean isSource(ElementType type) {
    return source.equals(type);
  }

  protected ConnectionType(ElementType source, ElementType target) {
    this.source = source;
    this.target = target;
  }

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
