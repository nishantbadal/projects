package mvc.model;

import images.SpriteTexLoader;
import mvc.view.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class for formation and moving many enemies in sync with each other
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Formation {

    private ArrayList<Enemy> stage;
    private boolean bLeft;
    private boolean bRight;
    private boolean bCameraZoomHappening;
    private double dCamera;
    private Score score;
    private Lives lives;
    private static boolean bSubtractLife;

    private Dimension enemyDim = new Dimension(28, 23);

    public Formation(ArrayList<Enemy> stage, Score score, Lives lives) {
        this.stage = stage;
        bLeft = true;
        bRight = false;
        bSubtractLife = false;
        bCameraZoomHappening = false;
        dCamera = 0;
        this.score = score;
        this.lives = lives;

    }

    public boolean checkIfDead() {
        for (int i = 0; i < stage.size(); i++) {
            if (stage.get(i).getbDead() == false) {
                return false;
            }
        }

        return true;
    }

    public void move(double dDeltaTimeInSecs) {

        dCamera += dDeltaTimeInSecs;
        //System.out.println(dCamera);

        for (int j = 0; j < stage.size(); j++) {

            RedFighter holder = (RedFighter) stage.get(j);

            // Doesn't move that sprite if it isn't in formation or is dead
            if (holder.getbDead() == false && holder.getBInFormation()) {

                if (GameFrame.FRAME_DIM.width - holder.mPos.getX() == 100) {
                    bLeft = true;
                    bRight = false;

                } else if (GameFrame.FRAME_DIM.width - holder.mPos.getX() == 1000) {
                    bLeft = false;
                    bRight = true;

                }
            }
        }


        for (int i = 0; i < stage.size(); i++) {

            RedFighter holder = (RedFighter) stage.get(i);

            if (holder.getbDead() == false && holder.getBInFormation()) {


                if (bLeft) {
                    holder.mPos.translate(-2, 0);
                    holder.x = holder.mPos.getX();
                } else if (bRight) {
                    holder.mPos.translate(2, 0);
                    holder.x = holder.mPos.getX();

                }

                holder.setFormationLocation(holder.mPos);



                if (dCamera > 10 && bCameraZoomHappening == false) {
                    bCameraZoomHappening = true;
                    dCamera = 0;
                }

                if (bCameraZoomHappening && dCamera > 1 && dCamera < 1.046) {
                    //System.out.println(holder.getmDim().getWidth());
                    // grow
                    holder.getmDim().setSize(holder.getmDim().getWidth() + 4, holder.getmDim().getHeight() + 4);
                    //System.out.println(holder.getmDim().getWidth());

                } else if (bCameraZoomHappening && dCamera > 1.5 && dCamera < 1.546) {

                    // grow
                    holder.getmDim().setSize(holder.getmDim().getWidth() + 4, holder.getmDim().getHeight() + 4);

                } else if (bCameraZoomHappening && dCamera > 2 && dCamera < 2.046) {

                    // grow
                    holder.getmDim().setSize(holder.getmDim().getWidth() + 4, holder.getmDim().getHeight() + 4);

                } else if (bCameraZoomHappening && dCamera > 3 && dCamera < 3.046) {
                    //System.out.println("shrink 1");
                    // shrink
                    holder.getmDim().setSize(holder.getmDim().getWidth() - 4, holder.getmDim().getHeight() - 4);

                } else if (bCameraZoomHappening && dCamera > 4 && dCamera < 4.046) {
                    //System.out.println("shrink 2");
                    // shrink
                    holder.getmDim().setSize(holder.getmDim().getWidth() - 4, holder.getmDim().getHeight() - 4);

                } else if (bCameraZoomHappening && dCamera > 5 && dCamera < 5.046) {
                    //System.out.println("shrink 3");
                    // shrink
                    holder.getmDim().setSize(holder.getmDim().getWidth() - 4, holder.getmDim().getHeight() - 4);

                } else if (bCameraZoomHappening && dCamera > 5.046) {
                    bCameraZoomHappening = false;
                    dCamera = 0;
                }


            } else if (holder.getbDead() == false && holder.getBInFormation() == false) {
                if (bLeft) {
                   holder.getFormationLocation().translate(-2, 0);
                } else if (bRight) {
                    //System.out.println("before" + holder.getFormationLocation().getX());
                   holder.getFormationLocation().translate(2, 0);
                    //System.out.println("after" + holder.getFormationLocation().getX());
                }
            }

        }
    }

    public void attack(double dDeltaTimeInSecs, Point shipLoc, Ship mShip) {
        for (int i = 0; i < stage.size(); i++) {
            RedFighter holder = (RedFighter) stage.get(i);

            // Checks to see if should goToPoint
            holder.countDownToAttack(dDeltaTimeInSecs);

            // Attacks
            holder.goToPoint(shipLoc, true);

            if (holder.mPos.getY() == 700) {
                holder.mPos.y = -60;
                holder.y = holder.mPos.getY();
                holder.setbGetBackInFormation(true);
                holder.setbInFormation(true);
            }

            // Decrement score by one
            if (mShip.collide(holder.getLeftBullet(), holder)) {
                bSubtractLife = true;
            }

            if (mShip.collide(holder.getDownBullet(), holder)) {
                bSubtractLife = true;
            }

            if (mShip.collide(holder.getRightBullet(), holder)) {
                bSubtractLife = true;
            }
            mShip.explode(bSubtractLife, lives);
            holder.explode();


        }
    }

}
