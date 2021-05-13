package view.diagram;

import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.EventProcessingType;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.palette.ElementsPalette;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class DiagramPanel extends JPanel {
  public DiagramPanel() {
    super(new BorderLayout());

    initComponents(this);
  }

  private void initComponents(JPanel contentPane) {
    JPanel mainArea = new JPanel(new BorderLayout());

    JScrollPane workspace = new JScrollPane();
    mainArea.add(workspace, BorderLayout.CENTER);

    JPanel elementsPalette = new ElementsPalette();
    mainArea.add(elementsPalette, BorderLayout.NORTH);

    contentPane.add(mainArea, BorderLayout.CENTER);

    Graph graph = new Graph();
    contentPane.add(graph.createSatelliteView(), BorderLayout.WEST);
    JComponent sceneView = graph.createView();
    workspace.setViewportView(sceneView);

    graph.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_CHILDREN);

    graph.addNode(() -> ElementType.UNARY_PREDICATE);
    graph.addNode(() -> ElementType.BINARY_PREDICATE);
    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ENTITY);

    SceneLayout graphLayout = LayoutFactory.createSceneGraphLayout(graph, GraphLayoutFactory.createHierarchicalGraphLayout(graph, true));
    graphLayout.invokeLayoutImmediately();

    // TODO: add elements and constraints palette to contentPane
  }
}
