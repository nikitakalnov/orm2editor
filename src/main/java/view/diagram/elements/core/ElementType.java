package view.diagram.elements.core;

import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.orm2diagram.model.*;

import java.util.LinkedList;
import java.util.List;

public enum ElementType {
  ENTITY(ElementCategory.OBJECT, ORM_EntityType.class),
  VALUE(ElementCategory.OBJECT, ORM_ValueType.class),
  UNARY_PREDICATE(ElementCategory.OBJECT, ORM_UnaryPredicate.class),
  BINARY_PREDICATE(ElementCategory.OBJECT, ORM_BinaryPredicate.class),

  EQUALITY_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT, null),
  SUBSET_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT, null),
  XOR_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT, null),
  EXCLUSION_CONSTRAINT(ElementCategory.SET_COMPARISON_CONSTRAINT, null),

  SUBTYPING(ElementCategory.SUBTYPING_CONSTRAINT, ORM_Subtyping.class);

  // ORM Element can be either an object or a constraint (including subtyping constraint)
  protected final ElementCategory category;
  protected final Class<? extends DiagramElement> elementClass;

  ElementType(ElementCategory category, Class<? extends DiagramElement> elementClass) {
    this.category = category;
    this.elementClass = elementClass;
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

  public Class<? extends DiagramElement> getElementClass() {
    return elementClass;
  }

  public static ElementType getByClass(Class<? extends DiagramElement> c) throws ElementNotExistException {
    for(ElementType type : values()) {
      if(type.elementClass.equals(c))
        return type;
    }

    throw new ElementNotExistException(c);
  }

  public static class ElementNotExistException extends Exception {
    public ElementNotExistException(Class<? extends DiagramElement> c) {
      super(c.getCanonicalName() + " is not representing any ORM element");
    }
  }
}
