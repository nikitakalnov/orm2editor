package view.diagram.elements.core;

public enum ElementType {
  ENTITY(true),
  VALUE(true),
  ROLE(true),
  BINARY_PREDICATE(true),

  EQUALITY_CONSTRAINT(false),
  SUBSET_CONSTRAINT(false),
  XOR_CONSTRAINT(false),
  EXCLUSION_CONSTRAINT(false),

  SUBTYPING(false);

  // ORM Element can be either an object or a constraint (including subtyping constraint)
  protected final boolean isObject;

  ElementType(boolean isObject) {
    this.isObject = isObject;
  }

  public boolean isObject() {
    return isObject;
  }
}
