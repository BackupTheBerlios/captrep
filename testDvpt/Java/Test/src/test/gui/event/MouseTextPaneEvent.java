package test.gui.event;

import java.awt.event.ComponentEvent;
import java.awt.Component;
import javax.swing.text.Element;
import java.awt.event.MouseEvent;

/**
 * <p>Title: MouseTextPaneEvent </p>
 * <p>Description: Evenement g�n�rer par la souris sur un TextPane </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author GiraudR
 * @version 1.0
 */

public class MouseTextPaneEvent extends ComponentEvent
{
  /** L'element concern� par l'�v�nement  */
  private Element element = null;

  /** La position du curseur lors de l'�v�nement. */
  private MouseEvent mouseEvent = null;

  /**
   * Cr�e l'�v�nement concern�.
   * @param source le textPane concern�
   * @param id (pas utilis�).
   */
  public MouseTextPaneEvent(Component source, int id)
  {
    super(source, id);
  } // Constructeur MouseTextPaneEvent

  /**
   * Cr�e l'�v�nement concern�.
   * @param source le textPane concern�
   * @param id (pas utilis�).
   * @param mouse l'endroit de l'�v�nement.
   * @param e l'�l�ment concern� par l'�v�nement.
   */
  public MouseTextPaneEvent (Component source, int id, MouseEvent mouse, Element e)
  {
    super (source, id);
    mouseEvent = mouse;
    element = e;
  } // Constructeur MouseTextPaneEvent

  /**
   * Cr�e l'�v�nement concern�.
   * @param source le textPane concern�
   * @param mouse l'endroit de l'�v�nement.
   * @param e l'�l�ment concern� par l'�v�nement.
   */
  public MouseTextPaneEvent (Component source, MouseEvent mouse, Element e)
  {
    super (source, mouse.getID());
    mouseEvent = mouse;
    element = e;
  } // Constructeur MouseTextPaneEvent


  /**
   * Renvoi l'�l�ment sur lequel l'�v�nement a �t� d�clench�.
   * @return l'�l�ment sur lequel l'�v�nement a �t� d�clench�.
   */
  public Element getElement ()
  {
    return element;
  } /* getElement() */


  /**
   * Renvoi la position de la souris lors de l'�v�nement.
   * @return la position de la souris lors de l'�v�nement.
   */
  public MouseEvent getMouseEvent ()
  {
    return mouseEvent;
  } /* getMouseEvent() */

} // Classe MouseTextPaneEvent
