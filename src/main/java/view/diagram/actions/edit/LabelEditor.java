package view.diagram.actions.edit;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

public class LabelEditor {

  static class EditProvider implements TextFieldInplaceEditor {
    private final String defaultText;
    private final EditListener listener;

    EditProvider(String defaultText, EditListener listener) {
      this.defaultText = defaultText;
      this.listener = listener;
    }

    @Override
    public boolean isEnabled(Widget widget) {
      return true;
    }

    @Override
    public String getText(Widget widget) {
      return ((LabelWidget)widget).getLabel();
    }

    @Override
    public void setText(Widget widget, String s) {
      LabelWidget label = (LabelWidget)widget;
      String newLabel = s;

      if(newLabel.isEmpty())
        newLabel = defaultText;

      label.setLabel(newLabel);
      if(listener != null)
        listener.labelChanged(newLabel);
    }
  }

  public static WidgetAction withDefaultLabel(String defaultLabel, EditListener listener) {
    return ActionFactory.createInplaceEditorAction(new EditProvider(defaultLabel, listener));
  }

  public static WidgetAction withDefaultLabel(String defaultLabel) {
    return withDefaultLabel(defaultLabel, null);
  }
}
