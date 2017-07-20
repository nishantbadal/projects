package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;
/**
 * A class for enemy bullets
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class EnemyBullet extends Sprite {
    /**
     * Member vars
     */
    private final static Dimension BULLET_DIM = new Dimension(3, 8);
    private boolean bShooting;
    private boolean bLeft;
    private boolean bRight;

    // checks if bullet is in position
    private boolean bInPosition;

    /**
     * Constructor
     * @param initPos
     * @param bLeft
     * @param bRight
     */
    public EnemyBullet(Point initPos, boolean bLeft, boolean bRight) {
        super(initPos, BULLET_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_BULLET_DOWN));
        bInPosition = false;
        bShooting = false;
        setDim(bLeft, bRight);
        this.bLeft = bLeft;
        this.bRight = bRight;
    }

    /**
     * Set Dimension
     * @param bLeft
     * @param bRight
     */
    public void setDim(boolean bLeft, boolean bRight) {
        if (bLeft) {
            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_BULLET_LEFT));
        } else if (bRight) {
            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY_BULLET_RIGHT));
        }
    }

    /**
     * Move formation
     */
    public void move() {
        if (bLeft) {
            this.mPos.translate(-15, 15);
            this.setmDim(new Dimension(7, 7));
        } else if (bRight) {
            this.mPos.translate(15, 15);
            this.setmDim(new Dimension(7, 7));
        } else {
            this.mPos.translate(0, 15);
        }

        this.x = mPos.getX();
        this.y = mPos.getY();
    }

    /**
     * Set bullet location for firing enemies
     * @param x
     * @param y
     */
    public void setBulletLocation(double x, double y) {
        this.mPos.setLocation(x, y + 25);
        this.x = mPos.getX();
        this.y = mPos.getY();
    }

    /**
     * Set if shooting
     * @param shooting
     */
    public void setbShooting(boolean shooting) {
        bShooting = shooting;
    }

    /**
     * Get if shooting
     * @return
     */
    public boolean getbShooting() {
        return bShooting;
    }

    /**
     * If the bullet has not hit the bottom, keep moving
     * @return
     */
    public boolean stillShooting() {
        if (this.mPos.getY() != 700 && this.mPos.getY() > 100) {
            move();
            return true;
        } else {
            setbShooting(false);
            return false;
        }
    }
}
