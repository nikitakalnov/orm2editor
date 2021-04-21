package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.awt.*;
import java.util.List;

public class Entity extends ComponentWidget implements OrmWidget {

  private final static String DEFAULT_NAME = "<entity>";
  private final static WidgetAction ENTITY_NAME_EDITOR = LabelEditor.withDefaultLabel(DEFAULT_NAME);
  private final static ShapeStrategy SHAPE = new EntityShapeStrategy();
  private final OrmElement element;

  public Entity(OrmElement element, Scene scene) {
    super(scene, new SwingAbstractBox(SHAPE));

    this.element = element;

    setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));

    LabelWidget labelWidget = new LabelWidget(scene, DEFAULT_NAME);
    labelWidget.setFont(new Font(null, Font.BOLD, 16));
    labelWidget.setAlignment(LabelWidget.Alignment.CENTER);
    labelWidget.setVerticalAlignment(LabelWidget.VerticalAlignment.CENTER);

    labelWidget.getActions().addAction(ENTITY_NAME_EDITOR);

    addChild(labelWidget);
    labelWidget.setPreferredSize(new Dimension(SHAPE.getShapeSize().width, 16 + 2));
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public ShapeStrategy getShape() {
    return SHAPE;
  }

  @Override
  public void attachActions(List<WidgetAction> actions) {
    WidgetAction.Chain widgetActions = getActions();

    for(WidgetAction action : actions) {
      widgetActions.addAction(action);
    }
  }

  @Override
  public void attachAction(WidgetAction action) {
    getActions().addAction(action);
  }

  @Override
  public Widget getWidget() {
    return this;
  }
}
