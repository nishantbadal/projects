package mvc.model;

import images.SpriteTexLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class for RedFighter enemies
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class RedFighter extends Enemy {
    /**
     * Member vars
     */

    // Vars used for intro animation
    private boolean bIntroDone;
    private boolean bAnimInitial;
    private boolean bAnimIntro;
    private int nIntroAnimX;
    private int nIntroAnimY;

    // Vars used for traveling to a point
    private boolean bHolderXisZero;
    private boolean bHolderYisZero;
    private int nAnimPointCounter;
    private double holderX;
    private double holderY;

    // Vars used to countdown to time until attack
    private double dCountDownToAttack;
    private double dTimer;

    // Var used to determine when to fire bullet (halfway to ship point)
    private double dBulletHalfwayToShip;

    // Var to determine if ship is in formation or attacking
    private boolean bInFormation;

    // Var to determine if enemy is done attacking
    private boolean bAttackDone;

    // Var to determine if getting back in formation
    private boolean bGetBackInFormation;

    // Random object used to create time until enemy attacks
    private Random rand;

    // Enemy bullets
    private EnemyBullet bulletOne;
    private EnemyBullet bulletTwo;
    private EnemyBullet bulletThree;


    // Point to return to in formation
    private Point formationLocation;

    // Arraylist of location in formation
    private ArrayList<Point> locations;

    // Vars used for state animations (each position has two flying states)
    private int nAnimateCounter;
    private double dAnimateTime;

    // Images
    private BufferedImage redFighter;
    private BufferedImage redFighterTwo;
    private BufferedImage redFighterDown;
    private BufferedImage redFighterDownTwo;
    private BufferedImage redFighterLeft;
    private BufferedImage redFighterLeftTwo;
    private BufferedImage redFighterRight;
    private BufferedImage redFighterRightTwo;
    private BufferedImage redFighterLeftTilt;
    private BufferedImage redFighterLeftTiltTwo;
    private BufferedImage redFighterLeftFullway;
    private BufferedImage redFighterLeftFullwayTwo;
    private BufferedImage redFighterRightTilt;
    private BufferedImage redFighterRightTiltTwo;
    private BufferedImage redFighterRightFullway;
    private BufferedImage redFighterRightFullwayTwo;
    private BufferedImage redFighterLeftTiltDown;
    private BufferedImage redFighterLeftTiltDownTwo;
    private BufferedImage redFighterLeftFullwayDown;
    private BufferedImage redFighterLeftFullwayDownTwo;
    private BufferedImage redFighterRightTiltDown;
    private BufferedImage redFighterRightTiltDownTwo;
    private BufferedImage redFighterRightFullwayDown;
    private BufferedImage redFighterRightFullwayDownTwo;

    /**
     * Constructor
     * @param initPos
     * @param locations
     * @param left
     * @param down
     * @param right
     * @param nMax
     */
    public RedFighter(Point initPos, ArrayList<Point> locations, EnemyBullet left, EnemyBullet down, EnemyBullet right, int nMax) {
        super(initPos);
        // starts animation
        bIntroDone = false;
        bAnimInitial = true;
        bAnimIntro = false;

        // not at destination point
        bHolderXisZero = false;
        bHolderYisZero = false;

        // starts in formation, not attacking
        bInFormation = true;
        bAttackDone = true;
        bGetBackInFormation = false;

        // Anim points
        nAnimPointCounter = 0;
        nIntroAnimX = 0;
        nIntroAnimY = 0;
        holderX = 0;
        holderY = 0;

        // Countdown to attack vars
        dCountDownToAttack = 0;
        rand = new Random();

        // Randomly sets time enemy will attack. nMax is decremented by 10 each wave so each wave will attack faster
        dTimer = rand.nextInt((nMax - 5) + 1) + 5;
        dBulletHalfwayToShip = 0;

        // sets enemy bulelts
        bulletOne = left;
        bulletTwo = down;
        bulletThree = right;

        // sets formation location
        formationLocation = new Point((int) initPos.getX(), (int) initPos.getY());
        this.locations = locations;

        // animation counter
        nAnimateCounter = 0;
        dAnimateTime = 0;

        // Loads sprites so game doesn't slow down
        initializeImages();
    }

    /**
     * Initialize images
     */
    private void initializeImages() {
        redFighter = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER);
        redFighterTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTERTWO);
        redFighterDown = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_DOWN);
        redFighterDownTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_DOWNTWO);
        redFighterLeft = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT);
        redFighterLeftTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFTTWO);
        redFighterRight = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT);
        redFighterRightTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHTTWO);
        redFighterLeftTilt = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT);
        redFighterLeftTiltTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILTTWO);
        redFighterLeftFullway = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY);
        redFighterLeftFullwayTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAYTWO);;
        redFighterRightTilt = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT);
        redFighterRightTiltTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILTTWO);
        redFighterRightFullway = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY);
        redFighterRightFullwayTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAYTWO);
        redFighterLeftTiltDown = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT_DOWN);
        redFighterLeftTiltDownTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT_DOWNTWO);
        redFighterLeftFullwayDown = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY_DOWN);
        redFighterLeftFullwayDownTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY_DOWNTWO);
        redFighterRightTiltDown = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT_DOWN);
        redFighterRightTiltDownTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT_DOWNTWO);
        redFighterRightFullwayDown = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY_DOWN);
        redFighterRightFullwayDownTwo = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY_DOWNTWO);
    }

    /**
     * Return if intro is done
     * @return
     */
    public boolean getIntroDone() {
        return bIntroDone;
    }

    /**
     * If not dead, shoot bullets
     */
    public void shoot() {
        if (this.getbDead() == false) {
            bulletOne.move();
            bulletTwo.move();
            bulletThree.move();
        }
    }

    /**
     * Intro animation
     */
    public void introAnimation() {

        // Only execute code if it hasn't been done
        if (bIntroDone == false) {

            // Only execute if there are points left
            if (nAnimPointCounter < locations.size() - 1) {

                Point holder;
                Point holderTwo;
                holder = locations.get(nAnimPointCounter);
                holderTwo = locations.get(nAnimPointCounter + 1);

                // Initial point based on the entry point the user gives
                if (bAnimInitial == true) {
                    this.mPos.setLocation(holder.getX(), holder.getY());
                    bAnimInitial = false;
                }

                // Find distance to point if intro animaiton is not done
                if (bAnimIntro == false) {
                    System.out.println(nAnimPointCounter);
                    holderX = holder.getX() - holderTwo.getX();
                    holderY = holder.getY() - holderTwo.getY();
                    bAnimIntro = true;

                    // Set Sprite to going up
                    if (holderY > 0) {
                        // Going sharp upward left
                        if (holderX > 50) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY));
                        }
                        // Tilting upward left
                        else if (holderX <= 50 && holderX > 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT));
                        }
                        // Going sharp upward right
                        else if (holderX < -50) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY));
                        }
                        // Tilting upward right
                        else if (holderX >= -50 && holderX < 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT));
                        }
                        // Going straight up
                        else if (holderX == 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER));
                        }
                    }
                    // Set Sprite to going down
                    else if (holderY < 0) {
                        // Going sharp downward left
                        if (holderX > 50) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY_DOWN));
                        }
                        // Tilting downward left
                        else if (holderX <= 50 && holderX > 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT_DOWN));
                        }
                        // Going sharp downward right
                        else if (holderX < -50) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY_DOWN));
                        }
                        // Tilting downward right
                        else if (holderX >= -50 && holderX < 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT_DOWN));
                        }
                        // Going straight down
                        else if (holderX == 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_DOWN));
                        }

                    }
                    // Else the sprite is moving left or right, not up and down
                    else {
                        if (holderX > 0) {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT));
                        } else {
                            this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT));
                        }

                    }


                }
                // Set moving var
                if (holderX != 0) {
                    //System.out.println("x");
                    if (holderX > 0) {
                        holderX -= 10;
                        nIntroAnimX = -10;
                    } else if (holderX < 0) {
                        holderX += 10;
                        nIntroAnimX = 10;
                    }

                    // Set rectangle for collisions
                    this.x = this.mPos.getX();
                    this.y = this.mPos.getY();
                } else {
                    this.x = this.mPos.getX();
                    this.y = this.mPos.getY();
                    nIntroAnimX = 0;
                    bHolderXisZero = true;
                }
                // Set moving var
                if (holderY != 0) {
                    //System.out.println("y");
                    if (holderY > 0) {
                        holderY -= 10;
                        nIntroAnimY = -10;
                        //System.out.println(this.mPos.getY());
                    } else if (holderY < 0) {
                        holderY += 10;
                        nIntroAnimY = 10;
                        //System.out.println(holderY);
                    }

                    // Set rectangle to the same position for collisions
                    this.x = this.mPos.getX();
                    this.y = this.mPos.getY();
                } else {
                    nIntroAnimY = 0;
                    bHolderYisZero = true;
                    this.x = this.mPos.getX();
                    this.y = this.mPos.getY();
                }

                // move to point
                if (bAnimIntro) {
                    this.mPos.translate(nIntroAnimX, nIntroAnimY);
                }

                // only increment to next point if current point is reached
                if (bHolderXisZero && bHolderYisZero) {
                    nAnimPointCounter++;
                    bAnimIntro = false;
                    bHolderXisZero = false;
                    bHolderYisZero = false;
                }
            } else {
                bIntroDone = true;
                bInFormation = true;
            }
        }

    }

    /**
     * Animate enemy
     * @param dDeltaTimeInSecs
     */
    public void animate(double dDeltaTimeInSecs) {
        dAnimateTime += dDeltaTimeInSecs;

        // Animate every 0.3 seconds
        if (dAnimateTime > 0.3) {

            // Change image to one that corresponds to the current image
            if (this.compareImages(this.getTex(), redFighter) || this.compareImages(this.getTex(), redFighterTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighter);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterDown)
                    || this.compareImages(this.getTex(), redFighterDownTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterDown);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterDownTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterLeft)
                    || this.compareImages(this.getTex(), redFighterLeftTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeft);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterRight)
                    || this.compareImages(this.getTex(), redFighterRightTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterRight);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterLeftTilt)
                    || this.compareImages(this.getTex(), redFighterLeftTiltTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftTilt);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftTiltTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterLeftFullway)
                    || this.compareImages(this.getTex(), redFighterLeftFullwayTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftFullway);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftFullwayTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterRightTilt)
                    || this.compareImages(this.getTex(), redFighterRightTiltTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightTilt);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightTiltTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterRightFullway)
                    || this.compareImages(this.getTex(), redFighterRightFullwayTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightFullway);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightFullwayTwo);
                    nAnimateCounter--;
                }
            } else if (this.compareImages(this.getTex(), redFighterLeftTiltDown)
                    || this.compareImages(this.getTex(), redFighterLeftTiltDownTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftTiltDown);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftTiltDownTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterLeftFullwayDown)
                    || this.compareImages(this.getTex(), redFighterLeftFullwayDownTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftFullwayDown);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterLeftFullwayDownTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterRightTiltDown)
                    || this.compareImages(this.getTex(), redFighterRightTiltDownTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightTiltDown);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightTiltDownTwo);
                    nAnimateCounter--;
                }

            } else if (this.compareImages(this.getTex(), redFighterRightFullwayDown)
                    || this.compareImages(this.getTex(), redFighterRightFullwayDownTwo)) {

                if (nAnimateCounter == 0 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightFullwayDown);
                    nAnimateCounter++;
                } else if (nAnimateCounter == 1 && this.getbExplode() == false) {
                    this.changeTex(redFighterRightFullwayDownTwo);
                    nAnimateCounter--;
                }
            }


            dAnimateTime = 0;
        }
    }

    /**
     * Go to a point
     * @param fighterLoc
     * @param bAttack
     */
    public void goToPoint(Point fighterLoc, boolean bAttack) {

        // If not attacking
        if (bAttackDone == false) {
            Point holder;
            Point holderTwo;
            holder = this.mPos;
            holderTwo = fighterLoc;

            // If in formation and the animation to the point is not completed
            if (bAnimIntro == false && bInFormation) {
                // set in formation to false until it returns to formation
                bInFormation = false;
                holderX = holder.getX() - holderTwo.getX();
                holderY = holder.getY() - holderTwo.getY();


                // if this is an attack keep going past the ship off screen
                if (bAttack) {
                    holderY -= 80;
                    dBulletHalfwayToShip = holderY / 2;
                }

                bAnimIntro = true;
                this.setmDim(new Dimension (28, 23));

                // Set Sprite to going up
                if (holderY > 0) {
                    // Going sharp upward left
                    if (holderX > 50) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY));
                    }
                    // Tilting upward left
                    else if (holderX <= 50 && holderX > 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT));
                    }
                    // Going sharp upward right
                    else if (holderX < -50) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY));
                    }
                    // Tilting upward right
                    else if (holderX >= -50 && holderX < 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT));
                    }
                    // Going straight up
                    else if (holderX == 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER));
                    }
                }
                // Set Sprite to going down
                else if (holderY < 0) {
                    // Going sharp downward left
                    if (holderX > 50) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_FULLWAY_DOWN));
                    }
                    // Tilting downward left
                    else if (holderX <= 50 && holderX > 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT_TILT_DOWN));
                    }
                    // Going sharp downward right
                    else if (holderX < -50) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_FULLWAY_DOWN));
                    }
                    // Tilting downward right
                    else if (holderX >= -50 && holderX < 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT_TILT_DOWN));
                    }
                    // Going straight down
                    else if (holderX == 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_DOWN));
                    }

                }
                // Else the sprite is moving left or right, not up and down
                else {
                    if (holderX > 0) {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_LEFT));
                    } else {
                        this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_RIGHT));
                    }

                }


            }


            if (holderX > 5 || holderX < -5) {
                //System.out.println("x");
                if (holderX > 0) {
                    holderX -= 10;
                    nIntroAnimX = -10;
                } else if (holderX < 0) {
                    holderX += 10;
                    nIntroAnimX = 10;
                }

                // Set rectangle for collisions
                this.x = this.mPos.getX();
                this.y = this.mPos.getY();
            } else {
                this.x = this.mPos.getX();
                this.y = this.mPos.getY();
                nIntroAnimX = 0;
                bHolderXisZero = true;
            }

            if (holderY > 5 || holderY < -5) {
                if (bHolderXisZero) {
                    this.changeTex(SpriteTexLoader.load(SpriteTexLoader.SpriteTex.RED_FIGHTER_DOWN));
                }
                //System.out.println("y");
                if (holderY > 0) {
                    holderY -= 10;
                    nIntroAnimY = -10;



                    //System.out.println(this.mPos.getY());
                } else if (holderY < 0) {
                    holderY += 10;
                    nIntroAnimY = 10;


                    if (bAttack && holderY > dBulletHalfwayToShip) {
                        if (bulletOne.getbShooting() == false && bulletTwo.getbShooting() == false && bulletThree.getbShooting() == false && this.getbDead() == false) {
                            bulletOne.setBulletLocation(this.mPos.getX(), this.mPos.getY());
                            bulletTwo.setBulletLocation(this.mPos.getX(), this.mPos.getY());
                            bulletThree.setBulletLocation(this.mPos.getX(), this.mPos.getY());
                            bulletOne.setbShooting(true);
                            bulletTwo.setbShooting(true);
                            bulletThree.setbShooting(true);
                        } else {
                            bulletOne.stillShooting();
                            bulletTwo.stillShooting();
                            bulletThree.stillShooting();
                        }

                    }
                    //System.out.println(holderY);
                }

                // Set rectangle to the same position for collisions
                this.x = this.mPos.getX();
                this.y = this.mPos.getY();
            } else {
                nIntroAnimY = 0;
                bHolderYisZero = true;
                this.x = this.mPos.getX();
                this.y = this.mPos.getY();
            }

            // movement
            if (bAnimIntro) {
                this.mPos.translate(nIntroAnimX, nIntroAnimY);
            }



            // only increment to next point if current point is reached
            if (bHolderXisZero && bHolderYisZero) {
                bGetBackInFormation = false;
                bAttackDone = true;
                bAnimIntro = false;
                bHolderXisZero = false;
                bHolderYisZero = false;
            }


        }

    }

    /**
     * Countdown until enemy attacks
     * @param deltaTimeInSec
     */
    public void countDownToAttack(double deltaTimeInSec) {

        if (bAttackDone == true) {
            dCountDownToAttack += deltaTimeInSec;
        }

        if (dCountDownToAttack > dTimer) {
            // initiate goToPoint
            bAttackDone = false;

            // set count down to goToPoint to 0
            dCountDownToAttack = 0;



        }

    }

    /**
     * get if in formation
     * @return
     */
    public boolean getBInFormation() {
        return bInFormation;
    }

    /**
     * Set if in formation
     * @param bInformation
     */
    public void setbInFormation(boolean bInformation) {
        this.bInFormation = bInformation;
    }

    /**
     * Get if going back to formation
     * @return
     */
    public boolean getbGetBackInFormation() {
        return bGetBackInFormation;
    }

    /**
     * Set if going back to formatino
     * @param bGetBackInFormation
     */
    public void setbGetBackInFormation(boolean bGetBackInFormation) {
        this.bGetBackInFormation = bGetBackInFormation;
    }

    /**
     * Get formation location
     * @return
     */
    public Point getFormationLocation() {
        return formationLocation;
    }

    /**
     * Set formation location
     * @param formationLocation
     */
    public void setFormationLocation(Point formationLocation) {
        this.formationLocation.x = formationLocation.x;
        this.formationLocation.y = formationLocation.y;
    }

    /**
     * Set if reached point
     * @param bAnimIntro
     */
    public void setbAnimIntro(boolean bAnimIntro) {
        this.bAnimIntro = bAnimIntro;
    }

    /**
     * Get bullet that fires left
     * @return
     */
    public EnemyBullet getLeftBullet() {
        return bulletOne;
    }

    /**
     * Get down bullet
     * @return
     */
    public EnemyBullet getDownBullet() {
        return bulletTwo;
    }

    /**
     * Get right bullet
     * @return
     */
    public EnemyBullet getRightBullet() {
        return bulletThree;
    }

    /**
     * I did not write this method. I slightly modified it. Credit goes to Jerome Brunel.
     * https://mylifeonascrumboard.com is his website.
     * The method compares if 2 images are the same. I use this to switch images for the flying animation.
     * @param imgA
     * @param imgB
     * @return
     */
    private boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
            int width = imgA.getWidth();
            int height = imgA.getHeight();

            // Loop over every pixel.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Compare the pixels for equality.
                    if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
