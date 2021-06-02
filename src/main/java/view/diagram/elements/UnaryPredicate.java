package view.diagram.elements;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.vstu.orm2diagram.model.ORM_Role;
import org.vstu.orm2diagram.model.ORM_UnaryPredicate;
import view.diagram.actions.edit.EditListener;
import view.diagram.actions.popup.RolePopupMenuProvider;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.elements.predicate.UniquenessConstraint;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.anchor.shape.OrmAnchorShapeFactory;

import java.awt.*;

public class UnaryPredicate extends Widget implements OrmWidget, EditListener {

  private final static String DEFAULT_ROLE = "<role>";
  private final static WidgetAction EDIT_ROLE_ACTION = LabelEditor.withDefaultLabel(DEFAULT_ROLE);

  private final Role roleBox;
  private final LabelWidget roleLabel;
  private final OrmElement element;
  private final ORM_UnaryPredicate predicate;
  private final Graph graph;

  public UnaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.graph = (Graph)scene;
    this.element = element;
    this.predicate = (ORM_UnaryPredicate)element.getNode();

    LookFeel lookFeel = scene.getLookFeel();
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, lookFeel.getMargin()));

    this.roleBox = new Role(scene, this, predicate.getItem(0));

    this.roleLabel = new LabelWidget(scene, predicate.getItem(0).getName());
    roleLabel.setAlignment(LabelWidget.Alignment.CENTER);
    this.roleLabel.getActions().addAction(EDIT_ROLE_ACTION);

    addChild(roleBox);
    addChild(roleLabel);

    // Setting correct position for RoleBox when label is getting wider than RoleBox itself
    roleLabel.addDependency(this.roleBox);
  }

  public Widget getRoleBox() {
    return this.roleBox;
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public void attachConnectAction(WidgetAction action) {
    this.roleBox.getActions().addAction(action);
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
    graph.updateModel((model) -> {
      // Unary predicate has only one role
      ORM_Role role = predicate.getItem(0);
      role.setName(newLabel);
    });
  }
}
