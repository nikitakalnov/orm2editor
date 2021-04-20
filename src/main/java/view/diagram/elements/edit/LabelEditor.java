package view.diagram.elements.edit;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

import java.util.EnumSet;

public class LabelEditor {

  static class EditProvider implements TextFieldInplaceEditor {
    private final String defaultText;

    EditProvider(String defaultText) {
      this.defaultText = defaultText;
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
    }
  }

  public static WidgetAction withDefaultLabel(String defaultLabel) {
    return ActionFactory.createInplaceEditorAction(new EditProvider(defaultLabel));
  }
}
