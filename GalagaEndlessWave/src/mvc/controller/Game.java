package mvc.controller;

import mvc.model.*;
import mvc.view.GameFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for controlling game logic
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Game implements Runnable, KeyListener {

    /** Represents the JFrame for the game */
    private GameFrame mGameFrame;

    /** Represents the Animation delay between frames */
    public final static int DRAW_DELAY = 45;

    /** The thread that handles the render loop for the game */
    private Thread mRenderThread;

    /** Represents the ship in the game */
    private Ship mShip;

    /** Represents the bullet objects in the game */
    private Bullet mBullet;
    private Bullet mBulletTwo;
    private Bullet mBulletThree;
    private Bullet mBulletFour;
    private Bullet mBulletFive;

    /** Represents the max amount of time an enemy can take to take */
    private int nMaxAttackTime;

    /** Represents if the ship is shooting */
    private boolean bShooting;

    /** Represents whether the enemy intro is finished */
    private boolean bIntroDone;

    /** State variables for the game */
    private static boolean bGameOver;
    private static boolean bMainMenu;
    private static boolean bHighScores;

    /** Main menu selecter */
    private Selecter select = new Selecter(355);

    /** High score object */
    private static Highscores highScores;

    /** Score object */
    private static Score score = new Score();

    /** Lives object */
    private static Lives lives = new Lives();

    /** Menu sprites */
    private static ArrayList<Sprite> _menus = new ArrayList<Sprite>();

    /** List of Sprites that need to be rendered  */
    private static ArrayList<Sprite> _sprites = new ArrayList<Sprite>();

    /** All enemies */
    private static ArrayList<Enemy> groupOne = new ArrayList<>();

    /** Enemy bullets */
    private static ArrayList<EnemyBullet> groupOneBullets = new ArrayList<>();

    /** Enemy intro animation points */
    private static ArrayList<ArrayList<Point>> entryPoints = new ArrayList<>();

    /** ArrayLists of Points that each Fighter uses */
    private static ArrayList<Point> RedFighterEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterTwoEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterThreeEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterFourEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterFiveEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterSixEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterSevenEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterEightEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterNineEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighterTenEntry = new ArrayList<>();
    private static ArrayList<Point> RedFighter11Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter12Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter13Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter14Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter15Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter16Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter17Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter18Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter19Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter20Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter21Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter22Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter23Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter24Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter25Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter26Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter27Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter28Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter29Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter30Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter31Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter32Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter33Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter34Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter35Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter36Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter37Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter38Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter39Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter40Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter41Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter42Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter43Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter44Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter45Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter46Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter47Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter48Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter49Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter50Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter51Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter52Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter53Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter54Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter55Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter56Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter57Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter58Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter59Entry = new ArrayList<>();
    private static ArrayList<Point> RedFighter60Entry = new ArrayList<>();

    /** Formation object that enemies take after introduction */
    private static Formation stage;


    /**
     * Constructor
     * @throws IOException
     */
    public Game() throws IOException {
        this.mGameFrame = new GameFrame(this);

        // Sets max time enemies wait to attack
        nMaxAttackTime = 100;

        // Create ArrayLists of Points used for intro animation locations
        setStageOneIntroAnimArrays();

        // Create all Sprite objects
        createSpriteObjects();

        // Used to fire bullets on release
        bShooting = false;

        // Tracks if intro animations are done
        bIntroDone = false;

        // Tracks if current state is main menu
        bMainMenu = true;

        // Tracks if current state is game over
        bGameOver = false;

        // Tracks if current state is high scores
        bHighScores = false;

        // High score object initialization
        highScores = new Highscores();

        // Add all objects to ArrayList of Sprites so they can be drawn
        addSprites();

    }

    /**
     * Sends next wave of enemies
     * @throws IOException
     */
    public void sendNextWave() throws IOException {

        // Fighter objects
        groupOne = new ArrayList<>();

        // fighter bullets
        groupOneBullets = new ArrayList<>();

        for (int i = 0; i < entryPoints.size(); i++) {
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), true, false));
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), false, false));
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), false, true));
        }

        int j = 0;

        // Add RedFighters to groupOne ArrayList
        for (int i = 0; i < entryPoints.size(); i++) {
            j += 3;
            groupOne.add(new RedFighter(new Point(500, 400), entryPoints.get(i), groupOneBullets.get(j - 3),groupOneBullets.get(j - 2), groupOneBullets.get(j - 1), nMaxAttackTime));
        }

        for (int i = 0; i < groupOneBullets.size(); i++) {
            Game._sprites.add(groupOneBullets.get(i));
        }

        for (int i = 0; i < groupOne.size(); i++) {
            Game._sprites.add(groupOne.get(i));
        }


        // Used to fire bullets on release
        bShooting = false;

        bIntroDone = false;

        stage = null;

    }

    /**
     * Sets animations for entering enemies
     */
    private void setStageOneIntroAnimArrays() {
        // First entry
        RedFighterEntry = setRedFighterEntryPoints(590, 0);
        RedFighterTwoEntry = setRedFighterEntryPoints(620, 0);
        RedFighterThreeEntry = setRedFighterEntryPoints(650, 0);
        RedFighterFourEntry = setRedFighterEntryPoints(590, -30);
        RedFighterFiveEntry = setRedFighterEntryPoints(620, -30);
        RedFighterSixEntry = setRedFighterEntryPoints(650, -30);

        // Second entry
        RedFighterSevenEntry = setRedFighterEntryPointsWest(400, 0);
        RedFighterEightEntry = setRedFighterEntryPointsWest(430, 0);
        RedFighterNineEntry = setRedFighterEntryPointsWest(460, 0);
        RedFighterTenEntry = setRedFighterEntryPointsWest(400, -30);
        RedFighter11Entry = setRedFighterEntryPointsWest(430, -30);
        RedFighter12Entry = setRedFighterEntryPointsWest(460, -30);

        // Third entry
        RedFighter13Entry = setRedFighterEntryPointsFarEast(1130, 350);
        RedFighter14Entry = setRedFighterEntryPointsFarEast(1160, 350);
        RedFighter15Entry = setRedFighterEntryPointsFarEast(1190, 350);
        RedFighter16Entry = setRedFighterEntryPointsFarEast(1130, 380);
        RedFighter17Entry = setRedFighterEntryPointsFarEast(1160, 380);
        RedFighter18Entry = setRedFighterEntryPointsFarEast(1190, 380);

        // Fourth entry
        RedFighter19Entry = setRedFighterEntryPointsFarWest(-80, 350);
        RedFighter20Entry = setRedFighterEntryPointsFarWest(-110, 350);
        RedFighter21Entry = setRedFighterEntryPointsFarWest(-140, 350);
        RedFighter22Entry = setRedFighterEntryPointsFarWest(-80, 380);
        RedFighter23Entry = setRedFighterEntryPointsFarWest(-110, 380);
        RedFighter24Entry = setRedFighterEntryPointsFarWest(-140, 380);

        // Fifth entry
        RedFighter25Entry = setRedFighterEntryPointsFarEast(1220, 350);
        RedFighter26Entry = setRedFighterEntryPointsFarEast(1250, 350);
        RedFighter27Entry = setRedFighterEntryPointsFarEast(1280, 350);
        RedFighter28Entry = setRedFighterEntryPointsFarEast(1220, 380);
        RedFighter29Entry = setRedFighterEntryPointsFarEast(1250, 380);
        RedFighter30Entry = setRedFighterEntryPointsFarEast(1280, 380);

        // Sixth entry
        RedFighter31Entry = setRedFighterEntryPointsFarWest(-170, 350);
        RedFighter32Entry = setRedFighterEntryPointsFarWest(-200, 350);
        RedFighter33Entry = setRedFighterEntryPointsFarWest(-230, 350);
        RedFighter34Entry = setRedFighterEntryPointsFarWest(-170, 380);
        RedFighter35Entry = setRedFighterEntryPointsFarWest(-200, 380);
        RedFighter36Entry = setRedFighterEntryPointsFarWest(-230, 380);

        // Seventh entry
        RedFighter37Entry = setRedFighterEntryPointsBottomTopEast(1220, 450);
        RedFighter38Entry = setRedFighterEntryPointsBottomTopEast(1250, 450);
        RedFighter39Entry = setRedFighterEntryPointsBottomTopEast(1280, 450);
        RedFighter40Entry = setRedFighterEntryPointsBottomTopEast(1220, 480);
        RedFighter41Entry = setRedFighterEntryPointsBottomTopEast(1250, 480);
        RedFighter42Entry = setRedFighterEntryPointsBottomTopEast(1280, 480);

        // Eighth entry
        RedFighter43Entry = setRedFighterEntryPointsBottomTopEast(1310, 450);
        RedFighter44Entry = setRedFighterEntryPointsBottomTopEast(1340, 450);
        RedFighter45Entry = setRedFighterEntryPointsBottomTopEast(1370, 450);
        RedFighter46Entry = setRedFighterEntryPointsBottomTopEast(1310, 480);
        RedFighter47Entry = setRedFighterEntryPointsBottomTopEast(1340, 480);
        RedFighter48Entry = setRedFighterEntryPointsBottomTopEast(1370, 480);

        // Ninth entry
        RedFighter49Entry = setRedFighterEntryPointsBottomTopWest(-170, 450);
        RedFighter50Entry = setRedFighterEntryPointsBottomTopWest(-200, 450);
        RedFighter51Entry = setRedFighterEntryPointsBottomTopWest(-230, 450);
        RedFighter52Entry = setRedFighterEntryPointsBottomTopWest(-170, 480);
        RedFighter53Entry = setRedFighterEntryPointsBottomTopWest(-200, 480);
        RedFighter54Entry = setRedFighterEntryPointsBottomTopWest(-230, 480);

        // Tenth entry
        RedFighter55Entry = setRedFighterEntryPointsBottomTopWest(-260, 450);
        RedFighter56Entry = setRedFighterEntryPointsBottomTopWest(-290, 450);
        RedFighter57Entry = setRedFighterEntryPointsBottomTopWest(-320, 450);
        RedFighter58Entry = setRedFighterEntryPointsBottomTopWest(-260, 480);
        RedFighter59Entry = setRedFighterEntryPointsBottomTopWest(-290, 480);
        RedFighter60Entry = setRedFighterEntryPointsBottomTopWest(-320, 480);

        // Adds them to entryPoints ArrayList
        entryPoints.add(RedFighterEntry);
        entryPoints.add(RedFighterTwoEntry);
        entryPoints.add(RedFighterThreeEntry);
        entryPoints.add(RedFighterFourEntry);
        entryPoints.add(RedFighterFiveEntry);
        entryPoints.add(RedFighterSixEntry);
        entryPoints.add(RedFighterSevenEntry);
        entryPoints.add(RedFighterEightEntry);
        entryPoints.add(RedFighterNineEntry);
        entryPoints.add(RedFighterTenEntry);
        entryPoints.add(RedFighter11Entry);
        entryPoints.add(RedFighter12Entry);
        entryPoints.add(RedFighter13Entry);
        entryPoints.add(RedFighter14Entry);
        entryPoints.add(RedFighter15Entry);
        entryPoints.add(RedFighter16Entry);
        entryPoints.add(RedFighter17Entry);
        entryPoints.add(RedFighter18Entry);
        entryPoints.add(RedFighter19Entry);
        entryPoints.add(RedFighter20Entry);
        entryPoints.add(RedFighter21Entry);
        entryPoints.add(RedFighter22Entry);
        entryPoints.add(RedFighter23Entry);
        entryPoints.add(RedFighter24Entry);
        entryPoints.add(RedFighter25Entry);
        entryPoints.add(RedFighter26Entry);
        entryPoints.add(RedFighter27Entry);
        entryPoints.add(RedFighter28Entry);
        entryPoints.add(RedFighter29Entry);
        entryPoints.add(RedFighter30Entry);
        entryPoints.add(RedFighter31Entry);
        entryPoints.add(RedFighter32Entry);
        entryPoints.add(RedFighter33Entry);
        entryPoints.add(RedFighter34Entry);
        entryPoints.add(RedFighter35Entry);
        entryPoints.add(RedFighter36Entry);
        entryPoints.add(RedFighter37Entry);
        entryPoints.add(RedFighter38Entry);
        entryPoints.add(RedFighter39Entry);
        entryPoints.add(RedFighter40Entry);
        entryPoints.add(RedFighter41Entry);
        entryPoints.add(RedFighter42Entry);
        entryPoints.add(RedFighter43Entry);
        entryPoints.add(RedFighter44Entry);
        entryPoints.add(RedFighter45Entry);
        entryPoints.add(RedFighter46Entry);
        entryPoints.add(RedFighter47Entry);
        entryPoints.add(RedFighter48Entry);
        entryPoints.add(RedFighter49Entry);
        entryPoints.add(RedFighter50Entry);
        entryPoints.add(RedFighter51Entry);
        entryPoints.add(RedFighter52Entry);
        entryPoints.add(RedFighter53Entry);
        entryPoints.add(RedFighter54Entry);
        entryPoints.add(RedFighter55Entry);
        entryPoints.add(RedFighter56Entry);
        entryPoints.add(RedFighter57Entry);
        entryPoints.add(RedFighter58Entry);
        entryPoints.add(RedFighter59Entry);
        entryPoints.add(RedFighter60Entry);
    }

    /**
     * Creates all Sprites
     */
    private void createSpriteObjects() {
        this.mShip = new Ship(new Point(527, 620));

        for (int i = 0; i < entryPoints.size(); i++) {
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), true, false));
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), false, false));
            groupOneBullets.add(new EnemyBullet(new Point(-100, -100), false, true));
        }

        int j = 0;

        // Add RedFighters to groupOne ArrayList
        for (int i = 0; i < entryPoints.size(); i++) {
           j += 3;
            groupOne.add(new RedFighter(new Point(500, 400), entryPoints.get(i), groupOneBullets.get(j - 3),groupOneBullets.get(j - 2), groupOneBullets.get(j - 1), nMaxAttackTime));
        }

        this.mBullet = new Bullet(new Point(-10, -10));
        this.mBulletTwo = new Bullet(new Point(-10, -10));
        this.mBulletThree = new Bullet(new Point(-10, -10));
        this.mBulletFour = new Bullet(new Point(-10, -10));
        this.mBulletFive = new Bullet(new Point(-10, -10));
    }

    /**
     * Adds all Sprites to ArrayList to be drawn later
     */
    private void addSprites() {
        Game._sprites.add(mShip);

        for (int i = 0; i < groupOneBullets.size(); i++) {
            Game._sprites.add(groupOneBullets.get(i));
        }

        for (int i = 0; i < groupOne.size(); i++) {
            Game._sprites.add(groupOne.get(i));
        }

        Game._sprites.add(mBullet);
        Game._sprites.add(mBulletTwo);
        Game._sprites.add(mBulletThree);
        Game._sprites.add(mBulletFour);
        Game._sprites.add(mBulletFive);
        Game._sprites.add(score);
        Game._sprites.add(lives);

        Game._menus.add(select);
    }

    /**
     * Sets Entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPoints(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        y += 200;
        Point one = new Point(x, y);

        x += 50;
        y += 50;
        Point two = new Point(x, y);


        x -= 50;
        y += 50;
        Point four = new Point(x, y);

        x -= 50;
        y -= 50;
        Point five = new Point(x, y);

        y -= 50;
        Point six = new Point(x, y);

        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);
        fighterEntry.add(six);

        return fighterEntry;

    }

    /**
     * Sets Entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPointsWest(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        y += 200;
        Point one = new Point(x, y);

        x -= 50;
        y += 50;
        Point two = new Point(x, y);


        x += 50;
        y += 50;
        Point four = new Point(x, y);

        x += 50;
        y -= 50;
        Point five = new Point(x, y);

        y -= 50;
        Point six = new Point(x, y);

        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);
        fighterEntry.add(six);

        return fighterEntry;

    }

    /**
     * Sets entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPointsFarEast(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        x -= 560;
        Point one = new Point(x, y);

        x -= 50;
        y -= 50;
        Point two = new Point(x, y);


        x += 50;
        y += 50;
        Point four = new Point(x, y);

        y -= 120;
        Point five = new Point(x, y);

        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);

        return fighterEntry;

    }

    /**
     * Sets entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPointsFarWest(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        x += 560;
        Point one = new Point(x, y);

        x += 50;
        y -= 50;
        Point two = new Point(x, y);


        x -= 50;
        y += 50;
        Point four = new Point(x, y);

        y -= 120;
        Point five = new Point(x, y);

        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);

        return fighterEntry;

    }

    /**
     * Sets entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPointsBottomTopEast(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        x -= 200;
        y -= 200;
        Point one = new Point(x, y);

        x -= 200;
        y += 200;
        Point two = new Point(x, y);

        y -= 300;
        Point four = new Point(x, y);

        x -= 250;
        Point five = new Point(x, y);

        y -= 40;
        Point six = new Point(x, y);


        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);
        fighterEntry.add(six);


        return fighterEntry;

    }

    /**
     * Sets Entry points
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Point> setRedFighterEntryPointsBottomTopWest(int x, int y) {

        ArrayList<Point> fighterEntry = new ArrayList<>();

        Point zero = new Point(x, y);

        x += 200;
        y -= 200;
        Point one = new Point(x, y);

        x += 200;
        y += 200;
        Point two = new Point(x, y);

        y -= 300;
        Point four = new Point(x, y);

        x += 250;
        Point five = new Point(x, y);

        y -= 40;
        Point six = new Point(x, y);


        fighterEntry.add(zero);
        fighterEntry.add(one);
        fighterEntry.add(two);
        fighterEntry.add(four);
        fighterEntry.add(five);
        fighterEntry.add(six);


        return fighterEntry;

    }

    /**
     * Starts the thread that will handle the render loop for the game
     */
    private void startRenderLoopThread() {
        //Check to make sure the render loop thread has not begun
        if (this.mRenderThread == null) {
            //All threads that are created in java need to be passed a Runnable object.
            //In this case we are making the "Runnable Object" the actual game instance.
            this.mRenderThread = new Thread(this);
            //Start the thread
            this.mRenderThread.start();
        }
    }

    /**
     * This represents the method that will be called for a Runnable object when a thread starts.
     * In this case, this run method represents the render loop.
     */
    public void run() {

        //Make this thread a low priority such that the main thread of the Event Dispatch is always is
        //running first.
        this.mRenderThread.setPriority(Thread.MIN_PRIORITY);

        //Get the current time of rendering this frame
        long elapsedTime = System.currentTimeMillis();

        long currentTime = 0;
        long lastTime = 0;
        long deltaTime = 0;

        // this thread animates the scene
        while (Thread.currentThread() == this.mRenderThread) {

            currentTime = System.currentTimeMillis();

            if(lastTime == 0){
                lastTime = currentTime;
                deltaTime = 0;
            }else {
                deltaTime = currentTime - lastTime;
                lastTime = currentTime;
            }

            // Start game if not on main menu or high scores
            if (bMainMenu == false && bHighScores == false) {

                /************* Update game HERE
                 * - Move the game models
                 * - Check for collisions between the bullet, or fighters and the ship
                 * - Check whether we should move to a new level potentially.
                 */

                // check ship movement
                if (mShip.ismIsMovingRight() == true) {
                    this.mShip.moveRight();
                }
                if (mShip.ismIsMovingLeft() == true) {
                    this.mShip.moveLeft();
                }

                // Millisecond conversion for animations
                final double MILS_TO_SECS = 0.001f;
                double deltaTimeInSecs = deltaTime * MILS_TO_SECS;

                // Let's user shoot up to five bullets at a time
                mBullet.stillShooting();
                mBulletTwo.stillShooting();
                mBulletThree.stillShooting();
                mBulletFour.stillShooting();
                mBulletFive.stillShooting();
                bIntroDone = true;

                // Intro animations
                for (int i = 0; i < groupOne.size(); i++) {
                    RedFighter holder = (RedFighter) groupOne.get(i);
                    if (holder.getbDead() == false) {
                        holder.introAnimation();

                        // Check to see if intros are done
                        if (holder.getIntroDone() == false) {
                            bIntroDone = false;
                        }

                        // Animate flying
                        holder.animate(deltaTimeInSecs);

                        // Increment score when bullet destroys enemy
                        if (holder.collide(mBullet) ||
                        holder.collide(mBulletTwo) ||
                        holder.collide(mBulletThree) ||
                        holder.collide(mBulletFour) ||
                        holder.collide(mBulletFive)) {
                            score.setnScore(score.getnScore() + 10);
                        }
                        // collide with other bullets test
                        holder.explode();

                    }
                }

                // If intro done, create formation
                if (bIntroDone && stage == null) {
                    stage = new Formation(groupOne, score, lives);
                }

                // If formation is created have it move back and forth, attack, and have enemies explode if hit in formation
                if (stage != null) {
                    stage.move(deltaTimeInSecs);
                    stage.attack(deltaTimeInSecs, mShip.getmPos(), mShip);
                    mShip.explode(false, lives);

                    // If all enemies in formation are dead, send the next wave
                    if (stage.checkIfDead() == true) {
                        nMaxAttackTime = nMaxAttackTime - 10;
                        try {
                            sendNextWave();
                        } catch (IOException f) {

                        }
                    }

                    // If lives equals 0, game over display player score
                    if (lives.getnLives() == 0) {
                        bGameOver = true;
                    }

                }
            }

            //Redraw the game frame with to visually show the updated game state.
            this.mGameFrame.draw();

            try {
                /** We want to ensure that the drawing time is at least the DRAW_DELAY we specified. */
                 elapsedTime += DRAW_DELAY;
                 Thread.sleep(Math.max(0, elapsedTime - currentTime));
            } catch (InterruptedException e) {
                //If an interrupt occurs then you can just skip this current frame.
                continue;
            }
        }
    }

    /**
     * Get if in main menu
     * @return
     */
    public static boolean getbMainMenu() {
        return bMainMenu;
    }

    /**
     * Get if game over
     * @return
     */
    public static boolean getbGameOver() {
        return bGameOver;
    }

    /**
     * Get if in high scores
     * @return
     */
    public static boolean getbHighScores() {
        return bHighScores;
    }

    /**
     * Get score
     * @return
     */
    public static Score getScore() {
        return score;
    }

    /**
     * Get Highscores object
     * @return
     */
    public static Highscores getHighScores() {
        return highScores;
    }

    /***
     * Generates all the drawable sprites for the game currently
     * @return an arraylist of all the drawable sprites in the game
     */
    public static ArrayList<Sprite> getDrawableSprites() {
        return new ArrayList<Sprite>(_sprites);
    }

    /**
     * Contains all drawable sprites for the menu
     * @return
     */
    public static ArrayList<Sprite> getDrawableMenuItems() {
        return new ArrayList<Sprite>(_menus);
    }

    /**
     * Key press events
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int nKey = e.getKeyCode();

        switch (nKey) {
            // Move left
            case KeyEvent.VK_LEFT:
                this.mShip.setmIsMovingLeft(true);
                break;
            // Move right
            case  KeyEvent.VK_RIGHT:
                this.mShip.setmIsMovingRight(true);
                break;
            // Shoot bullets
            case  KeyEvent.VK_SPACE:
                if (bShooting == false) {
                    this.mShip.shoot(mBullet, mBulletTwo, mBulletThree, mBulletFour, mBulletFive);
                }
                bShooting = true;
                break;
            // If in main menu, set selecter
            case KeyEvent.VK_UP:
                select.setxMainMenu(355);
                break;

            // If in main menu, set selecter
            case KeyEvent.VK_DOWN:
                select.setxMainMenu(505);
                break;
            default:
                System.out.println("Pressing the key: " + KeyEvent.getKeyText(nKey));
                break;
        }
    }

    /**
     * Key release events
     * @param e
     */
    public void keyReleased(KeyEvent e) {

        int nKey = e.getKeyCode();

        switch (nKey) {
            // When released, stop moving left
            case KeyEvent.VK_LEFT:
                this.mShip.setmIsMovingLeft(false);
                break;
            // Stop moving right
            case KeyEvent.VK_RIGHT:
                this.mShip.setmIsMovingRight(false);
                break;
            // Space has different functions depending on the state of the game
            case KeyEvent.VK_SPACE:
                // Starts game
                if (bMainMenu && select.getxMainMenu() == 355) {
                    bMainMenu = false;
                }
                // Views high scores
                else if (bMainMenu && select.getxMainMenu() == 505) {
                    bMainMenu = false;
                    bHighScores = true;
                }
                // Goes back from high scores page
                else if (bHighScores) {
                    bHighScores = false;
                    bMainMenu = true;
                }

                bShooting = false;
                break;
        }

    }

    /**
     * Key typed events
     * @param e
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * Driver method
     * @param args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {

                try {
                    //Construct the game controller
                    Game game = new Game();
                    //Start the render loop for the game
                    game.startRenderLoopThread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
