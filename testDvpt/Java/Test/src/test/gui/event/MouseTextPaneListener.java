package test.gui.event;

import java.util.EventListener;
import test.gui.event.MouseTextPaneEvent;

/**
 * <p>Title: mouseTextPaneListener.java </p>
 * <p>Description: The listener interface for receiving "interesting" mouse events (press, release, click, enter, and exit) on a JTextPane </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Giraudr
 * @version 1.0
 */

public interface MouseTextPaneListener extends EventListener
{
  /**
   * Invoqu� quand un bouton de la souris a �t� cliqu� sur un JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneClicked(MouseTextPaneEvent e);


  /**
   * Invoqu� quand un bouton de la souris a �t� press� sur un JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPanePressed(MouseTextPaneEvent e);

  /**
   * Invoqu� quand un bouton de la souris a �t� relach� sur un JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneReleased(MouseTextPaneEvent e);

  /**
   * Invoqu� quand la souris entre sur le JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneEntered(MouseTextPaneEvent e);

  /**
  * Invoqu� quand la souris sort du JTextPane.
   * @param e MouseTextPaneEvent
   */
  public void mouseTextPaneExited(MouseTextPaneEvent e);




} // Interface mouseTextPaneListener
