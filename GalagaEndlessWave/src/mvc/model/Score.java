package mvc.model;

import java.awt.*;

/**
 * A class for score displayed as player plays, constantly updated
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Score extends Sprite {
    /**
     * Member vars
     */
    private static int nScore;
    private int nX;
    private int nY;

    /**
     * Score constructor
     */
    public Score() {
        nScore = 0;
        nX = 10;
        nY = 20;
    }

    /**
     * Get score
     * @return
     */
    public static int getnScore() {
        return nScore;

    }

    /**
     * Set score
     * @param nScore
     */
    public void setnScore(int nScore) {
        this.nScore = nScore;
    }

    /**
     * Draw score
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);

        g2d.drawString("Score: " + nScore, nX, nY);
    }

    /**
     * Change coordinates to show player score at the end of the game
     */
    public void showScore() {
        nX = 525;
        nY = 550;
    }
}
