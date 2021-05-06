package view.diagram.elements.core;

import java.util.LinkedList;
import java.util.List;

public enum ElementType {
  ENTITY(ElementCategory.OBJECT),
  VALUE(ElementCategory.OBJECT),
  ROLE(ElementCategory.OBJECT),
  BINARY_PREDICATE(ElementCategory.OBJECT),

  EQUALITY_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT),
  SUBSET_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT),
  XOR_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT),
  EXCLUSION_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT),

  SUBTYPING(ElementCategory.SUBTYPING_CONSTRAINT);

  // ORM Element can be either an object or a constraint (including subtyping constraint)
  protected final ElementCategory category;

  ElementType(ElementCategory category) {
    this.category = category;
  }

  public ElementCategory getCategory() {
    return category;
  }

  public static List<ElementType> getInCategory(ElementCategory category) {
    List<ElementType> categoryTypes = new LinkedList<>();

    ElementType[] types = values();
    for(ElementType type : types)
      if(type.getCategory().equals(category))
        categoryTypes.add(type);

    return categoryTypes;
  }
}
