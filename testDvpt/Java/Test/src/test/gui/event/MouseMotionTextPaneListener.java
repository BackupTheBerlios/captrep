package test.gui.event;

import java.util.EventListener;

/**
 * <p>Title: MouseMotionTextPaneListener.java </p>
 * <p>Description: The listener interface for receiving mouse motion events on a JTextPane. (For clicks and other mouse events, use the MouseTextPaneListener.) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Giraudr
 * @version 1.0
 */

public interface MouseMotionTextPaneListener extends EventListener
{
  /**
   * Invoqué quand un bouton de la souris a été appuyé et que la souris se déplace sur un JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneDragged(MouseTextPaneEvent e);


  /**
   * Invoqué lorsque le curseur de la souris se déplace sur le JTextPane sans aucun bouton pressé.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneMoved(MouseTextPaneEvent e);
} //Interface MouseMotionTextPaneListener
