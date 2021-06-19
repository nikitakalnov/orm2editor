package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.InplaceEditorAction;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ObjectType;
import view.diagram.actions.edit.EditListener;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.actions.edit.LabelUtils;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.SoftRectangular;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.Comparator;
import java.util.Optional;

public abstract class ObjectType extends ComponentWidget implements OrmWidget, EditListener {
  private final static int FONT_SIZE = 16;
  private final static Font FONT = new Font(null, Font.BOLD, FONT_SIZE);
  private final static String DEFAULT_NAME = "<name>";
  private final WidgetAction entityNameEditor = LabelEditor.withDefaultLabel(DEFAULT_NAME, this);
  protected final OrmElement element;
  protected final ORM_ObjectType object;
  protected final LabelWidget labelWidget;
  protected final Graph graph;
  protected final SoftRectangular shape;
  protected final Widget labelsContainer;

  public ObjectType(OrmElement element, Scene scene, SoftRectangular shape){
    super(scene, new SwingAbstractBox(shape));

    this.shape = shape;
    this.element = element;
    this.graph = (Graph)scene;
    this.object = (ORM_ObjectType)element.getNode();

    labelsContainer = new Widget(scene);
    labelsContainer.setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));
    addChild(labelsContainer);

    setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));

    labelWidget = createLabelWidget(object.getName());
    labelWidget.getActions().addAction(entityNameEditor);
    labelsContainer.addChild(labelWidget);

    setShapeSize();
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
    setShapeSize();
  }

  protected void setShapeSize() {
    Optional<Integer> newWidth = labelsContainer
            .getChildren()
            .stream()
            .map(w -> {
              LabelWidget labelWidget = (LabelWidget)w;
              return LabelUtils.getLabelWidth(getGraphics(), FONT, labelWidget.getLabel());
            })
            .max(Comparator.naturalOrder());

    newWidth.ifPresent(width -> {
      shape.setWidth(width);

      Dimension newSize = shape.getShapeSize();
      getComponent().setSize(newSize);
      setPreferredSize(newSize);

      labelsContainer.getChildren().forEach(label -> {
        label.setPreferredSize(new Dimension(newSize.width, FONT_SIZE + 4));
      });
    });
  }

  protected void setName(String name) {
    graph.updateModel((model) -> object.setName(name));
  }

  protected void setValidateStatusColor() {
    Color color;
    ValidateStatus status = object.getValidateStatus();
    if(status.equals(ValidateStatus.Acceptable))
      color = Color.BLACK;
    else
      color = OrmColorFactory.getValidateStatusColor(status);

    labelWidget.setForeground(color);
  }

  protected LabelWidget createLabelWidget(String label) {
    LabelWidget labelWidget = new LabelWidget(getScene(), label);
    labelWidget.setFont(FONT);
    labelWidget.setAlignment(LabelWidget.Alignment.CENTER);
    labelWidget.setPreferredSize(new Dimension(shape.getShapeSize().width, FONT_SIZE + 4));

    return labelWidget;
  }
}
