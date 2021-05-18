package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;

import javax.swing.*;
import java.awt.*;

public class BinaryPredicateMenuProvider implements PopupMenuProvider {

  private final BinaryPredicate predicate;

  public BinaryPredicateMenuProvider(BinaryPredicate predicate) {
    this.predicate = predicate;
  }

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    WidgetPopupMenuProvider provider = new WidgetPopupMenuProvider(predicate);
    PredicatePopupMenuProvider predicatePopupMenuProvider = new PredicatePopupMenuProvider(predicate);
    for(JMenuItem item : predicatePopupMenuProvider.getItems()) {
      provider.addItem(item, null);
    }

    return provider.getPopupMenu(widget, point);
  }
}
