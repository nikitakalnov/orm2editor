package view.diagram.elements.factory;

import org.netbeans.api.visual.widget.Scene;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.elements.Value;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class OrmWidgetFactory {
  private final Scene scene;

  static Map<ElementType, BiFunction<OrmElement, Scene, OrmWidget>> widgetConstructors = new HashMap<>();

  public OrmWidgetFactory(Scene scene) {
    this.scene = scene;

    initWidgets();
  }

  public OrmWidget forElement(OrmElement element) {
    return widgetConstructors.get(element.getType()).apply(element, scene);
  }

  private static void initWidgets() {
    widgetConstructors.put(ElementType.ENTITY, Entity::new);
    widgetConstructors.put(ElementType.UNARY_PREDICATE, Role::new);
    widgetConstructors.put(ElementType.VALUE, Value::new);
    widgetConstructors.put(ElementType.BINARY_PREDICATE, BinaryPredicate::new);
    widgetConstructors.put(ElementType.EQUALITY_CONSTRAINT, SetComparisonConstraint::new);
    widgetConstructors.put(ElementType.EXCLUSION_CONSTRAINT, SetComparisonConstraint::new);
    widgetConstructors.put(ElementType.SUBSET_CONSTRAINT, SetComparisonConstraint::new);
    widgetConstructors.put(ElementType.XOR_CONSTRAINT, SetComparisonConstraint::new);
  }
}
