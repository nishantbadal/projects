package mvc.model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class for sprites
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public abstract class Sprite extends Rectangle.Double {

    /** The dimensions of the sprite */
    private Dimension mDim;

    /** The position of the sprite */
    protected Point mPos;

    /** The texture for the sprite */
    private BufferedImage mTex;

    public Sprite() {

    }

    public Sprite(Point initPos, Dimension dim, BufferedImage texture) {
        this.mPos = initPos;
        this.mDim = dim;
        this.mTex = texture;
        this.x = mPos.getX();
        this.y = mPos.getY();
        this.width = dim.getWidth();
        this.height = dim.getHeight();
    }
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(this.mTex, (int) this.mPos.getX(), (int) this.mPos.getY(), (int) this.mDim.getWidth(),
                                 (int) this.mDim.getHeight(), null);
    }

    public void setmDim(Dimension dim) {
        this.mDim = dim;
    }

    public Dimension getmDim() {
        return mDim;
    }

    public Point getmPos() {
        return mPos;
    }

    public void changeTex(BufferedImage img) {
        mTex = img;
    }

    public BufferedImage getTex() {
        return mTex;
    }

}
