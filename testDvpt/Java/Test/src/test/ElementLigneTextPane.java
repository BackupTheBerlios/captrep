package test;

/**
 * <p>Title: ElementLigneTextPane.java</p>
 * <p>Description: Represente tout ce qu'il faut savoir pour afficher correctement une ligne dans PataCara. </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Giraudr
 * @version 1.0
 */

public class ElementLigneTextPane
{
  private String contenuLigne = null;
  private String toolTipLigne = null;

  /**
   * Construit une ligne avec le contenu et le toolTip donné.
   * @param contenuLigne le contenu de la ligne.
   * @param toolTipLigne le toolTip qui sera associé au text.
   */
  public ElementLigneTextPane (String contenuLigne, String toolTipLigne)
  {
    this.contenuLigne = contenuLigne;
    this.toolTipLigne = toolTipLigne;
  } // Constructeur ElementLigneTextPane


  /**
   *
   * @return Le contenu de la ligne.
   */
  public String getContenuLigne ()
  {
    return contenuLigne;
  } /* getContenuLigne() */


  /**
   * Fise le contenu de la ligne.
   * @param contenuLigne le contenu de la ligne
   */
  public void setContenuLigne (String contenuLigne)
  {
    this.contenuLigne = contenuLigne;
  } /* setContenuLigne() */

  public String getToolTip ()
  {
    return toolTipLigne;
  } /* getToolTip() */


  /**
   * Fise le toolTip de la ligne
   * @param toolTip le toolTip de la ligne.
   */
  public void setToolTop (String toolTip)
  {
    this.toolTipLigne = toolTip;
  }

} // Classe ElementLigneTextPane
