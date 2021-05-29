package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.InplaceEditorAction;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ObjectType;
import view.diagram.actions.edit.EditListener;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.actions.edit.LabelUtils;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.SoftRectangular;
import view.diagram.graph.Graph;

import java.awt.*;

public abstract class ObjectType extends ComponentWidget implements OrmWidget, EditListener {
  private final static int FONT_SIZE = 16;
  private final static Font FONT = new Font(null, Font.BOLD, FONT_SIZE);
  private final static String DEFAULT_NAME = "<name>";
  private final WidgetAction entityNameEditor = LabelEditor.withDefaultLabel(DEFAULT_NAME, this);
  private final OrmElement element;
  private final ORM_ObjectType object;
  private final LabelWidget labelWidget;
  private final Graph graph;
  private final SoftRectangular shape;

  public ObjectType(OrmElement element, Scene scene, SoftRectangular shape){
    super(scene, new SwingAbstractBox(shape));

    this.shape = shape;
    this.element = element;
    this.graph = (Graph)scene;
    this.object = (ORM_ObjectType)element.getNode();

    setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));

    labelWidget = new LabelWidget(scene, object.getName());
    labelWidget.setFont(FONT);
    labelWidget.setAlignment(LabelWidget.Alignment.CENTER);
    labelWidget.setVerticalAlignment(LabelWidget.VerticalAlignment.CENTER);

    labelWidget.getActions().addAction(entityNameEditor);

    addChild(labelWidget);
    labelWidget.setPreferredSize(new Dimension(shape.getShapeSize().width, FONT_SIZE + 4));
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public void attachAction(WidgetAction action) {
    getActions().addAction(action);
  }

  @Override
  public Widget getWidget() {
    return this;
  }

  @Override
  public void labelChanged(String newLabel) {
    setName(newLabel);

    int newWidth = LabelUtils.getLabelWidth(getGraphics(), FONT, newLabel);
    shape.setWidth(newWidth);

    Dimension newSize = shape.getShapeSize();

    getComponent().setSize(newSize);
    setPreferredSize(newSize);
    labelWidget.setPreferredSize(new Dimension(newSize.width, FONT_SIZE + 4));
  }

  protected void setName(String name) {
    graph.updateModel(() -> object.setName(name));
  }

  public void openNameEditor() {
    InplaceEditorAction editorAction = labelWidget
            .getActions()
            .getActions()
            .stream()
            .filter(a -> a instanceof InplaceEditorAction)
            .findFirst()
            .map(a -> (InplaceEditorAction)a)
            .orElseThrow(() -> new RuntimeException("Name of " + this.toString() + " is not editable"));

    editorAction.openEditor(labelWidget);
  }
}
