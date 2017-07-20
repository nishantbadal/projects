package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class for logic all enemies use
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Enemy extends Sprite {

    /**
     * Member vars
     */
    // Is enemy exploding
    private boolean bExplode;
    // Counter for exploding animation
    private int nCounter;
    // Is enemy dead
    private boolean bDead;


    /**
     * Default constructor
     */
    public Enemy() {

    }

    /**
     * Constructor
     * @param initPos
     */
    public Enemy(Point initPos) {
        super(initPos, new Dimension(28, 23), SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER));

        bExplode = false;
        nCounter = 0;
        bDead = false;

    }

    /**
     * Returns if enemy is exploding
     * @return
     */
    public boolean getbExplode() {
        return bExplode;
    }

    /**
     * Returns if enemy is dead
     * @return
     */
    public boolean getbDead() {
        return bDead;
    }

    /**
     * Returns if there is a collision with a bullet, set bExplode to true if there is to trigger animation
     * @param mBullet
     * @return
     */
    public boolean collide(Bullet mBullet) {
        if (mBullet.getBounds().intersects(this.getBounds()) && bExplode == false) {
            bExplode = true;
            return true;
        }
        return false;
    }

    /**
     * Exploding animation
     */
    public void explode() {
        if (bExplode) {
            if (nCounter == 0) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.EXPLOSION));
                nCounter++;
            } else if (nCounter == 1) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.EXPLOSIONTWO));
                nCounter++;
            } else if (nCounter == 2) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.EXPLOSIONTHREE));
                nCounter++;
            } else if (nCounter == 3) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.EXPLOSIONFOUR));
                nCounter++;
            } else if (nCounter == 4) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.EXPLOSIONFIVE));
                nCounter++;
            } else {
                this.mPos.setLocation(-100, -200);
                bDead = true;
            }
        }
    }
}
