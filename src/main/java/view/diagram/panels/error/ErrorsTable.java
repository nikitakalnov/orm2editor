package view.diagram.panels.error;

import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.ClientDiagramModelListener;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.ModelUpdateEvent;
import view.diagram.graph.Graph;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.stream.Collectors;

public class ErrorsTable extends JTable {

  public ErrorsTable(ClientDiagramModel model) {
    super(new ErrorModel(model));
  }

  public static class ErrorModel extends AbstractTableModel implements ClientDiagramModelListener {
    private final ClientDiagramModel model;

    public ErrorModel(ClientDiagramModel model) {
      this.model = model;
      this.model.addListener(this);
    }

    private final List<Row> rows = new ArrayList<>();
    private static final String[] COLUMNS = { "Element", "Error" };

    @Override
    public String getColumnName(int columnIndex) {
      return COLUMNS[columnIndex];
    }

    @Override
    public int getRowCount() {
      return rows.size();
    }

    @Override
    public int getColumnCount() {
      return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Row row = rows.get(rowIndex);
      return row.getColumn(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      Row row = rows.get(rowIndex);
      row.setColumn(columnIndex, aValue);
    }

    @Override
    public void isUpdated(ModelUpdateEvent modelUpdateEvent) {
      rows.clear();

      model
              .getElements(DiagramElement.class)
              .forEach(e -> {
                List<String> defects = e.getDefects();
                defects.forEach(d -> rows.add(new Row(e, d)));
              });

      fireTableDataChanged();
    }

    public static class Row {
      private final Map<Integer, String> COLUMNS = new HashMap<>();

      public Row(DiagramElement element, String error) {
        COLUMNS.put(0, element.toString());
        COLUMNS.put(1, error);
      }

      public String getColumn(int index) {
        return COLUMNS.get(index);
      }

      public void setColumn(int index, Object object) {
        COLUMNS.put(index, object.toString());
      }
    }
  }

}
