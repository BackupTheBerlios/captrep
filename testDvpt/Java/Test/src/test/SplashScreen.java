package test;

import java.awt.*;


/**
 * @author ludo
 * Ma class splash screen difficile de faire + simple ;o)
 */
public class SplashScreen extends Frame
{
  Image[] img;

  public SplashScreen ()
  {
    super ();
    setSize (400, 300);
    setUndecorated (true);
    setFocusable (false);
    setEnabled (false);
    String fileloc = "./images/logo.jpg";
    img = new Image[2];
    img[0] = this.getToolkit ().createImage (fileloc);
    img[1] = this.getToolkit ().createImage ("./images/logo1.jpg");
    try
    {
      MediaTracker mTrack = new MediaTracker (this); // load les image avan de les afficher
      for (int i = 0; i < img.length; i++)
      {
        mTrack.addImage (img[i], i);
      }
      mTrack.waitForAll ();
    }
    catch (Exception e)
    {System.out.println (" setimages e : " + e);
    }
  }


  public void paint (Graphics g)
  {
    super.paint (g);
    Dimension d = this.getSize ();
    g.drawImage (img[0], 0, 0, d.width, d.height, this); // dessine l image
    SplashScreen e;
  }


  static public void main (String args[])
  {
    try
    {
      GraphicsEnvironment ge = GraphicsEnvironment.
                               getLocalGraphicsEnvironment ();
      GraphicsDevice[] gs = ge.getScreenDevices ();
      GraphicsDevice gd = gs[0];
      GraphicsConfiguration[] gc = gd.getConfigurations ();
      Rectangle r = gc[0].getBounds ();
      Point pt = new Point ( (int) r.width / 2, (int) r.height / 2);
      SplashScreen sp = new SplashScreen ();
      Point loc = new Point (pt.x - 200, pt.y - 150);
      sp.setLocation (loc);
      sp.setVisible (true);

      // apres fau metre le splash en parametre a l apli
      // et c elle ki fait le setvisible (false ) qd elle a fini de bosser
      // genre ( sur son setvisible( true ) ou a la fin du constructeur ou autre... ;o)


      //comme sa on peu pas la lancer plus tot ;o)

    }
    catch (Exception e)
    {
      System.out.println ("enclosing_package.enclosing_method : " + e);
    }
  }

}
