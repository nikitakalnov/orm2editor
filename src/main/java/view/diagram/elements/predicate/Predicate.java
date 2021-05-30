package view.diagram.elements.predicate;

import org.vstu.orm2diagram.model.ORM_Predicate;

import java.beans.PropertyChangeListener;

public interface Predicate {
  boolean isUnique();
  boolean toggleUnique();

  boolean canToggleConstraints();
  int getArity();
  void addUniquenessChangeListener(PropertyChangeListener l);
}
