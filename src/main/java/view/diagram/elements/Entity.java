package view.diagram.elements;
import org.netbeans.api.visual.widget.Scene;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;


public class Entity extends ObjectType {

  public Entity(OrmElement element, Scene scene) {
    super(element, scene, new EntityShapeStrategy());
  }
}
