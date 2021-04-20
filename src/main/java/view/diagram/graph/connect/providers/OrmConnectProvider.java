package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectProvider;
import view.diagram.graph.connect.ConnectionType;

public interface OrmConnectProvider extends ConnectProvider {
  ConnectionType getConnectionType();
}
