package test.gui;


import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JToolTip;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.event.EventListenerList;
import test.gui.event.MouseTextPaneEvent;
import test.gui.event.MouseTextPaneListener;
import javax.swing.text.Element;
import java.awt.Point;
import test.gui.event.MouseMotionTextPaneListener;
import java.awt.event.MouseMotionListener;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import javax.swing.text.html.HTMLEditorKit;
import java.io.StringReader;
import javax.swing.SwingUtilities;


/**
 * <p>Title: JTextPaneHtml </p>
 * <p>Description: Ajoute des fonctionnalité spécifique pour un JTextPane</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JTextPaneHtml extends JTextPane implements MouseListener, MouseMotionListener
{
  /** La couleur de background du toolTip */
  private Color couleurToolTipBackground = null;

  protected EventListenerList listenerList = null;
  protected MouseTextPaneEvent mouseTextPaneEvent = null;

  public JTextPaneHtml ()
  {
    super ();
    listenerList = new EventListenerList();
    addMouseListener(this);
    addMouseMotionListener(this);
  } // Constructeur JTextPaneHtml


  public JTextPaneHtml (StyledDocument styleDocument)
  {
    super (styleDocument);
    listenerList = new EventListenerList();
    addMouseListener(this);
    addMouseMotionListener(this);
  } // Constructeur JTextPaneHtml


  /**
   * Fixe la couleur courante de fond du toolTip du TextPane.
   * @param couleurToolTipBackground la nouvelle couleur (background) du toolTip.
   */
  public void setCouleurToolTipBackground (Color couleurToolTipBackground)
  {
    this.couleurToolTipBackground = couleurToolTipBackground;
  } /* setCouleur() */

  /**
   * Renvoi la couleur courante de fond du toolTip du TextPane.
   * @return la couleur courante de fond du toolTip du TextPane.
   */
  public Color getCouleurToolTipBackground ()
  {
    return couleurToolTipBackground;
  } /* getCouleurToolTipBackground() */


  /**
   * Insert du text HTML à l'endroit spécifier.
   * @param location le débout ou insérer du HTML.
   * @param html le text en HTML à insérer.
   * @throws BadLocationException
   * @throws IOException
   */
  public synchronized void insertHTML (final int location, final String html) throws BadLocationException, IOException
  {
    if (!SwingUtilities.isEventDispatchThread ())
    {
      SwingUtilities.invokeLater (new Runnable ()
      {

        public void run ()
        {
          try
          {
            insertHTML (location, html);
          }
          catch (IOException ex)
          {
          }
          catch (BadLocationException ex)
          {
          }
        }
      });
      return;
    }

      HTMLEditorKit kit = (HTMLEditorKit) getEditorKit ();
      StringReader reader = new StringReader (html);
      kit.read (reader, getDocument(), location);
  } /* insertHTML() */


  /**
   * Crée le toolTip avec la couleur de fond couleurToolTipBackground.
   * @return JToolTip
   */
  public JToolTip createToolTip ()
  {
    JToolTip r = super.createToolTip ();
    if (couleurToolTipBackground != null)
      r.setBackground (couleurToolTipBackground);
    r.setFont (new Font ("Dialog", Font.PLAIN, 12));
    return r;
  } /* createToolTip() */


  /**
   * Ajoute le listener de mouseTextPane l pour recevoir les événements mouseTextPane lié au textPane.
   * @param l MouseTextPaneListener
   */
  public void addMouseTextPaneListener (MouseTextPaneListener l)
  {
    listenerList.add(MouseTextPaneListener.class, l);
  } /* addMouseTextPaneListener() */


  /**
   * retire le listener de mouseTextPane l pour ne plus recevoir les événements mouseTextPane lié au textPane.
   * @param l MouseTextPaneListener
   */
  public void removeMouseTextPaneListener (MouseTextPaneListener l)
  {
    listenerList.remove(MouseTextPaneListener.class, l);
  } /* addMouseTextPaneListener() */

  /**
   * Ajoute le listener de mouseMotionTextPane l pour recevoir les événements mouseMotionTextPane lié au textPane.
   * @param l MouseMotionTextPaneListener
   */
  public void addMouseMotionTextPaneListener (MouseMotionTextPaneListener l)
  {
    listenerList.add(MouseMotionTextPaneListener.class, l);
  } /* addMouseMotionTextPaneListener() */


  /**
   * retire le listener de mouseTextPane l pour ne plus recevoir les événements mouseTextPane lié au textPane.
   * @param l MouseMotionTextPaneListener
   */
  public void removeMouseMotionTextPaneListener (MouseMotionTextPaneListener l)
  {
    listenerList.remove(MouseMotionTextPaneListener.class, l);
  } /* addMouseTextPaneListener() */


  /**
   * Notifie tous les écouteurs (listener MouseTextPaneListener) que le textPane
   * a été cliqué.
   *
   * @param me l'endroit ou on a cliqué.
   * @param e l'element concerné par le clique.
   */
  protected void fireMouseTextPaneClicked (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseTextPaneListener) listeners[i+1]).
            mouseTextPaneClicked (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneClicked() */

  /**
   * Notifie tous les écouteurs (listener MouseTextPaneListener) que le bouton
   * de la souris a été appuyé sur le textPane.
   *
   * @param me l'endroit ou on a appuyé le bouton de la souris.
   * @param e l'element concerné par l'appuie.
   */
  protected void fireMouseTextPanePressed (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseTextPaneListener) listeners[i+1]).
            mouseTextPanePressed (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPanePressed() */


  /**
   * Notifie tous les écouteurs (listener MouseTextPaneListener) que le bouton
   * de la souris a été relaché sur le textPane.
   *
   * @param me l'endroit ou on a relaché le bouton.
   * @param e l'element concerné par le relachement.
   */
  protected void fireMouseTextPaneReleased (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseTextPaneListener) listeners[i+1]).
            mouseTextPaneReleased (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneReleased() */


  /**
   * Notifie tous les écouteurs (listener MouseTextPaneListener) que la souris
   * entre sur le textPane.
   *
   * @param me l'endroit ou la souris est rentrée sur le textPane.
   * @param e l'element concerné par l'entrer sur le textPane.
   */
  protected void fireMouseTextPaneEntered (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseTextPaneListener) listeners[i+1]).
            mouseTextPaneEntered (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneEntered() */

  /**
   * Notifie tous les écouteurs (listener MouseTextPaneListener) que la souris
   * sort textPane.
   *
   * @param me l'endroit ou la souris est sortie textPane.
   * @param e l'element concerné par la sortie sur le textPane.
   */
  protected void fireMouseTextPaneExited (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseTextPaneListener) listeners[i+1]).
            mouseTextPaneExited (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneExited() */


  /**
   * Notifie tous les écouteurs (listener MouseMotionTextPaneListener) que la souris
   * bouge dans le textPane.
   *
   * @param me l'endroit actuel du curseur de la souris.
   * @param e l'element du textPane à l'endroit ou se trouve la souris.
   */
  protected void fireMouseTextPaneMove (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseMotionTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseMotionTextPaneListener) listeners[i+1]).
            mouseTextPaneMoved (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneMove() */


  /**
   * Notifie tous les écouteurs (listener MouseMotionTextPaneListener) que la souris
   * bouge dans le textPane.
   *
   * @param me l'endroit actuel du curseur de la souris.
   * @param e l'element du textPane à l'endroit ou se trouve la souris.
   */
  protected void fireMouseTextPaneDragged (MouseEvent me, Element e)
  {
    Object [] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2)
    {
      if (listeners[i] == MouseMotionTextPaneListener.class)
      {
        mouseTextPaneEvent = new MouseTextPaneEvent (this, me, e);
        ((MouseMotionTextPaneListener) listeners[i+1]).
            mouseTextPaneDragged (mouseTextPaneEvent);
      }
    }
  } /* fireMouseTextPaneDragged() */


  /**
   * Retrouve l'élément qui correspond à la position e.
   * @param e MouseEvent
   * @return Element
   */
  private Element elementForEvent (MouseEvent e)
  {
    // avoid instantiating a point in this code
    Point point = new Point ();
    point.x = e.getX ();
    point.y = e.getY ();
    int pos = this.getUI ().viewToModel (this, point);
    Element element = this.getStyledDocument ().getCharacterElement (pos);
    return element;
  } /* elementForEvent() */



  /**
   * mouseClicked
   *
   * @param e MouseEvent
   */
  public void mouseClicked (MouseEvent e)
  {
    fireMouseTextPaneClicked(e, elementForEvent(e));
  }


  /**
   * mouseEntered
   *
   * @param e MouseEvent
   */
  public void mouseEntered (MouseEvent e)
  {
    fireMouseTextPaneEntered (e, elementForEvent(e));
  }


  /**
   * mouseExited
   *
   * @param e MouseEvent
   */
  public void mouseExited (MouseEvent e)
  {
    fireMouseTextPaneExited (e, elementForEvent(e));
  }


  /**
   * mousePressed
   *
   * @param e MouseEvent
   */
  public void mousePressed (MouseEvent e)
  {
    fireMouseTextPanePressed (e, elementForEvent(e));
  }


  /**
   * mouseReleased
   *
   * @param e MouseEvent
   */
  public void mouseReleased (MouseEvent e)
  {
    fireMouseTextPaneReleased (e, elementForEvent(e));
  }


  /**
   * mouseDragged
   *
   * @param e MouseEvent
   */
  public void mouseDragged (MouseEvent e)
  {
    fireMouseTextPaneDragged(e, elementForEvent(e));
  }


  /**
   * mouseMoved
   *
   * @param e MouseEvent
   */
  public void mouseMoved (MouseEvent e)
  {
    fireMouseTextPaneMove(e, elementForEvent(e));
  }

} // Classe JTextPaneHtml
