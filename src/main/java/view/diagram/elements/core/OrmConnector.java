package view.diagram.elements.core;

import org.vstu.nodelinkdiagram.DiagramNode;

/**
 * Widget drawn by parent OrmWidget and representing connection point
 */
public interface OrmConnector {
  OrmWidget getParent();
  OrmElement getElement();
}
