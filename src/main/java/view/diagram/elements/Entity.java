package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.edit.EditListener;
import view.diagram.actions.edit.LabelUtils;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Entity extends ComponentWidget implements OrmWidget, EditListener {

  private final static int FONT_SIZE = 16;
  private final static Font FONT = new Font(null, Font.BOLD, FONT_SIZE);
  private final static String DEFAULT_NAME = "<entity>";
  private final WidgetAction entityNameEditor = LabelEditor.withDefaultLabel(DEFAULT_NAME, this);
  private final EntityShapeStrategy shape;
  private final OrmElement element;
  private final LabelWidget labelWidget;

  public Entity(OrmElement element, Scene scene) {
    super(scene, new SwingAbstractBox(new EntityShapeStrategy()));

    this.element = element;

    SwingAbstractBox box = (SwingAbstractBox)getComponent();
    shape = (EntityShapeStrategy)box.getShape();

    setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));

    labelWidget = new LabelWidget(scene, DEFAULT_NAME);
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
  public Dimension getSize() {
    return shape.getShapeSize();
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

  @Override
  public void labelChanged(String newLabel) {
    int newWidth = LabelUtils.getLabelWidth(getGraphics(), FONT, newLabel);
    shape.setWidth(newWidth);

    Dimension newSize = shape.getShapeSize();

    getComponent().setSize(newSize);
    setPreferredSize(newSize);
    labelWidget.setPreferredSize(new Dimension(newSize.width, FONT_SIZE + 4));
  }
}
