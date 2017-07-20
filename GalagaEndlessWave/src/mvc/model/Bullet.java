package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A class for player bullets
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Bullet extends Sprite {
    /**
     * Member vars
     */
    private final static Dimension BULLET_DIM = new Dimension(3, 8);

    //Checks if bullet is shooting
    private boolean bShooting;

    // checks if bullet is in position
    private boolean bInPosition;

    /**
     * Constructor
     * @param initPos
     */
    public Bullet(Point initPos) {
        super(initPos, BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_BULLET));
        bInPosition = false;
        bShooting = false;
    }

    /**
     * Moves bullet downwards
     */
    public void move() {
        this.mPos.translate(0, -10);
        this.y = mPos.getY();
    }

    /**
     * Sets bullet location in front of attacking enemy
     * @param x
     * @param y
     */
    public void setBulletLocation(double x, double y) {
        this.mPos.setLocation(x + 11, y - 55);
        this.x = mPos.getX();
        this.y = mPos.getY();
    }

    /**
     * Returns whether the bullet is shooting
     * @param shooting
     */
    public void setbShooting(boolean shooting) {
        bShooting = shooting;
    }

    /**
     * If still shooting, keep moving bullet downwards
     * @return
     */
    public boolean stillShooting() {
        if (this.mPos.getY() > -10) {
            move();
            return true;
        } else {
            setbShooting(false);
            return false;
        }
    }
}
