package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.TemporaryConnection;
import view.diagram.graph.connect.anchor.OrmAnchorFactory;
import view.diagram.graph.connect.factory.ConnectionWidgetDesigner;

import java.util.List;

public abstract class OrmConnectProvider implements ConnectProvider {
  protected final Graph scene;

  protected List<Class<? extends Widget>> targets;

  protected OrmConnectProvider(Graph scene) {
    this.scene = scene;

    targets = initTargets();
  }

  protected abstract List<Class<? extends Widget>> initTargets();

  @Override
  public void createConnection(Widget source, Widget target) {
    ConnectionWidget connection = new ConnectionWidget(scene);

    connection.setTargetAnchor(OrmAnchorFactory.forWidget(target));
    connection.setSourceAnchor(OrmAnchorFactory.forWidget(source));

    connection = ConnectionWidgetDesigner.decorate(connection);

    scene.addConnection(new TemporaryConnection(
            getOrmElement(source),
            getOrmElement(target),
            connection
    ));
  }

  protected OrmElement getOrmElement(Widget widget) {
    OrmElement element;

    if(widget instanceof OrmWidget)
      element = ((OrmWidget) widget).getElement();
    else if(widget instanceof OrmConnector)
      element = ((OrmConnector)widget).getElement();
    else
      throw new IllegalArgumentException(widget.toString() + " is not ORM widget");

    return element;
  }

  @Override
  public ConnectorState isTargetWidget(Widget source, Widget target) {
    boolean isSource = isSourceWidget(source);
    boolean isTarget = false;

    for(Class<? extends Widget> targetClass : targets) {
      isTarget = isTarget || targetClass.isInstance(target);
    }

    return isSource && isTarget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
  }

  public abstract ElementType getSourceType();
}
