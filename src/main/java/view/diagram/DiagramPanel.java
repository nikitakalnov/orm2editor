package view.diagram;

import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.DiagramClient;
import org.vstu.nodelinkdiagram.MainDiagramModel;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.palette.ElementsPalette;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class DiagramPanel extends JPanel {
  public DiagramPanel(MainDiagramModel mainModel) {
    super(new BorderLayout());

    initComponents(this, mainModel);
  }

  private void initComponents(JPanel contentPane, MainDiagramModel mainModel) {
    ClientDiagramModel model = mainModel.registerClient(new DiagramClient() {});

    JPanel mainArea = new JPanel(new BorderLayout());

    JScrollPane workspace = new JScrollPane();
    mainArea.add(workspace, BorderLayout.CENTER);

    JPanel topPanels = new JPanel();
    topPanels.setLayout(new BoxLayout(topPanels, BoxLayout.Y_AXIS));
    JPanel elementsPalette = new ElementsPalette();
    topPanels.add(elementsPalette);

    JPanel statusPanel = new StatusPanel(model);
    topPanels.add(statusPanel);

    mainArea.add(topPanels, BorderLayout.NORTH);

    contentPane.add(mainArea, BorderLayout.CENTER);

    Graph graph = new Graph(model);
    contentPane.add(graph.createSatelliteView(), BorderLayout.WEST);
    JComponent sceneView = graph.createView();
    workspace.setViewportView(sceneView);

    graph.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_CHILDREN);

    SceneLayout graphLayout = LayoutFactory.createSceneGraphLayout(graph, GraphLayoutFactory.createHierarchicalGraphLayout(graph, true));
    graphLayout.invokeLayoutImmediately();

    // TODO: add elements and constraints palette to contentPane
  }
}
