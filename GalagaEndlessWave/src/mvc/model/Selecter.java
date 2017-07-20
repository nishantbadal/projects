package mvc.model;

import java.awt.*;

/**
 * A class for a selecter to interact with the main menu
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Selecter extends Sprite {
    // X coordiante of mainmenu selecter
    private int xMainMenu;

    /**
     * Constructor
     * @param xMainmenu
     */
    public Selecter(int xMainmenu) {
        this.xMainMenu = xMainmenu;
    }

    /**
     * Draw main menu selecter
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawString("*", 420, xMainMenu);
    }

    /**
     * Set main menu coordinate
     * @param xMainMenu
     */
    public void setxMainMenu(int xMainMenu) {
        this.xMainMenu = xMainMenu;
    }

    /**
     * Get main menu coordinate
     * @return
     */
    public int getxMainMenu() {
        return xMainMenu;
    }
}
