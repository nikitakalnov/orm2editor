package view.diagram.actions.edit;

public interface EditCompletionListener {
  /**
   * Editing is completed
   * @param confirmed if true, then edit is confirmed, else it is escaped
   */
  void completed(boolean confirmed);
}
