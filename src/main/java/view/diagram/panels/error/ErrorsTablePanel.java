package view.diagram.panels.error;

import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.ClientDiagramModelListener;
import org.vstu.nodelinkdiagram.ModelUpdateEvent;

import javax.swing.*;
import java.awt.*;

public class ErrorsTablePanel extends JPanel implements ClientDiagramModelListener {

  private static final int TABLE_TITLE_SIZE = 16;
  private static final Font TABLE_TITLE_FONT = new Font(null, Font.PLAIN, TABLE_TITLE_SIZE);
  private final static String TABLE_TITLE_TEXT = "Model Errors: ";
  private final JLabel tableTitle;
  private final JTable errorsTable;

  public ErrorsTablePanel(ClientDiagramModel model) {
    super(new BorderLayout());
    model.addListener(this);

    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    errorsTable = new ErrorsTable(model);
    JScrollPane tablePane = new JScrollPane(errorsTable);
    tablePane.setPreferredSize(new Dimension(800, 60));

    tableTitle = new JLabel(getTableTitle(errorsTable.getRowCount()));
    tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    tableTitle.setFont(TABLE_TITLE_FONT);

    add(tableTitle, BorderLayout.NORTH);
    add(tablePane, BorderLayout.SOUTH);
  }

  protected String getTableTitle(int errorsCount) {
    return TABLE_TITLE_TEXT + errorsCount;
  }

  @Override
  public void isUpdated(ModelUpdateEvent modelUpdateEvent) {
    int errorsCount = errorsTable.getRowCount();
    tableTitle.setText(getTableTitle(errorsCount));
  }
}
