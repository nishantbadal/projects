package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;

/**
 * A class for managing player ship logic
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Ship extends Sprite {

    /**
     * Member vars
     */
    private final static Dimension SHIP_DIM = new Dimension(25,39);
    private boolean mIsMovingLeft;
    private boolean mIsMovingRight;
    private boolean bExplode;
    // doesn't let another bullet take a life if already hit by one bullet
    private boolean bCollideActive;
    private int nCounter;

    /**
     * checks if moving left
     * @return
     */
    public boolean ismIsMovingLeft() {
        return mIsMovingLeft;
    }

    /**
     * Set if moving left
     * @param mIsMovingLeft
     */
    public void setmIsMovingLeft(boolean mIsMovingLeft) {
        this.mIsMovingLeft = mIsMovingLeft;
    }

    /**
     * Checks if moving right
     * @return
     */
    public boolean ismIsMovingRight() {
        return mIsMovingRight;
    }

    /**
     * Set if moving right
     * @param mIsMovingRight
     */
    public void setmIsMovingRight(boolean mIsMovingRight) {
        this.mIsMovingRight = mIsMovingRight;
    }

    /**
     * Constructor
     * @param initPos
     */
    public Ship(Point initPos) {
        super(initPos, SHIP_DIM, SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP));
        mIsMovingLeft = false;
        mIsMovingRight = false;
        bExplode = false;
        nCounter = 0;
        bCollideActive = true;
    }

    /**
     * Unused method provided in base code
     * @param deltaTime
     */
    public void move(long deltaTime) {

    }

    /**
     * Moves right
     */
    public void moveRight() {
        this.mPos.translate(10,0);
        this.x = this.mPos.getX();
    //    this.mPos = new Point(this.mPos.x + 5, this.mPos.y);
    }

    /**
     * Moves left
     */
    public void moveLeft() {
        this.mPos.translate(-10,0);
        this.x = this.mPos.getX();
        //    this.mPos = new Point(this.mPos.x - 5, this.mPos.y);

    }

    /**
     * Shoots up to 5 bullets at a time
     * @param mBullet
     * @param mBulletTwo
     * @param mBulletThree
     * @param mBulletFour
     * @param mBulletFive
     */
    public void shoot(Bullet mBullet, Bullet mBulletTwo, Bullet mBulletThree, Bullet mBulletFour, Bullet mBulletFive) {
        if (!mBullet.stillShooting()) {
            mBullet.setBulletLocation(this.mPos.getX(), this.mPos.getY() + 50);
        } else if (!mBulletTwo.stillShooting()) {
            mBulletTwo.setBulletLocation(this.mPos.getX(), this.mPos.getY() + 50);
        } else if (!mBulletThree.stillShooting()) {
            mBulletThree.setBulletLocation(this.mPos.getX(), this.mPos.getY() + 50);
        } else if (!mBulletFour.stillShooting()) {
            mBulletFour.setBulletLocation(this.mPos.getX(), this.mPos.getY() + 50);
        } else if (!mBulletFive.stillShooting()) {
            mBulletFive.setBulletLocation(this.mPos.getX(), this.mPos.getY() + 50);
        }
    }

    /**
     * Checks if ship has collided with an enemy or enemy bullet
     * @param mBullet
     * @param mEnemy
     * @return
     */
    public boolean collide(EnemyBullet mBullet, Enemy mEnemy) {

        if (mBullet.getBounds().intersects(this.getBounds()) && bExplode == false && bCollideActive) {
            //System.out.println("this happened");
            bExplode = true;
            bCollideActive = false;
            return true;
        }

        if (mEnemy.getBounds().intersects(this.getBounds()) && bExplode == false && bCollideActive) {
            bExplode = true;
            bCollideActive = false;
            return true;
        }

        return false;
    }

    /**
     * Explodes ship if it was hit
     * @param bSubtractLife
     * @param lives
     */
    public void explode(boolean bSubtractLife, Lives lives) {
        //System.out.println(bExplode);
        if (bExplode) {

            if (nCounter == 0 || nCounter == 1) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_EXPLOSION));
                this.setmDim(new Dimension(37, 36));
                nCounter++;
            } else if (nCounter == 2 || nCounter == 3) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_EXPLOSIONTWO));
                nCounter++;
            } else if (nCounter == 4 || nCounter == 5) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_EXPLOSIONTHREE));
                nCounter++;
            } else if (nCounter > 5 && nCounter < 100 ) {
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP_EXPLOSIONFOUR));
                nCounter++;
            } else {
                this.mPos.setLocation(50, 620);
                this.setmDim(new Dimension(25, 39));
                this.x = this.mPos.getX();
                this.y = this.mPos.getY();
                this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SHIP));

                if (bSubtractLife) {
                    lives.setnLives(lives.getnLives() - 1);
                    bSubtractLife = false;
                }
                bExplode = false;
                bCollideActive = true;
                nCounter = 0;
            }

        }
    }

}
