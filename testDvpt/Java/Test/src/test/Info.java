package test;

/**
 * Title:        pata_cara
 * Description:  chat en java
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Rémy Giraud
 * @version 1.0
 */


import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.*;
import java.awt.*;


public class Info extends JPanel
{
    private JTextPane textPane;
//    private static final Color     Couleur;// = Color.lightGray;

  private static Style regular;
  private static Style def;



  public Info()
  {
      super ();
//      setBackground(Couleur);
      try
      {
        jbInnit ();
      }
      catch (Exception ex)
      {
      }

  }

  /**
   * Accesseur au textpane
   * @return JTextPane
   */
  public JTextPane getTextPane ()
  {
    return this.textPane;
  }

  private void jbInnit()
  {
    textPane = createTextPane ();
    JPanel pGUI =    jbInit();

    JScrollPane scrollPane = new JScrollPane(textPane,
                                   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBorder (BorderFactory.createLoweredBevelBorder ());

    JPanel pFin = new JPanel (new BorderLayout ());
//      pFin.setBackground (Couleur);

    pFin.add (scrollPane, BorderLayout.CENTER);
    pFin.setBorder ( BorderFactory.createCompoundBorder (
  BorderFactory.createTitledBorder ("Informations"),
  BorderFactory.createEmptyBorder (0, 0, 0, 0 )));

    setLayout (new BorderLayout ());
    JPanel pHaut = new JPanel (new FlowLayout (FlowLayout.CENTER, 2,2));
//      pHaut.setBackground(Couleur);
    add (pHaut, BorderLayout.NORTH);
    add (pFin,  BorderLayout.CENTER);
    add(pGUI,  BorderLayout.EAST);


  }

  static public JTextPane createTextPane ()
  {
      JTextPane textPane = new JTextPane ();
      textPane.setEditable (false);
      def = javax.swing.text.StyleContext.getDefaultStyleContext ().
                            getStyle (javax.swing.text.StyleContext.DEFAULT_STYLE);
      regular = textPane.addStyle ("toto", def);
      StyleConstants.setFontFamily (def, "SansSerif");

      Style s = textPane.addStyle ("tata", regular);
      StyleConstants.setFontSize (s, 10);

      s = textPane.addStyle ("titi", regular);
      StyleConstants.setFontSize (s, 16);
      //StyleConstants.setBold (s, true);

      s = textPane.addStyle ("tutu", regular);
      StyleConstants.setFontSize (s, 17);
      StyleConstants.setBold (s, true);


      return textPane;

  } /* createTextPane () */


  private JPanel creatDesigGUI () {return null;}


  private JPanel jbInit ()// throws Exception

  {
    JPanel pReturn = new JPanel (new GridLayout (3, 1));
    pReturn.setPreferredSize(new Dimension(200, 423));

/*    JButton but1 = null;
    JButton but2 = null;
    String textBut1 = "Changer";
    String textBut2 = "Défaut";
    String toolTipBut1 = "Changer de look";
    String toolTipBut2 = "défaut";
    String titreBoite = "Look / Design";
    JList liste = null;

    JPanel pPrincipale = new JPanel (new BorderLayout (0,5));
    JPanel pBut = new JPanel (new FlowLayout (FlowLayout.CENTER,10,0));
    JPanel pBut1 = new JPanel();
    JPanel pBut2 = new JPanel();

    but1 = new JButton ();
    but1.setText(textBut1);
//    boutont2 = new JButton (textBut2);
    but2 = new JButton();

    but2.setText(textBut2);
//    pBut2.add(boutont2, "b2");
    pBut1.add(but1, "b1");


//    pForme.setBackground (Salon.COULEURSALONEST);

    liste = new JList ();
    //ListeConnecte.addActionListener(auditeur);
    JScrollPane scrollPaneListe = new JScrollPane(liste,
                                         JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPaneListe.setPreferredSize(new Dimension(258, 70));

    liste.setPreferredSize(new Dimension(0, 0));

    pBut.setLayout(new BorderLayout ());

    pBut2.setLayout(new CardLayout ());
//    boutont2.setToolTipText(toolTipBut2);
    pBut1.setLayout(new CardLayout ());
    but1.setToolTipText(toolTipBut1);

    pBut.add(pBut2, BorderLayout.WEST);
    pBut.add(pBut1, BorderLayout.EAST);
    pBut2.add(but2, "b2");

//    bDial.addActionListener(auditeur);
//bDial.setFont (new Font ("sansserif", 0, 8));
//    bIgno.addActionListener(auditeur);

//    pBut.setBackground(Salon.COULEURSALONEST);
    pBut.setPreferredSize(new Dimension(112, 19));


    pPrincipale.setBorder ( BorderFactory.createCompoundBorder (
              BorderFactory.createTitledBorder (titreBoite),
              BorderFactory.createEmptyBorder (10, 10, 10, 10 )));

    pPrincipale.add(scrollPaneListe, BorderLayout.CENTER);
    pPrincipale.add (pBut, BorderLayout.SOUTH);
*/
    JButton but1 = null;
    JButton but2 = null;
    String textBut1 = "Changer";
    String textBut2 = "Défaut";
    String toolTipBut1 = "Changer de look";
    String toolTipBut2 = "défaut";
    String titreBoite = "Look / Design";
    JList liste = null;

    JPanel pPrincipale = createBoxWith2Bouton1Liste(titreBoite, but1, but2, textBut1, textBut2, liste, toolTipBut1, toolTipBut2);
    JPanel pThemes = createBoxWith2Bouton1Liste("Thèmes", but1, but2, "Select", "Annuler", liste, "Selectionne le thème", "Annule le changement de thème");
    pReturn.add(pPrincipale);
    JPanel pCThemes = new JPanel (new BorderLayout ());
    JPanel pNordThemes = new JPanel (new FlowLayout (FlowLayout.CENTER, 5, 5));
    pCThemes.add(pNordThemes, BorderLayout.NORTH);
    pCThemes.add(pThemes, BorderLayout.CENTER);

    pReturn.add(pCThemes);

    JPanel pBas = new JPanel (new BorderLayout ());
    JPanel pSudBas = new JPanel ();
    pSudBas.add( (new JButton ("test bas")));
    pBas.add(pSudBas, BorderLayout.SOUTH);

    pReturn.add(pBas);

    //pReturn.add(createBoxWith2Bouton1Liste( titreBoite, but1, but2, textBut1, textBut2, liste, toolTipBut1, toolTipBut2));
    return pReturn;

  }


  private JPanel createBoxWith2Bouton1Liste (String titreBoite, JButton but1,
                                             JButton but2, String textBut1,
                                             String textBut2, JList liste,
                                             String toolTipBut1, String toolTipBut2)
  {
    JPanel pPrincipale = new JPanel (new BorderLayout (0,5));
    JPanel pBut = new JPanel (new FlowLayout (FlowLayout.CENTER,10,0));
    JPanel pBut1 = new JPanel();
    JPanel pBut2 = new JPanel();

    but1 = new JButton ();
    but1.setText(textBut1);
//    boutont2 = new JButton (textBut2);
    but2 = new JButton();

    but2.setText(textBut2);
//    pBut2.add(boutont2, "b2");
    pBut1.add(but1, "b1");


//    pForme.setBackground (Salon.COULEURSALONEST);

    liste = new JList ();
    //ListeConnecte.addActionListener(auditeur);
    JScrollPane scrollPaneListe = new JScrollPane(liste,
                                         JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPaneListe.setPreferredSize(new Dimension(258, 70));

    scrollPaneListe.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
    scrollPaneListe.getHorizontalScrollBar().setPreferredSize(new Dimension (10, 10));

    pBut.setLayout(new BorderLayout ());

    pBut2.setLayout(new CardLayout ());
//    boutont2.setToolTipText(toolTipBut2);
    pBut1.setLayout(new CardLayout ());
    but1.setToolTipText(toolTipBut1);

    pBut.add(pBut2, BorderLayout.WEST);
    pBut.add(pBut1, BorderLayout.EAST);
    pBut2.add(but2, "b2");

//    bDial.addActionListener(auditeur);
//bDial.setFont (new Font ("sansserif", 0, 8));
//    bIgno.addActionListener(auditeur);

//    pBut.setBackground(Salon.COULEURSALONEST);
    pBut.setPreferredSize(new Dimension(112, 19));


    pPrincipale.setBorder ( BorderFactory.createCompoundBorder (
              BorderFactory.createTitledBorder (titreBoite),
              BorderFactory.createEmptyBorder (10, 10, 10, 10 )));

    pPrincipale.add(scrollPaneListe, BorderLayout.CENTER);
    pPrincipale.add (pBut, BorderLayout.SOUTH);

    return pPrincipale;
  }


public static void main (String [] args)
{
  JFrame f = new JFrame ("test");
  f.getContentPane().setLayout(new BorderLayout ());
  f.getContentPane().add ( new Info ());
  f.setSize(400, 500);
  f.setVisible(true);
}

}

