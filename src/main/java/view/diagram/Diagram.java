package view.diagram;

import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.palette.PaletteSupport;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class Diagram extends JFrame {
  public Diagram() {
    setMinimumSize(new Dimension(800, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel contentPane = new JPanel(new BorderLayout());

    setContentPane(contentPane);

    setTitle("NetBeans Visual Library example");
    pack();
    setVisible(true);

    initComponents(contentPane);
  }

  private void initComponents(JPanel contentPane) {
    JScrollPane workspace = new JScrollPane();
    contentPane.add(workspace, BorderLayout.CENTER);

    Graph graph = new Graph();
    contentPane.add(graph.createSatelliteView(), BorderLayout.WEST);
    workspace.setViewportView(graph.createView());
    //graph.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_CHILDREN);

    graph.addNode(() -> ElementType.ROLE);
    graph.addNode(() -> ElementType.BINARY_PREDICATE);
    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ENTITY);
    graph.addNode(() -> ElementType.ENTITY);

    SceneLayout graphLayout = LayoutFactory.createSceneGraphLayout(graph, GraphLayoutFactory.createHierarchicalGraphLayout(graph, true));
    graphLayout.invokeLayoutImmediately();

    // TODO: add elements and constraints palette to contentPane
  }
}
