package view.diagram.elements.constraints;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.PopupMenuAction;
import view.diagram.actions.popup.PopupMenuUtils;
import view.diagram.actions.popup.SetComparisonConstraintPopupMenuProvider;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.factory.OrmElementIconFactory;

import java.awt.*;
import java.util.List;

public class SetComparisonConstraint extends ImageWidget implements OrmWidget {

  private final Image icon;
  private final OrmElement element;

  public SetComparisonConstraint(OrmElement element, Scene scene) {
    super(scene);
    this.icon = OrmElementIconFactory.getForType(element.getType());
    this.element = element;
    setImage(this.icon);

    getActions().addAction(ActionFactory.createPopupMenuAction(new SetComparisonConstraintPopupMenuProvider(this)));
  }

  @Override
  public Widget getWidget() {
    return this;
  }

  public Image getIcon() {
    return this.icon;
  }

  @Override
  public void attachAction(WidgetAction action) {
    getActions().addAction(action);
  }

  @Override
  public OrmElement getElement() {
    return this.element;
  }
}
