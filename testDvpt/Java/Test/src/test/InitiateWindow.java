package test;

import javax.swing.*;
import java.awt.*;
//import com.borland.jbcl.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InitiateWindow extends JWindow
{
  public InitiateWindow()
  {
//  setVisible(true);
  }
  public static void main(String[] args)
  {
    //InitiateWindow initiateWindow1 = new InitiateWindow();
    JWindow fentre = createfenetreChargement();
    fentre.setVisible(true);
    System.out.println("Ok");
  }
  private void jbInit() throws Exception
  {
  }

  private static JWindow createfenetreChargement ()
  {
    JWindow fenetreChargement = new JWindow ();

    //Initialisation de la fenetre
    JLabel lTextAttente = new JLabel();
    JLabel jLabel1 = new JLabel();
    JPanel pCentre = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();

    ImageIcon iLogo = new ImageIcon(InitiateWindow.class.getResource("/images/logo_attente.jpg"));
    lTextAttente.setFont(new java.awt.Font("Dialog", 1, 13));
    lTextAttente.setOpaque(false);
    lTextAttente.setPreferredSize(new Dimension(260, 21));
    lTextAttente.setRequestFocusEnabled(true);
    lTextAttente.setToolTipText("Attendez s\'il vous plait");
    lTextAttente.setText("<html>Installation de PataCara en cours<br><center>....................</center></html>");
    lTextAttente.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    lTextAttente.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    lTextAttente.setBounds(new Rectangle(68, 163, 260, 48));
    fenetreChargement.getContentPane().setBackground(SystemColor.info);
    fenetreChargement.setEnabled(false);
    fenetreChargement.getContentPane().setLayout(borderLayout1);
    jLabel1.setIcon(iLogo);
    jLabel1.setText("");
    jLabel1.setBounds(new Rectangle(46, 52, 305, 73));
    iLogo.setDescription("");
    pCentre.setLayout(null);
    borderLayout1.setHgap(30);
    borderLayout1.setVgap(30);
//    pCentre.setBackground(SystemColor.info);
    pCentre.setBackground(SystemColor.info);
    pCentre.setFont(new java.awt.Font("MS Sans Serif", 0, 11));
    pCentre.setForeground(Color.black);
    pCentre.add(lTextAttente, null);
    pCentre.add(jLabel1, null);
    fenetreChargement.getContentPane().add(pCentre, BorderLayout.CENTER);


    //Mise en forme
    fenetreChargement.setSize (394, 265);
    java.awt.Rectangle screenRect = fenetreChargement.getGraphicsConfiguration().getBounds();
    fenetreChargement.setLocation(screenRect.x + screenRect.width /2 - fenetreChargement.getSize().width /2,
      screenRect.y + screenRect.height/2 - fenetreChargement.getSize().height/2);

    return fenetreChargement;
  }

}
