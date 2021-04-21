package view.diagram.elements.core;

import org.netbeans.api.visual.widget.Scene;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.elements.Value;

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
    widgetConstructors.put(ElementType.ROLE, Role::new);
    widgetConstructors.put(ElementType.VALUE, Value::new);
  }
}
