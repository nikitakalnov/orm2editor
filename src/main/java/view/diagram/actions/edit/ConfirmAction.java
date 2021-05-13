package view.diagram.actions.edit;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.confirm.ConfirmListener;

import java.awt.event.KeyEvent;

public class ConfirmAction extends WidgetAction.Adapter {

  private final ConfirmListener listener;

  public ConfirmAction(ConfirmListener listener) {
    this.listener = listener;
  }

  @Override
  public State keyTyped(Widget widget, WidgetKeyEvent event) {
    State state = State.REJECTED;

    if(event.getKeyChar() == KeyEvent.VK_ENTER) {
      listener.confirmed();
      state = State.CONSUMED;
    }

    return state;
  }
}
