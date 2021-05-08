package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Role;
import view.diagram.elements.core.ElementCategory;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.util.*;

public class ConnectProviderFactory {
  private final Graph scene;

  private final Map<ElementType, OrmConnectProvider> CONNECT_PROVIDERS = new HashMap<>();

  private final List<Class<? extends Widget>> CONSTRAINT_TARGETS = Arrays.asList(Role.RoleBox.class, BinaryPredicate.RolesBox.class);

  public ConnectProviderFactory(Graph scene) {
    this.scene = scene;

    for(ElementType type : ElementType.getInCategory(ElementCategory.SET_COMPARISON_CONSTRAINT)) {
      if(!type.equals(ElementType.SUBSET_CONSTRAINT))
        CONNECT_PROVIDERS.put(type, initForConstraint(type));
      else
        CONNECT_PROVIDERS.put(type, new SubsetConnectProvider(scene, CONSTRAINT_TARGETS));
    }

    CONNECT_PROVIDERS.put(ElementType.ENTITY, new EntityConnectProvider(scene));
    CONNECT_PROVIDERS.put(ElementType.UNARY_PREDICATE, new UnaryPredicateConnectProvider(scene));
  }

  /**
   * Инициализаровать провайдер для заданного типа ограничения
   * @param type тип элемента
   * @return новый коннект-провайдер. Используется для инициализации (только для внутреннего использования!)
   */
  private SetComparisonConnectProvider initForConstraint(ElementType type) {
    return new SetComparisonConnectProvider(scene, type, CONSTRAINT_TARGETS);
  }

  public OrmConnectProvider getFor(ElementType type) {
    return CONNECT_PROVIDERS.get(type);
  }

  private static final ConnectDecorator DEFAULT_CONNECT_DECORATOR = new DefaultConnectDecorator();

  public static ConnectDecorator getDefaultConnectDecorator() {
    return DEFAULT_CONNECT_DECORATOR;
  }
}
