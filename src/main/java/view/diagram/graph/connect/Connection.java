package view.diagram.graph.connect;

import view.diagram.elements.core.OrmElement;

// TODO: make class abstract
public class Connection {
  private OrmElement source;
  private OrmElement target;

  public Connection(OrmElement source, OrmElement target) {
    this.source = source;
    this.target = target;
  }

  public void setSource(OrmElement source) {
    this.source = source;
  }

  public void setTarget(OrmElement target) {
    this.target = target;
  }

  public OrmElement getSource() {
    return source;
  }

  public OrmElement getTarget() {
    return target;
  }
}
