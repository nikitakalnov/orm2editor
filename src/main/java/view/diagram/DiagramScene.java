package view.diagram;

import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class DiagramScene extends JFrame {
  public DiagramScene() {
    setMinimumSize(new Dimension(800, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel contentPane = new JPanel(new BorderLayout());

    setContentPane(contentPane);

    initComponents(contentPane);

    setTitle("NetBeans Visual Library example");
    pack();
    setVisible(true);
  }

  private void initComponents(JPanel contentPane) {
    JScrollPane workspace = new JScrollPane();
    contentPane.add(workspace, BorderLayout.CENTER);

    Graph graph = new Graph();

    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ROLE);
    graph.addNode(() -> ElementType.BINARY_PREDICATE);

    SceneLayout graphLayout = LayoutFactory.createSceneGraphLayout(graph, GraphLayoutFactory.createHierarchicalGraphLayout(graph, true));
    graphLayout.invokeLayoutImmediately();

    workspace.setViewportView(graph.createView());

    contentPane.add(graph.createSatelliteView(), BorderLayout.WEST);
  }
}
