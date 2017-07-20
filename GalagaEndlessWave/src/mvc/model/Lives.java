package mvc.model;

import java.awt.*;

/**
 * A class for managing player lives
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Lives extends Sprite {
    // # of lives
    private int nLives;

    /**
     * Constructor
     */
    public Lives() {
        nLives = 3;
    }

    /**
     * Get number of lives
     * @return
     */
    public int getnLives() {
        return nLives;
    }

    /**
     * Set number of lives
     * @param nLives
     */
    public void setnLives(int nLives) {
        this.nLives = nLives;
    }

    /**
     * Draw number of lives
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);

        g2d.drawString("Lives: " + nLives, 10, 680);
    }
}
