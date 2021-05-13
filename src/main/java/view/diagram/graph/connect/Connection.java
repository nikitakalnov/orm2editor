package view.diagram.graph.connect;

import org.netbeans.api.visual.widget.ConnectionWidget;
import view.diagram.elements.core.OrmElement;
import view.diagram.graph.connect.providers.OrmConnectProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: make class abstract
public class Connection {
  private OrmElement source;
  private OrmElement target;
  private final ConnectionWidget widget;

  /**
   * Для дуг, которые можно соединять друг с другом
   */
  private List<OrmConnectProvider> connectProviders = new ArrayList<>();

  public Connection(OrmElement source, OrmElement target, ConnectionWidget widget) {
    this.source = source;
    this.target = target;
    this.widget = widget;
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

  public ConnectionWidget getWidget() {
    return widget;
  }

  public List<OrmConnectProvider> getConnectProviders() {
    return Collections.unmodifiableList(connectProviders);
  }

  public void addConnectProvider(OrmConnectProvider provider) {
    this.connectProviders.add(provider);
  }

  public boolean connectedWith(OrmElement element) {
    return source.equals(element) || target.equals(element);
  }
}
