package test.gui.event;

import java.awt.event.ComponentEvent;
import java.awt.Component;
import javax.swing.text.Element;
import java.awt.event.MouseEvent;

/**
 * <p>Title: MouseTextPaneEvent </p>
 * <p>Description: Evenement générer par la souris sur un TextPane </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author GiraudR
 * @version 1.0
 */

public class MouseTextPaneEvent extends ComponentEvent
{
  /** L'element concerné par l'événement  */
  private Element element = null;

  /** La position du curseur lors de l'événement. */
  private MouseEvent mouseEvent = null;

  /**
   * Crée l'événement concerné.
   * @param source le textPane concerné
   * @param id (pas utilisé).
   */
  public MouseTextPaneEvent(Component source, int id)
  {
    super(source, id);
  } // Constructeur MouseTextPaneEvent

  /**
   * Crée l'événement concerné.
   * @param source le textPane concerné
   * @param id (pas utilisé).
   * @param mouse l'endroit de l'événement.
   * @param e l'élément concerné par l'événement.
   */
  public MouseTextPaneEvent (Component source, int id, MouseEvent mouse, Element e)
  {
    super (source, id);
    mouseEvent = mouse;
    element = e;
  } // Constructeur MouseTextPaneEvent

  /**
   * Crée l'événement concerné.
   * @param source le textPane concerné
   * @param mouse l'endroit de l'événement.
   * @param e l'élément concerné par l'événement.
   */
  public MouseTextPaneEvent (Component source, MouseEvent mouse, Element e)
  {
    super (source, mouse.getID());
    mouseEvent = mouse;
    element = e;
  } // Constructeur MouseTextPaneEvent


  /**
   * Renvoi l'élément sur lequel l'événement a été déclenché.
   * @return l'élément sur lequel l'événement a été déclenché.
   */
  public Element getElement ()
  {
    return element;
  } /* getElement() */


  /**
   * Renvoi la position de la souris lors de l'événement.
   * @return la position de la souris lors de l'événement.
   */
  public MouseEvent getMouseEvent ()
  {
    return mouseEvent;
  } /* getMouseEvent() */

} // Classe MouseTextPaneEvent
