package view.diagram.actions.edit;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.vstu.nodelinkdiagram.StandaloneNode;

import java.awt.event.KeyEvent;

public class EditCompleteAction extends WidgetAction.Adapter {

  private final EditCompletionListener listener;

  public EditCompleteAction(EditCompletionListener listener) {
    this.listener = listener;
  }

  @Override
  public State keyTyped(Widget widget, WidgetKeyEvent event) {
    State state = State.REJECTED;

    if(event.getKeyChar() == KeyEvent.VK_ENTER) {
      listener.completed(true);
      state = State.CONSUMED;
    }
    else if(event.getKeyChar() == KeyEvent.VK_ESCAPE) {
      listener.completed(false);
      state = State.CONSUMED;
    }

    return state;
  }
}
