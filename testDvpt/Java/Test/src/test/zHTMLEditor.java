package test;


import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.awt.*;
import java.awt.event.*;
import java.io.CharArrayWriter;
import java.io.CharArrayReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;


// Classe de fenetre Swing permettant de visualiser un
// document (HTML ou texte)
public class zHTMLEditor extends JFrame
    implements ActionListener
{
// Composant Swing permettant de visualiser un document
  public JTextPane viewer = new JTextPane ();
  public JEditorPane sourcePane = new JEditorPane ();
  public JPanel panel = new JPanel ();
  public HTMLEditorKit k = new HTMLEditorKit ();
  public HTMLDocument doc = (HTMLDocument) k.createDefaultDocument ();

  public zHTMLEditor ()
  {
// Construction de l'Interface Graphique
// Panel en haut avec un label et le champ de saisie
    viewer.setContentType ("text/html");
    viewer.setEditable (true);
    viewer.setEditorKit (k);
    viewer.setDocument (doc);
    viewer.setText ("<p></p>");

    sourcePane.setContentType ("text/plain");
    sourcePane.setEditable (false);
    showTree ();

// Zone scrollee au centre avec le document
    JScrollPane scrollPane = new JScrollPane (viewer);
    JScrollPane scrollPane2 = new JScrollPane (sourcePane);
    JPanel panel2 = new JPanel ();
    panel2.add (scrollPane, null);
    panel2.add (scrollPane2, null);
    panel2.setSize (200, 200);

// Ajout des composants a la fenetre
    setJMenuBar (createMenuBar ());
    panel.add (createToolBar (), BorderLayout.SOUTH);
    getContentPane ().add (scrollPane2, BorderLayout.SOUTH);
    getContentPane ().add (scrollPane, BorderLayout.CENTER);
    getContentPane ().add (panel, BorderLayout.NORTH);
  }


  public JMenuBar createMenuBar ()
  {
    JMenuBar menubar = new JMenuBar ();

    JMenu color = new JMenu ("Color");
    color.add (new StyledEditorKit.ForegroundAction ("Noir", Color.black));
    color.add (new StyledEditorKit.ForegroundAction ("Bleu", Color.blue));
    color.add (new StyledEditorKit.ForegroundAction ("Rouge", Color.red));
    color.add (new StyledEditorKit.ForegroundAction ("Jaune", Color.yellow));
    menubar.add (color);

    return menubar;
  }


  public JToolBar createToolBar ()
  {
    JToolBar bar = new JToolBar ();
    JButton boldButton = new JButton ();
    JButton italicButton = new JButton ();
    JButton underlineButton = new JButton ();
    JButton colorButton = new JButton ();
    JButton cutButton = new JButton ();
    JButton copyButton = new JButton ();
    JButton pasteButton = new JButton ();
    JButton leftButton = new JButton ();
    JButton centerButton = new JButton ();
    JButton rightButton = new JButton ();
    JButton testButton = new JButton ();
    JButton linkButton = new JButton ();
    JButton imageButton = new JButton ();
    JButton h1Button = new JButton ();
    JButton h2Button = new JButton ();
    JButton h3Button = new JButton ();

    Action a = viewer.getActionMap ().get ("font-bold");
    if (a != null)
    {
      boldButton = bar.add (a);
      boldButton.setText ("G");
    }
    a = viewer.getActionMap ().get ("font-italic");
    if (a != null)
    {
      italicButton = bar.add (a);
      italicButton.setText ("I");
    }
    a = viewer.getActionMap ().get ("font-underline");
    if (a != null)
    {
      underlineButton = bar.add (a);
      underlineButton.setText ("S");
    }
    bar.addSeparator ();
    a = viewer.getActionMap ().get (StyledEditorKit.cutAction);
    if (a != null)
    {
      cutButton = bar.add (a);
      cutButton.setText ("X");
    }
    a = viewer.getActionMap ().get (StyledEditorKit.copyAction);
    if (a != null)
    {
      copyButton = bar.add (a);
      copyButton.setText ("C");
    }
    a = viewer.getActionMap ().get (StyledEditorKit.pasteAction);
    if (a != null)
    {
      pasteButton = bar.add (a);
      pasteButton.setText ("V");
    }
    bar.addSeparator ();
    a = new StyledEditorKit.AlignmentAction ("left", 0);
    if (a != null)
    {
      leftButton = bar.add (a);
      leftButton.setText ("<");
    }
    a = new StyledEditorKit.AlignmentAction ("center", 1);
    if (a != null)
    {
      centerButton = bar.add (a);
      centerButton.setText ("-");
    }
    a = new StyledEditorKit.AlignmentAction ("right", 2);
    if (a != null)
    {
      rightButton = bar.add (a);
      rightButton.setText (">");
    }
    bar.addSeparator ();
    h1Button.addActionListener (new ActionListener ()
    {
      public void actionPerformed (ActionEvent event)
      {
        try
        {
          HTML.Tag htmlTag = HTML.Tag.H1;
          Hashtable htmlAttribs = new Hashtable ();
          String selText = viewer.getSelectedText ();
          int selStart = viewer.getSelectionStart ();
          int textLength = selText.length ();
          String myAnchor = "";
          viewer.select (selStart, selStart + textLength);
          SimpleAttributeSet sasTag = new SimpleAttributeSet ();
          SimpleAttributeSet sasAttr = new SimpleAttributeSet ();
//String newAnchor = "http://www.bsf-qc.com";
//htmlAttribs.put("href",newAnchor);
          Enumeration attribEntries = htmlAttribs.keys ();
          while (attribEntries.hasMoreElements ())
          {
            Object entryKey = attribEntries.nextElement ();
            Object entryValue = htmlAttribs.get (entryKey);
            sasAttr.addAttribute (entryKey, entryValue);
            htmlAttribs.remove (entryKey);
          }
          sasTag.addAttribute (htmlTag, sasAttr);
          viewer.setCharacterAttributes (sasTag, false);
          viewer.setText (viewer.getText ());
          viewer.select (selStart, selStart + textLength);
        }
        catch (Exception ignoredForNow)
        {}
      }
    });
    h1Button.setText ("Titre");
    bar.add (h1Button);
    h2Button.addActionListener (new ActionListener ()
    {
      public void actionPerformed (ActionEvent event)
      {
        try
        {
          HTML.Tag htmlTag = HTML.Tag.H2;
          Hashtable htmlAttribs = new Hashtable ();
          String selText = viewer.getSelectedText ();
          int selStart = viewer.getSelectionStart ();
          int textLength = selText.length ();
          viewer.select (selStart, selStart + textLength);
          SimpleAttributeSet sasTag = new SimpleAttributeSet ();
          SimpleAttributeSet sasAttr = new SimpleAttributeSet ();
//String newAnchor = "http://www.bsf-qc.com";
//htmlAttribs.put("href",newAnchor);
          Enumeration attribEntries = htmlAttribs.keys ();
          while (attribEntries.hasMoreElements ())
          {
            Object entryKey = attribEntries.nextElement ();
            Object entryValue = htmlAttribs.get (entryKey);
            sasAttr.addAttribute (entryKey, entryValue);
            htmlAttribs.remove (entryKey);
          }
          sasTag.addAttribute (htmlTag, sasAttr);
          viewer.setCharacterAttributes (sasTag, false);
          viewer.setText (viewer.getText ());
          viewer.select (selStart, selStart + textLength);
        }
        catch (Exception ignoredForNow)
        {}
      }
    });
    h2Button.setText ("Titre 2");
    bar.add (h2Button);
    h3Button.addActionListener (new ActionListener ()
    {
      public void actionPerformed (ActionEvent event)
      {
        try
        {
          HTML.Tag htmlTag = HTML.Tag.H3;
          Hashtable htmlAttribs = new Hashtable ();
          String selText = viewer.getSelectedText ();
          int selStart = viewer.getSelectionStart ();
          int textLength = selText.length ();
          String myAnchor = "";
          viewer.select (selStart, selStart + textLength);
          SimpleAttributeSet sasTag = new SimpleAttributeSet ();
          SimpleAttributeSet sasAttr = new SimpleAttributeSet ();
//String newAnchor = "http://www.bsf-qc.com";
//htmlAttribs.put("href",newAnchor);
          Enumeration attribEntries = htmlAttribs.keys ();
          while (attribEntries.hasMoreElements ())
          {
            Object entryKey = attribEntries.nextElement ();
            Object entryValue = htmlAttribs.get (entryKey);
            sasAttr.addAttribute (entryKey, entryValue);
            htmlAttribs.remove (entryKey);
          }
          sasTag.addAttribute (htmlTag, sasAttr);
          viewer.setCharacterAttributes (sasTag, false);
          viewer.setText (viewer.getText ());
          viewer.select (selStart, selStart + textLength);
        }
        catch (Exception ignoredForNow)
        {}
      }
    });
    h3Button.setText ("Titre 3");
    bar.add (h3Button);

    testButton.addActionListener (new ActionListener ()
    {
      public void actionPerformed (ActionEvent event)
      {
        showTree ();
      }
    });
    bar.add (testButton);
    testButton.setText ("Show Tree");

    return bar;
  }


  public void init ()
  {
    setSize (600, 600);
    show ();
  }


  public void actionPerformed (ActionEvent event)
  {
  }


  public void showTree ()
  {
    Reader reader = null;
    char[] chars = getSource ();
    reader = new CharArrayReader (chars);
    Document docum = new PlainDocument ();
    try
    {
      sourcePane.getEditorKit ().read (reader, docum, 0);
    }
    catch (Exception ignoredForNow)
    {}
    sourcePane.setDocument (docum);
  }


  public char[] getSource ()
  {
    CharArrayWriter writer = null;
    writer = new CharArrayWriter ();
    try
    {
      viewer.getEditorKit ().write (writer, viewer.getDocument (), 0,
                                    viewer.getDocument ().getLength ());
    }
    catch (Exception ignoredForNow)
    {}
    return writer.toCharArray ();
  }


  public String getTree ()
  {
    return sourcePane.getText ();
  }

  public static void  main (String [] args)
  {
    zHTMLEditor z=  new zHTMLEditor ();
    z.init();
  }
}
