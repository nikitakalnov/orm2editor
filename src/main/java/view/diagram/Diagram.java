package view.diagram;

import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.vstu.nodelinkdiagram.MainDiagramModel;
import org.vstu.orm2diagram.model.ORM_DiagramFactory;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.palette.ElementsPalette;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class Diagram extends JFrame {
  public Diagram() {
    setMinimumSize(new Dimension(800, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel contentPane = new DiagramPanel(new MainDiagramModel(new ORM_DiagramFactory()));

    setContentPane(contentPane);

    setTitle("NetBeans Visual Library example");
    pack();
    setVisible(true);

    // initComponents(contentPane);
  }

  /*private void initComponents(JPanel contentPane) {
    JPanel mainArea = new JPanel(new BorderLayout());

    JScrollPane workspace = new JScrollPane();
    mainArea.add(workspace, BorderLayout.CENTER);

    JPanel elementsPalette = new ElementsPalette();
    mainArea.add(elementsPalette, BorderLayout.NORTH);

    contentPane.add(mainArea, BorderLayout.CENTER);

    Graph graph = new Graph();
    contentPane.add(graph.createSatelliteView(), BorderLayout.WEST);
    JComponent sceneView = graph.createView();
    //sceneView.setDropTarget(null);
    //sceneView.setTransferHandler(new SceneTransferHandler(graph));
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
  }*/

  public void showSimpleMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
