package view.diagram;

import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.ClientDiagramModelListener;
import org.vstu.nodelinkdiagram.ModelUpdateEvent;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import view.diagram.colors.OrmColorFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatusPanel extends JPanel implements ClientDiagramModelListener {
  private final ClientDiagramModel model;
  private final static String MODEL_STATUS = "Model is ";
  private final static int FONT_SIZE = 16;
  private final static Font FONT = new Font(null, Font.PLAIN, FONT_SIZE);

  /* Panel components */
  private final JLabel validateStatusLabel;

  public StatusPanel(ClientDiagramModel model) {
    super(new FlowLayout(FlowLayout.LEFT, 2, 6));
    this.model = model;
    model.addListener(this);

    /* Initialising components */
    JLabel modelStatusLabel = new JLabel(MODEL_STATUS);
    modelStatusLabel.setFont(FONT);
    add(modelStatusLabel);

    this.validateStatusLabel = new JLabel();
    this.validateStatusLabel.setFont(FONT);
    setStatus();
    add(validateStatusLabel, BorderLayout.WEST);
  }

  private void setStatus() {
    ValidateStatus status = model.getValidateStatus();
    this.validateStatusLabel.setText(status.name());
    this.validateStatusLabel.setForeground(OrmColorFactory.getValidateStatusColor(status));
  }

  @Override
  public void isUpdated(ModelUpdateEvent modelUpdateEvent) {
    setStatus();
  }
}
