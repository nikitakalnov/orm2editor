package view.diagram.elements.core;

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
}
