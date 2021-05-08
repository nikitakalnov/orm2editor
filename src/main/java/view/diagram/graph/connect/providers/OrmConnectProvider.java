package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.Connection;
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

    scene.addConnection(new Connection(
            (getOrmWidget(source)).getElement(),
            (getOrmWidget(target)).getElement(),
            connection
    ));
  }

  protected OrmWidget getOrmWidget(Widget widget) {
    OrmWidget ormWidget;

    if(widget instanceof OrmWidget)
      ormWidget = (OrmWidget)widget;
    else if(widget instanceof OrmConnector)
      ormWidget = ((OrmConnector)widget).getParent();
    else
      throw new IllegalArgumentException(widget.toString() + " is not ORM widget");

    return ormWidget;
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
