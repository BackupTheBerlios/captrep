package test;


import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import test.gui.JTextPaneHtml;
import test.gui.event.MouseMotionTextPaneListener;
import test.gui.event.MouseTextPaneAdapter;
import test.gui.event.MouseTextPaneEvent;

import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.BadLocationException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.text.AttributeSet;

/**
 * <p>
 * Title: GestionLigneTextPane.java
 * </p>
 * <p>
 * Description: Permet d'ajouter, d'enlever les lignes du JTextPane (ou
 * JTextComponent) et gere tout seul le rollover
 * </p>.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Remy GIRAUD
 * @version 1.0
 */
public class GestionLigneTextPane
{

  private String           couleurNormalBackground;                 /*
                                                                     * La
                                                                     * couleur
                                                                     * normal de
                                                                     * fond de
                                                                     * la ligne
                                                                     */

  public static String     COULEUR_MOUSE_OVER_BACKGROUND = "BBCBBB"; /*
                                                                      * La
                                                                      * couleur
                                                                      * de fond
                                                                      * de la
                                                                      * ligne
                                                                      * quand la
                                                                      * souris
                                                                      * est
                                                                      * dessus
                                                                      */

  private Map              mapLigneElement               = null;    /*
                                                                     * Une map
                                                                     * dont la
                                                                     * clé est
                                                                     * l'identifiant
                                                                     * de
                                                                     * l'élément
                                                                     * (TR) de
                                                                     * la ligne
                                                                     * concernée
                                                                     * et la
                                                                     * valeur
                                                                     * est une
                                                                     * instance
                                                                     * de
                                                                     * ElementLigneTextPane
                                                                     */

  private static final int NBR_LIGNE_MAX                 = 75;      //le

  // nombre
  // de ligne
  // a pas
  // dépasser.

  private JTextPaneHtml    textPaneHTML                  = null;    //le text

  // component
  // concerné.

  private String           id                            = null;    /*
                                                                     * l'identifiant
                                                                     * de la
                                                                     * futur
                                                                     * nouvelle
                                                                     * ligne
                                                                     */

  private static double    FACTOR                        = 0.5;

  /**
   * Cré le gestionnaire de ligne pour le textPane spécifié. <b>ATTENTION </b>
   * instancié le gestionnaire <tt>APRES</tt> avoir fait setDocument ou
   * setEditorKit etc... <b>Attention 2 </b>instancié le gestionnaire APRES
   * avoir attaché le JTextPaneHtml a une fenetre.
   * 
   * @param textComponent le textPane concerné par la gestion des lignes.
   */
  public GestionLigneTextPane (JTextPaneHtml textComponent)
  {
    this.textPaneHTML = textComponent;
    mapLigneElement = Collections.synchronizedMap (new HashMap ());
    id = "" + 0;

    //Initialisation de textPane
    this.textPaneHTML.setContentType ("text/html");
    this.textPaneHTML
        .setText ("<html><head></head><body id=\"DebutTables\"></body></html>");

    //Calcule du couleur de fond du textPane
    Color couleurBackgroundTextPane = textPaneHTML.getBackground ();
    couleurNormalBackground = Integer.toHexString (couleurBackgroundTextPane
        .getRGB ());

    new RollOverTextPane (textPaneHTML); //active le rollover
  } // Constructeur GestionLigneTextPane ()

  /**
   * Ajoute une nouvelle ligne dans la map.
   * 
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   * @return la valeur de l'idTR.
   */
  protected String ajouteLigneMap (String contenuLigne, String toolTip)
  {
    //Ajoute dans la map
    mapLigneElement.put (id, new ElementLigneTextPane (contenuLigne, toolTip));
    String tmp = id;
    incrementeId ();
    return tmp;
    //Ajout dans le textPane
  } /* ajouteLongueurLigneTextPane() */

  /**
   * Modifie une ligne dans la map.
   * 
   * @param idTR l'identifiant de la ligne TR.
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   */
  protected void modifierLigneMap (String idTR, String contenuLigne,
      String toolTip)
  {
    Object o = mapLigneElement.get (idTR);
    if (o == null) return;
    ElementLigneTextPane ligne = (ElementLigneTextPane) o;
    ligne.setContenuLigne (contenuLigne);
    ligne.setToolTop (toolTip);
  } /* modifierLigneMap() */

  /**
   * Ajoute une nouvelle ligne dans le textPane apres la fin de l'élément el.
   * 
   * @param el l'élément apres lequel insérer la ligne.
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   */
  public void ajouteLigneTextPaneAfterEnd (Element el, String contenuLigne,
      String toolTip)
  {
    try
    {
      String idTR = ajouteLigneMap (contenuLigne, toolTip);
      textPaneHTML.insertHTML (el.getEndOffset (), ajoutBaliseLigneEtTable (
          idTR, contenuLigne, couleurNormalBackground));
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
  } /* ajouteLigneTextPaneAfterEnd() */

  /**
   * Ajoute une nouvelle ligne dans le textPane avant la fin de l'élément el.
   * 
   * @param el l'élément avant lequel insérer la ligne.
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   */
  public void ajouteLigneTextPaneBeforeEnd (Element el, String contenuLigne,
      String toolTip)
  {
    try
    {
      String idTR = ajouteLigneMap (contenuLigne, toolTip);
      textPaneHTML.insertHTML (el.getEndOffset () - 1, ajoutBaliseLigneEtTable (
          idTR, contenuLigne, couleurNormalBackground));
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
  } /* ajouteLigneTextPaneBeforeEnd () */

  /**
   * Ajoute une nouvelle ligne dans le textPane avant le debut de l'élément el.
   * 
   * @param el l'élément avant lequel insérer la ligne.
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   */
  public void ajouteLigneTextPaneBeforeStart (Element el, String contenuLigne,
      String toolTip)
  {
    try
    {
      String idTR = ajouteLigneMap (contenuLigne, toolTip);
      textPaneHTML.insertHTML (el.getStartOffset (), ajoutBaliseLigneEtTable (
          idTR, contenuLigne, couleurNormalBackground));
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }

  } /* ajouteLigneTextPaneBeforeStart() */

  /**
   * Ajoute une nouvelle ligne dans le textPane après le debut de l'élément el.
   * 
   * @param el l'élément apres lequel insérer la ligne.
   * @param contenuLigne le contenu de la ligne (entre balise TD /TD)
   * @param toolTip le toolTipText associcié à la ligne.
   */
  public void ajouteLigneTextPaneAfterStart (Element el, String contenuLigne,
      String toolTip)
  {
    try
    {
      String idTR = ajouteLigneMap (contenuLigne, toolTip);
      textPaneHTML
          .insertHTML (el.getStartOffset () + 1, ajoutBaliseLigneEtTable (idTR,
              contenuLigne, couleurNormalBackground));
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }

  } /* ajouteLigneTextPaneAfterStart() */

  /**
   * Ajoute les balises |tr bgcolor="..." text="..."| |td| contenu |/td| |/tr|
   * 
   * @param idTR l'identifiant de la ligne.
   * @param contenuLigne le contenu de la ligne.
   * @param couleurBgd la couleur de la ligne (background).
   * @return StringBuffer le text formaté.
   */
  private StringBuffer ajoutBaliseLigne (String idTR, String contenuLigne,
      String couleurBgd)
  {
    StringBuffer s = new StringBuffer ();
    s.append ("<tr bgcolor=\"");
    s.append (couleurBgd);
    s.append ("\" text=\"");
    s.append (idTR).append ("\"><td>");
    s.append (contenuLigne);
    s.append ("</td></tr>");
    return s;
  }

  /**
   * Ajoute les balises |table width="100%"| |tr bgcolor="..." text="..."| |td|
   * contenu |/td| |/tr| |/table|
   * 
   * @param idTR l'identifiant de la ligne.
   * @param contenuLigne le contenu de la ligne.
   * @param couleurBgd la couleur de la ligne (background).
   * @return StringBuffer le text formaté.
   */
  private String ajoutBaliseLigneEtTable (String idTR, String contenuLigne,
      String couleurBgd)
  {
    StringBuffer s = ajoutBaliseLigne (idTR, contenuLigne, couleurBgd);
    s.insert (0, "<table width=\"100%\">");
    s.append ("</table>");
    return s.toString ();
  }

  /**
   * Change la couleur de fond de la ligne retrouvé à partir de el.
   * 
   * @param el un des élément contenu de la ligne ou le background doit etre
   *        changer
   * @param couleurBackground la nouvelle couleur de fond (ex : "#BBBBBB").
   */
  private void changeBackgroundLigne (Element el, String couleurBackground)
  {
    Element elTR = findElementXX (Tag.TR, el);
    if (null == elTR) return;

    //cherche l'idTR
    String idTR = findIdTR (elTR);
    if (null == idTR)
    {
      System.err.println ("idTr pas trouvé");
      return;
    }

    //on cherche les info de la ligne
    ElementLigneTextPane infoLigne = getLigneTextPane (idTR);
    if (null == infoLigne)
    {
      System.err.println ("info ligne null");
      return;
    }
    try
    {
      //ajout de la ligne avec le background spécifié
      textPaneHTML.insertHTML (elTR.getEndOffset (), ajoutBaliseLigne (idTR,
          infoLigne.getContenuLigne (), couleurBackground).toString ());
      //On retire l'ancienne ligne
      textPaneHTML.getDocument ().remove (elTR.getStartOffset (),
          elTR.getEndOffset () - elTR.getStartOffset ());
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }

  } /* replaceBacgroundLigne() */

  /**
   * Change la couleur de fond de la ligne commencant à startOffset et finissant
   * à endOffSet et qui a pour id idTR.
   * 
   * @param startOffset le debut de la ligne.
   * 
   * @param endOffSet la fin de la ligne.
   * 
   * @param idTR l'identifiant de la ligne.
   * 
   * @param couleurBackground la nouvelle couleur de fond (ex : "#BBBBBB").
   */
  private void changeBackgroundLigne (int startOffset, int endOffSet,
      String idTR, String couleurBackground)
  {

    if (null == idTR)
    {
      System.err.println ("idTr pas trouvé");
      return;
    }

    //on cherche les info de la ligne
    ElementLigneTextPane infoLigne = getLigneTextPane (idTR);
    if (null == infoLigne)
    {
      System.err.println ("info ligne null");
      return;
    }
    try
    {
      //ajout de la ligne avec le background spécifié
      textPaneHTML.insertHTML (endOffSet, ajoutBaliseLigne (idTR,
          infoLigne.getContenuLigne (), couleurBackground).toString ());
      //On retire l'ancienne ligne
      textPaneHTML.getDocument ().remove (startOffset, endOffSet - startOffset);
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
    catch (BadLocationException ex)
    {
      ex.printStackTrace ();
    }

  } /* replaceBacgroundLigne() */

  /**
   * Change la couleur de fond de la ligne (a appeler quand la souris est sur
   * une ligne).
   * 
   * @param el un des éléments de la ligne.
   */
  public void ligneOnMouseOver (Element el)
  {
    changeBackgroundLigne (el, COULEUR_MOUSE_OVER_BACKGROUND);
  }

  /**
   * Change la couleur de fond de la ligne (a appeler quand la souris sort d'une
   * ligne).
   * 
   * @param el un des éléments de la ligne.
   */
  public void ligneOnMouseExit (Element el)
  {
    changeBackgroundLigne (el, couleurNormalBackground);
  }

  /**
   * Change la couleur de fond de la ligne (a appeler quand la souris est sur
   * une ligne).
   * 
   * @param startOffset le debut de la ligne.
   * 
   * @param endOffSet la fin de la ligne.
   * 
   * @param idTR l'identifiant de la ligne.
   *  
   */
  public void ligneOnMouseOver (int startOffset, int endOffSet, String idTR)
  {
    changeBackgroundLigne (startOffset, endOffSet, idTR,
        COULEUR_MOUSE_OVER_BACKGROUND);
  }

  /**
   * Change la couleur de fond de la ligne (a appeler quand la souris sort d'une
   * ligne).
   * 
   * @param startOffset le debut de la ligne.
   * 
   * @param endOffSet la fin de la ligne.
   * 
   * @param idTR l'identifiant de la ligne.
   *  
   */
  public void ligneOnMouseExit (int startOffset, int endOffSet, String idTR)
  {
    changeBackgroundLigne (startOffset, endOffSet, idTR,
        couleurNormalBackground);
  }

  /**
   * Supprime la premiere ligne insérée.
   */
  public void retirerPremiereLigneTextPane ()
  {
  //mapLigneElement.remove(elARetirer);

  } /* retirerPremiereLigne() */

  /**
   * Retire la ligne representée par l'identifiant idTR.
   * 
   * @param idTR L'identifiant de la ligne TR.
   */
  private void retirerLigneMap (String idTR)
  {
    mapLigneElement.remove (idTR);
  } /* retirerLigneTextPane() */

  /**
   * Renvoi les informations concernant la ligne pointé par l'élément cle.
   * 
   * @param idTR L'identifiant de la ligne TR.
   * @return les informations de la ligne, <tt>null</tt> si pas trouvé.
   */
  public ElementLigneTextPane getLigneTextPane (String idTR)
  {
    return (ElementLigneTextPane) mapLigneElement.get (idTR);
  } /* getLigneTextPane() */

  public void verifNbrLigneTextPane ()
  {
    if (mapLigneElement.size () >= NBR_LIGNE_MAX)
    //trop de lignes il fau en supprimer une
        ;
  } /* verifNbrLigneTextPane() */

  public void printElement ()
  {
    System.out
        .println ("------------------------print element-----------------");
    for (int i = 0; i < mapLigneElement.size (); ++i)
    {}
    System.out
        .println ("-------------------------------------------------------");
  }

  /**
   * Incrémente l'identifiant de ligne.
   * 
   * @return String
   */
  private String incrementeId ()
  {
    int val = Integer.parseInt (id);
    return (id = "" + (++val));
  } /* incrementeId() */

  /**
   * Trouve l'élément tag à partir de l'élément source et en remontant dans les
   * branches.
   * 
   * @param tag le tag recherché (par ex : Tag.TR).
   * @param source élément à partir duquel chercher.
   * @return l'element TR ou null si pas trouvé.
   */
  private Element findElementXX (Tag tag, Element source)
  {
    if (source == null) return null;
    Element el = source;
    while (el != null)
    {
      if (verifElementEqualTag (el, tag)) return el;
      el = el.getParentElement ();
    }
    return null;
  } /* findElementXX() */

  /**
   * Vérifie que Element est une balise Tag. Element et tag ne doivent pas être
   * null.
   * 
   * @param el l'element à tester.
   * @param tag le tag dont l'élément doit correspondre.
   * @return true si el est bien un tag tag.
   */
  private boolean verifElementEqualTag (Element el, Tag tag)
  {
    AttributeSet listeAttribut = el.getAttributes ();
    return listeAttribut.getAttribute (listeAttribut.NameAttribute)
        .equals (tag);
  }

  /**
   * Cherche a retrouver le texte inserer dans la balise TR (premiere balise TR
   * rencontrée). La forme de la balise tr doit etre <tr bgcolor='#couleur'
   * text='...'
   * 
   * @param source Element à partir duquel rechercher la balise TR
   * @return le text de l'attribut TEXT de la balise TR concerné, null si pas
   *         trouvé.
   */
  private String findIdTR (Element source)
  {
    Element el = findElementXX (Tag.TR, source);
    if (el == null) return null;
    //On regarde ses attributs
    return findAttributXX (Attribute.TEXT, el);
  }

  /**
   * Recherche l'attribut attr pour l'élément source.
   * 
   * @param attr l'attribut rechercher (ex : Attribute.HREF)
   * @param source Element
   * @return la valeur de l'attribut, null si pas trouvé.
   */
  private String findAttributXX (Attribute attr, Element source)
  {
    AttributeSet listeAttribut = source.getAttributes ();
    Object o = listeAttribut.getAttribute (attr);
    if (null == o) return null;
    return o.toString ();

  }

  public static Frame frameForComponent (Component component)
  {
    while (!(component instanceof Frame))
    {
      component = component.getParent ();
    }
    return (Frame) component;
  }

  public static Color darker (Color c)
  {
    return new Color (Math.max ((int) (c.getRed () * FACTOR), 0), Math.max (
        (int) (c.getGreen () * FACTOR), 0), Math.max (
        (int) (c.getBlue () * FACTOR), 0));
  }

  /**
   * Vérifie si la souris a pas bougé (ou presque pas) entre 2 événements.
   * 
   * @param evtFist le premier evt.
   * @param evtLast le dernier evt.
   * @return
   */
  private static boolean isSourisPasBouger (MouseEvent evtFist,
      MouseEvent evtLast)
  {
    int tailleRect = 20;
    return SwingUtilities.isRectangleContainingRectangle (new Rectangle (
        evtFist.getX () - (tailleRect / 2), evtFist.getY () - tailleRect / 2,
        10, 10), new Rectangle (evtLast.getPoint (), new Dimension (0, 0)));
    //return evtFist.getX () == evtLast.getX ()
    //    && evtFist.getY () == evtLast.getY ();
  } /* isSourisPasBouger () */

  /**
   * <p>
   * Classe : RollOverTextPane
   * </p>.
   * <p>
   * Description: Permet de gérer le chamgement de couleur d'une ligne du
   * TextPane quand la souris passe dessus.
   * </p>.
   */
  private class RollOverTextPane
  {

    private String  lastIdTR             = null; /*
                                                  * l'indice de la ligne de
                                                  * l'avant dernier appel de la
                                                  * fonction par le timer
                                                  */

    private int     debutLastLigne       = -1;  /*
                                                 * la position du debut de la
                                                 * ligne à l'avant dernier appel
                                                 * de la fonction par le timer
                                                 */

    private int     finLastLigne         = -1;  /*
                                                 * la position de la fin de la
                                                 * ligne à l'avant dernier appel
                                                 * de la fonction par le timer
                                                 */

    private String  ligneTRRollOver      = null; /*
                                                  * l'indice de la ligne qui a
                                                  * le rollOver
                                                  */

    private int     debutLigneTRRollOver = -1;  /*
                                                 * la position du debut de la
                                                 * ligne qui a le rollOver
                                                 */

    private int     finLigneTRRollOver   = -1;  /*
                                                 * la position de la fin de la
                                                 * ligne qui a le rollOver
                                                 */

    private JWindow windowToolTip        = null; /*
                                                  * La fenetre qui comporte le
                                                  * toolTip
                                                  */

    public RollOverTextPane (JTextPaneHtml textPane)
    {
      windowToolTip = new JWindow (frameForComponent (textPane));
      Timer timerRollOver = new TimerRollOver (textPane, 25,
          new ActionListener () {

            public void actionPerformed (ActionEvent e)
            {

              TimerRollOver timer = (TimerRollOver) e.getSource ();
              MouseTextPaneEvent evt = timer.getMouseTextPaneEvent ();
              fireRollOver (evt);
              /* actionPerformed () */
            }
          });
      timerRollOver.start ();

      //Pour savoir quand le souris sort du textPane
      textPane.addMouseTextPaneListener (new MouseTextPaneAdapter () {
        public void mouseTextPaneExited (MouseTextPaneEvent e)
        {
          mouseExited (e);
        }

      });
    } /* RollOverTextPane () */

    /**
     * Appeler quand la souris sort du JTextPane.
     *  
     */
    private void mouseExited (MouseTextPaneEvent e)
    {
      if (null != ligneTRRollOver)
      {
        //System.out.println ("ligneTRRollover = " + ligneTRRollOver
        //    + ", debut : " + debutLigneTRRollOver + ", fin : " +
        // finLigneTRRollOver);
        //On est sorti de la zone de saisie, on retire le ligne qui
        // a le roll over
        //retireLigneRollOver (debutLigneTRRollOver, finLigneTRRollOver,
        //    ligneTRRollOver);
        ligneOnMouseExit (debutLigneTRRollOver, finLigneTRRollOver,
            ligneTRRollOver);
      }
      //On masque le toolTip
      windowToolTip.setVisible (false);

    } /* mouseExited () */

    /**
     * Remet la couleur de fond normal sur la dernier ligne qui avait le
     * rollover.
     * 
     * @param debutLigneTRRollOver le debut de la ligne qui avait le rollOver.
     * @param finLigneTRRollOver la fin de la ligne qui avait le rollOver.
     * @param ligneTRRollOver l'idTR de la ligne concernée.
     */
    private void retireLigneRollOver (int debutLigneTRRollOver,
        int finLigneTRRollOver, String ligneTRRollOver)
    {
      ligneOnMouseExit (debutLigneTRRollOver, finLigneTRRollOver,
          ligneTRRollOver);
      this.ligneTRRollOver = null;
      this.debutLigneTRRollOver = -1;
      this.finLigneTRRollOver = -1;

    } /* retireLigneRollOver () */

    /**
     * Fixe les informations de la ligne qui a le rollOver
     * 
     * @param debutLigne l'indice dans le textPane du début de la ligne.
     * @param finLigne l'indice dans le textPane de la fin de la ligne.
     * @param idTRLigne l'identifiant de la ligne TR concernée.
     */
    private void setInfoLigneRollOver (int debutLigne, int finLigne,
        String idTRLigne)
    {
      ligneTRRollOver = idTRLigne;
      debutLigneTRRollOver = debutLigne;
      finLigneTRRollOver = finLigne;

    } /* setInfoLigneRollOver () */

    /**
     * Fonction qui traite le rollOver.
     * 
     * @param evt l'endroit ou la souris été lors de l'appel.
     */
    private synchronized void fireRollOver (MouseTextPaneEvent evt)
    {
      if (evt == null) return;

      String idTRCourant = findIdTR (evt.getElement ());
      Element ligneTR = findElementXX (Tag.TR, evt.getElement ());

      if (null == ligneTR)
      {
        if (null != ligneTRRollOver)
        {
          //On est sorti de la zone de saisie, on retire le ligne qui
          // a le roll over
          retireLigneRollOver (debutLigneTRRollOver, finLigneTRRollOver,
              ligneTRRollOver);
          hideToolTip ();
        }
        return;
      }

      if ((idTRCourant != null && lastIdTR != null
          && idTRCourant.equals (lastIdTR) && ligneTRRollOver != null && (!idTRCourant
          .equals (ligneTRRollOver)))
          || (idTRCourant != null && lastIdTR != null
              && idTRCourant.equals (lastIdTR) && ligneTRRollOver == null)
          || (idTRCourant != null && lastIdTR == null))
      {//Une ligne doit avoir le rollOver

        int debut = ligneTR.getStartOffset ();
        int fin = ligneTR.getEndOffset ();
        ligneOnMouseOver (debut, fin, idTRCourant);

        //On eleve la ligne précédement rollOver
        if (null != ligneTRRollOver)
            retireLigneRollOver (debutLigneTRRollOver, finLigneTRRollOver,
                ligneTRRollOver);

        //Information sur la nouvelle ligne qui a le rollover
        setInfoLigneRollOver (debut, fin, idTRCourant);

        /* TODO toolTip a créer iciiii */
        showToolTip (getLigneTextPane (idTRCourant).getToolTip (), Color.CYAN,
            evt.getMouseEvent (), debut);

        //        System.out.println ("tooltiptext : "
        //            + getLigneTextPane (idTRCourant).getToolTip ());
      }
      //      else if (idTRCourant != null && lastIdTR != null //On est toujours sur
      // la meme ligne.
      //          && idTRCourant.equals (lastIdTR))
      //      {
      //        //On deplace le toolTip
      //        if (lastMouseTextPaneEvent != null
      //            && !isSourisPasBouger (lastMouseTextPaneEvent.getMouseEvent (), evt
      //                .getMouseEvent ()))
      //            showToolTip (getLigneTextPane (idTRCourant).getToolTip (),
      //                Color.CYAN, evt.getMouseEvent ());
      //      }

      if (ligneTR != null) //On change les valeurs pour vérifier entre
      // 2 appel si c toujours la meme ligne.
      {
        lastIdTR = idTRCourant;
        debutLastLigne = ligneTR.getStartOffset ();
        finLastLigne = ligneTR.getEndOffset ();
      }

    } /* fireRollOver () */

    /**
     * Cré un toolTip avec le text spécifié et la couleur spécifiée.
     * 
     * @param toolTipText le message du toolTip.
     * @param couleurToolTip ma cpimeir de fond du toolTip.
     * @return le toolTip.
     */
    private JToolTip createToolTip (String toolTipText, Color couleurToolTip)
    {
      JToolTip tip = new JToolTip ();
      tip.setBackground (couleurToolTip);
      Border b = new LineBorder (darker (couleurToolTip));
      tip.setBorder (b);
      tip.setTipText (toolTipText);

      return tip;
    } /* createToolTip () */

    /**
     * Affiche le toolTip.
     * 
     * @param toolTip le message du toolTip.
     * @param couleurToolTip la couleur de fond du toolTip.
     * @param evt l'événement déclencheur.
     * @param debutLigne l'indice du debut de la ligne dans le textComponen.
     */
    private void showToolTip (String toolTip, Color couleurToolTip,
        MouseEvent evt, int debutLigne)
    {
      try
      {

        windowToolTip.getContentPane ().removeAll ();
        windowToolTip.getContentPane ().setLayout (new BorderLayout (0, 0));
        windowToolTip.getContentPane ().add (
            createToolTip (toolTip, couleurToolTip), BorderLayout.CENTER);
        windowToolTip.pack ();

        JTextComponent textComponent = (JTextComponent) evt.getSource ();
        Rectangle rect = textComponent.getUI ().modelToView (textComponent,
            debutLigne, Position.Bias.Forward);

        //Convertion des points dans le repere de l'ecran.
        Point p = new Point (0, (int) rect.getY () - windowToolTip.getHeight ()
            - 3);
        SwingUtilities.convertPointToScreen (p, evt.getComponent ());
        //p.y += win.getHeight() + 7;
        p.x += 15; // décalage du tooltip a droite du curseur.
        windowToolTip.setLocation (p);
        if (!windowToolTip.isVisible ()) windowToolTip.setVisible (true);
      }
      catch (BadLocationException e)
      {
        e.printStackTrace ();
      }

    } /* showToolTip () */

    /**
     * Masque le toolTip.
     */
    private void hideToolTip ()
    {
      windowToolTip.setVisible (false);
    }

    /**
     * 
     * @return true si la dernier ligne est en rollOver, false autrement.
     */
    private boolean isLastLigneRollOver ()
    {
      return null != lastIdTR;
    } /* isLastLigneRollOver () */

    /**
     * 
     * <p>
     * Classe : TimerRollOver
     * </p>.
     * <p>
     * Description: Classe permettant d'avoir des infos sur l'événement lors de
     * l'arrivé du fin du timer
     * </p>.
     */
    private class TimerRollOver extends Timer
    {

      private MouseTextPaneEvent eventCourant = null; /* l'événement en cours */

      /**
       * Constructeur de la classe TimerRollOver.
       * 
       * @param textPane le text pane concerné par les événements.
       * @param delai le delai entre chaque appel à actionPerformed.
       * @param le listener qui sera notifié a la fin de chaque delai.
       */
      public TimerRollOver (JTextPaneHtml textPane, int delai,
          ActionListener listener)
      {
        super (delai, listener);
        textPane
            .addMouseMotionTextPaneListener (new MouseMotionTextPaneListener ()
            {

              public void mouseTextPaneDragged (MouseTextPaneEvent e)
              {

              /* mouseTextPaneDragged () */
              }

              public void mouseTextPaneMoved (MouseTextPaneEvent e)
              {

                eventCourant = e;
                /* mouseTextPaneMoved () */
              }
            });
        // Constructeur TimerRollOver
      }

      public MouseTextPaneEvent getMouseTextPaneEvent ()
      {
        return eventCourant;
      } /* getMouseTextPaneEvent () */

    } // Classe TimerRollOver

  } // Classe RollOverTextPane

} // Classe GestionLigneTextPane

