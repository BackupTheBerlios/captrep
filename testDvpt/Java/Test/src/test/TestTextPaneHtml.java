package test;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.event.HyperlinkEvent.EventType;
import test.gui.JTextPaneHtml;
import test.gui.event.MouseTextPaneListener;
import test.gui.event.MouseTextPaneEvent;
import test.gui.event.MouseMotionTextPaneListener;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestTextPaneHtml extends JFrame
{
  JPanel jPanel1 = new JPanel ();
  BorderLayout borderLayout1 = new BorderLayout ();
//  JTextPane textPane = new TextPaneToolTip ();
  JTextPaneHtml textPane = new JTextPaneHtml ();
  JPanel jPanel2 = new JPanel ();
  JPanel jPanel3 = new JPanel ();
  JPanel jPanel4 = new JPanel ();
  JPanel jPanel5 = new JPanel ();
  JButton jButton1 = new JButton ();
  JTextField textFiel = new JTextField ();
  FlowLayout flowLayout1 = new FlowLayout ();
  JPanel jPanel6 = new JPanel ();
  BorderLayout borderLayout2 = new BorderLayout ();
  JPanel jPanel7 = new JPanel ();
  JPanel jPanel8 = new JPanel ();
  BorderLayout borderLayout3 = new BorderLayout ();
  JPanel jPanel9 = new JPanel ();
  JPanel jPanel10 = new JPanel ();
  FlowLayout flowLayout2 = new FlowLayout ();
  FlowLayout flowLayout3 = new FlowLayout ();
  BorderLayout borderLayout4 = new BorderLayout ();

  public HTMLEditorKit k = new HTMLEditorKit ();
  public HTMLDocument doc = (HTMLDocument) k.createDefaultDocument ();
  //private static String END_TAG_HTML = "<a id=\"PataCara\"></a></body></html>";
  private String pseudo = "patachou";
  private static String HTML_DELIM = "'";
//  private static Color COULEUR_PSEUDO = new Color (0x0303B6);
  private static String COULEUR_PSEUDO = "0303B6";


  //pour les test
  private int identifiantCourantTR =0;
  private GestionLigneTextPane gestionnaireLigne = null;


  public TestTextPaneHtml ()
  {
    try
    {
      win = new JWindow (this);
      win.getContentPane().setLayout(new BorderLayout (0, 0));
      jbInit ();
      setSize (600, 500);
      setVisible (true);
    }
    catch (Exception e)
    {
      e.printStackTrace ();
    }
  }
  
  
  public static void main (String[] args)
  {
    javax.swing.UIManager.LookAndFeelInfo [] info = UIManager.getInstalledLookAndFeels();
    if (null != info)
    {
      for (int i = 0; i < info.length; ++i)
     ;//   System.out.println(info[i]);
    }
    try
    {
      UIManager.setLookAndFeel (info[2].getClassName ());
    }
    catch (UnsupportedLookAndFeelException ex)
    {
    }
    catch (IllegalAccessException ex)
    {
    }
    catch (InstantiationException ex)
    {
    }
    catch (ClassNotFoundException ex)
    {
    }
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          TestTextPaneHtml testTextPaneHtml1 = new TestTextPaneHtml ();
          SwingUtilities.updateComponentTreeUI(testTextPaneHtml1);

        }
    });

  }

  private JWindow win;

  private void jbInit () throws Exception
  {

    //textPane.setToolTipText ("Mon TextPane a moi");
    textPane.setEditable (false);
    textPane.setCaretPosition (0);

    textPane.setEditorKit (k);

//System.out.println("document d : " + doc);
//System.out.println("document textPane : " + textPane.getDocument());

    textPane.setDocument (doc);
//System.out.println("document d : " + doc);
//System.out.println("document textPane : " + textPane.getDocument());

//    textPane.setContentType ();

//    textPane.setText ("<html><body id=\"DebutTables\">" + END_TAG_HTML);
    ((JTextPaneHtml) textPane).addMouseMotionTextPaneListener(new MouseMotionTextPaneListener () {

      public void mouseTextPaneDragged (MouseTextPaneEvent e)
      {
        
        
        /* mouseTextPaneDragged () */
      }

      public void mouseTextPaneMoved (MouseTextPaneEvent e)
      {
        
//        Color c = Color.CYAN;
//        
//        //JPanel p = new JPanel ();
//        JToolTip tip = new JToolTip ();
//        tip.setBackground(c);
//        Border b = new LineBorder (GestionLigneTextPane.darker(c));
//        tip.setBorder(b);
//        tip.setTipText("remyyyyyyyyy");
//        //p.add (tip);
//        win.getContentPane().add (tip, BorderLayout.CENTER);
//        win.pack();
//        Point p = new Point (e.getMouseEvent().getX(), e.getMouseEvent().getY());
//        SwingUtilities.convertPointToScreen(p, e.getComponent());
//        //p.y += win.getHeight() + 7;
//        p.x += 15;
//        win.setLocation (p);
//        win.setVisible(true);
        //win.toFront();
        //System.out.println ("fin win, border : ");
        
        
        /* mouseTextPaneMoved () */
      }});

    
    ((JTextPaneHtml) textPane).addMouseTextPaneListener(new MouseTextPaneListener () {
      /**
       * mouseTextPaneClicked
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneClicked (MouseTextPaneEvent e)
      {
        System.out.println ("" + GestionLigneTextPane. frameForComponent (textPane)); 
        Element el = e.getElement();
        if (el == null)
          System.out.println("mouseTextPaneClicked");
        else
        {
          System.out.println ("mouseTextPaneClicked, element : " + el);
          if (SwingUtilities.isLeftMouseButton(e.getMouseEvent()))
            monTest (e);
          else if (SwingUtilities.isRightMouseButton(e.getMouseEvent()))
            changeText (e.getElement ());
        }
      }

private void monTest (MouseTextPaneEvent e)
{
  
  
        Element elTR = findElementXX (Tag.TR, e.getElement ());
        if (null == elTR)
          System.out.println ("rien a faire");
        else
        {
            String idTR = findIdTR(elTR);
            System.out.println("TR LU (id=" + idTR + ") : " + elTR + ", has code : " + elTR.hashCode());
            if (null == idTR)
            {
              System.out.println("id TR pas trouvé dans monTest");
              return;
            }
            //gestionnaireLigne.ligneOnMouseExit(elTR.getStartOffset(), elTR.getEndOffset(), idTR);
        }
        
        
}

      /**
       * mouseTextPaneEntered
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneEntered (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPaneEntered");
      }


      /**
       * mouseTextPaneExited
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneExited (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPaneExited");
      }


      /**
       * mouseTextPanePressed
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPanePressed (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPanePressed");
      }


      /**
       * mouseTextPaneReleased
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneReleased (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPaneReleased");
      }
    });



    ((JTextPaneHtml) textPane).addMouseMotionTextPaneListener(new MouseMotionTextPaneListener () {
      /**
       * mouseTextPaneDragged
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneDragged (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPaneDragged : " + e.getElement());
      }


      /**
       * mouseTextPaneMoved
       *
       * @param e MouseTextPaneEvent
       */
      public void mouseTextPaneMoved (MouseTextPaneEvent e)
      {
//        System.out.println("mouseTextPaneMoved : " + e.getElement());
      }
    });


    textPane.addHyperlinkListener(new HyperlinkListener()
    {
      public void hyperlinkUpdate(HyperlinkEvent e)
      {
//        System.out.println("evt : " + e.getDescription());
        if (e.getEventType() != EventType.ACTIVATED)
          return;
       // System.out.println(e);
System.out.println("////////////////////////////////////////////////////////");

//System.out.println("nom élément : " + e.getSourceElement().getName() + ", " + e.getSourceElement());
       Element el = e.getSourceElement();
       if (e.equals(Tag.IMG))
       {
//         System.out.println("tag egale");
       }
       else
;//         System.out.println("tag different");
       Element pere = el.getParentElement ().getParentElement().getParentElement();
       if (pere == null)
       {
         System.out.println("pere null");
         return;
       }
       else
       {
        try
        {
          System.out.println ("nom pere = " + pere.getName () + ", " + pere +
                              ", text : " +
                              textPane.
                              getText (pere.getStartOffset (),
                                       (pere.getEndOffset () -
                                        pere.getStartOffset ())
                                       ));
        }
        catch (BadLocationException ex1)
        {
          ex1.printStackTrace();
        }
       }


       AttributeSet setEl = pere.getAttributes();
/*System.out.println("nom balise pere : " + setEl.getAttribute(setEl.NameAttribute));
if (setEl.getAttribute(setEl.NameAttribute).equals(Tag.TR))
System.out.println("yessssssss TR trouvé");
       Enumeration enumEl = setEl.getAttributeNames();
       System.out.println("nombre d'attribut = " + el.getElementCount());
       for (int j=0;enumEl.hasMoreElements();++j)
       {
         Object o = enumEl.nextElement();
         System.out.println("nomAttrib (" + j + ") : " + o);
         System.out.println("obj (" + j + ") : " + setEl.getAttribute(o));
         if (setEl.getAttribute(o) instanceof Tag)
           System.out.println("String (" + j + ") : oui");
         else
           System.out.println("String (" + j + ") : non");
       }
       for (int i = 0; i < pere.getElementCount(); ++i)
       {
         System.out.println("------------------------------------------------------------------------------------");
         Element tmp = pere.getElement(i);
        try
        {
          System.out.println ("el (" + i + ") : " + tmp + ", text : " +
                              doc.
                              getText (tmp.getStartOffset (), (tmp.getEndOffset () - tmp.getStartOffset())));
          AttributeSet setTmp = tmp.getAttributes();
          Enumeration enumTmp = setTmp.getAttributeNames();
          for (int j = 0;enumTmp.hasMoreElements();++j)
          {
            Object o = enumTmp.nextElement();
            System.out.println("nomAttrib (" + j + ") : " + o);
            System.out.println("obj (" + j + ") : " + setTmp.getAttribute(o));
          }
        }
        catch (BadLocationException ex)
        {
          ex.printStackTrace();
        }
       }
       Object o;
       if ((o = setEl.getAttribute( Attribute.NAME)) != null)
       {
         System.out.println("Valeur de src : " + o);
       }
       else
       {
       System.out.println("Valeur de src pas trouvé : ");
       }
*/

       if (!(e instanceof HTMLFrameHyperlinkEvent))
       {
         ((JTextPaneHtml) textPane).setCouleurToolTipBackground (Color.red);
         textPane.setToolTipText("toto + " + e.getDescription());

       }
      }
    });

    jPanel1.setLayout (borderLayout1);
    jPanel2.setLayout (borderLayout2);
    jButton1.setText ("jButton1");
    jButton1.addActionListener (new TestTextPaneHtml_jButton1_actionAdapter (this));
    textFiel.setText ("Texte <img src='file:images/smile.gif'> du Lien");
    textFiel.addActionListener (new TestTextPaneHtml_textFiel_actionAdapter (this));
    jPanel3.setLayout (flowLayout1);
    flowLayout1.setAlignment (FlowLayout.CENTER);
    flowLayout1.setHgap (10);
    jPanel6.setLayout (borderLayout3);
    jPanel4.setPreferredSize (new Dimension (10, 10));
    jPanel10.setLayout (flowLayout2);
    flowLayout2.setHgap (2);
    flowLayout2.setVgap (2);
    jPanel9.setLayout (flowLayout3);
    flowLayout3.setVgap (2);
    JScrollPane scroll = new JScrollPane (textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane ().setLayout (borderLayout4);
    jPanel2.add (textFiel, BorderLayout.CENTER);
    jPanel2.add (jButton1, BorderLayout.EAST);
    jPanel6.add (jPanel9, BorderLayout.SOUTH);
    jPanel6.add (jPanel10, BorderLayout.NORTH);
    jPanel1.add (scroll, BorderLayout.CENTER);
    jPanel1.add (jPanel3, BorderLayout.NORTH);
    jPanel1.add (jPanel4, BorderLayout.EAST);
    jPanel1.add (jPanel5, BorderLayout.WEST);
    jPanel1.add (jPanel6, BorderLayout.SOUTH);
    jPanel6.add (jPanel8, BorderLayout.EAST);
    jPanel6.add (jPanel7, BorderLayout.WEST);
    jPanel6.add (jPanel2, BorderLayout.CENTER);
    this.getContentPane ().add (jPanel1, BorderLayout.CENTER);

    //A créer apres avoir ajouter le textPane à la fenetre.
    gestionnaireLigne = new GestionLigneTextPane (textPane);

  }

  private void changeText (Element e)
  {
 //   try
//    {
      //gestionnaireLigne.ligneOnMouseOver (e);
    System.out.println ("textPane : " + textPane.getText());
/*
      ElementLigneTextPane infoLigne = gestionnaireLigne.getLigneTextPane(idTR);
      if (null == infoLigne)
      {
        System.out.println("info ligne null");
        return;
      }
     ((JTextPaneHtml) textPane). insertHTML( pere.getEndOffset(), "<tr text=\"" + idTR + "\"><td>" + infoLigne.getContenuLigne() + idTR + "</td></tr>");

     gestionnaireLigne.modifierLigneMap (idTR, infoLigne.getContenuLigne(),
                                            infoLigne.getToolTip());


      ( (HTMLDocument) textPane.getDocument ()).remove (pere.getStartOffset (),
          pere.getEndOffset () - pere.getStartOffset ());


    }
    catch (BadLocationException ex2)
    {
      ex2.printStackTrace();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
 }*/
  }


  /**
   * Formate le message msg en y ajoutant la structure neccessaire.
   *
   * @return String
   * @param msg String
   */
  private String formatMessage (String msg)
  {
    StringBuffer msgBuff = new StringBuffer ();
    //Ajout du pseudo avec la bonne couleur
    msgBuff.append ("<font color='#");
    msgBuff.append(COULEUR_PSEUDO);
    msgBuff.append("'>");
    msgBuff.append(pseudo);
    msgBuff.append("&gt;</font>"); // ajout de > apres le pseudo
    msgBuff.append(msg);
    return msgBuff.toString();
  }


  /**
   * Remplace les caracteres HTML spécifiques en caractères textes.
   * @param src String
   * @return String
   */
  public static String stripTags(String src){
    StringBuffer sb = new StringBuffer();
    for(int i=0;i<src.length();i++){
      char c= src.charAt(i) ;
      switch(c)
      {
        case '<':sb.append("&lt;"); break;
        case '>':sb.append("&gt;"); break;
        case '"':sb.append("&quot;"); break;
        case '&':sb.append("&amp;"); break;
        default: sb.append(c);
      }
    }
    return sb.toString();
  }





  void jButton1_actionPerformed (ActionEvent e)
  {
//Ajout du text dans le text Pane
    System.out.println ("cliqué");
    String textSaisie = textFiel.getText ();
    if ( (null == textSaisie) || (textSaisie.length () == 0))
      return;
//    textSaisie.concat ("\n");

      System.out.println ("textSaisie " + textSaisie);
      System.out.println("textpane : " + textPane.getText());
      HTMLDocument textdoc = (HTMLDocument) textPane.getDocument ();

      Element el = textdoc.getElement ("DebutTables");
      if (el == null)
      {
        System.out.println ("element null");
        return;
      }


      String textHRef = null;
          textSaisie = stripTags(textSaisie);
          textHRef = formatMessage (textSaisie);
System.out.println("textHref : " + textHRef);

          gestionnaireLigne.ajouteLigneTextPaneBeforeEnd (el, textHRef,
                                                 textSaisie);
          textPane.setCaretPosition(doc.getLength());
      textFiel.setText ("");
      
      JComponent j;
//      System.out.println ("TextPane apres :" + textPane.getText ());

  }


  /**
   * Trouve l'élément tag à partir de l'élément source et en remontant dans les
   * branches.
   * 
   * @param tag le tag recherché (par ex : Tag.TR).
   * @param source élément à partir duquel chercher.
   * @return l'element TR ou null si pas trouvé.
   */
  private static Element findElementXX (Tag tag, Element source)
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
  private static boolean verifElementEqualTag (Element el, Tag tag)
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



}






class TestTextPaneHtml_jButton1_actionAdapter implements java.awt.event.ActionListener
{
  TestTextPaneHtml adaptee;

  TestTextPaneHtml_jButton1_actionAdapter(TestTextPaneHtml adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jButton1_actionPerformed(e);
  }
}

class TestTextPaneHtml_textFiel_actionAdapter implements java.awt.event.ActionListener
{
  TestTextPaneHtml adaptee;

  TestTextPaneHtml_textFiel_actionAdapter(TestTextPaneHtml adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jButton1_actionPerformed(e);
  }
  
  
  

}
