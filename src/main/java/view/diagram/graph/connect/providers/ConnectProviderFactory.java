package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Role;
import view.diagram.elements.core.ElementCategory;
import view.diagram.elements.core.ElementType;

import java.util.*;

public class ConnectProviderFactory {
  private final Scene scene;
  private final LayerWidget sceneConnectionLayer;

  private final Map<ElementType, OrmConnectProvider> CONNECT_PROVIDERS = new HashMap<>();

  private final List<Class<? extends Widget>> TARGETS = Arrays.asList(Role.RoleBox.class, BinaryPredicate.RolesBox.class);

  public ConnectProviderFactory(Scene scene, LayerWidget sceneConnectionLayer) {
    this.scene = scene;
    this.sceneConnectionLayer = sceneConnectionLayer;

    for(ElementType type : ElementType.getInCategory(ElementCategory.SET_COMPARISON_CONSTRAINT))
      CONNECT_PROVIDERS.put(type, initForConstraint(type));
  }

  /**
   * Инициализаровать провайдер для заданного типа ограничения
   * @param type тип элемента
   * @return новый коннект-провайдер. Используется для инициализации (только для внутреннего использования!)
   */
  private SetComparisonConnectProvider initForConstraint(ElementType type) {
    return new SetComparisonConnectProvider(scene, sceneConnectionLayer, type, TARGETS);
  }

  public OrmConnectProvider getFor(ElementType type) {
    return CONNECT_PROVIDERS.get(type);
  }

  private static final ConnectDecorator DEFAULT_CONNECT_DECORATOR = new DefaultConnectDecorator();

  public static ConnectDecorator getDefaultConnectDecorator() {
    return DEFAULT_CONNECT_DECORATOR;
  }
}
