package images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class easily loads the textures for the sprites in the game
 *
 * @version  1.0
 * @author Lamont Samuels
 * @since  11-13-16
 */

public class SpriteTexLoader {

    private static SpriteTexLoader sInstance = new SpriteTexLoader();

    public enum SpriteTex {
        SHIP,
        BLUE_FIGHTER,
        RED_FIGHTER,
        RED_FIGHTERTWO,
        RED_FIGHTER_DOWN,
        RED_FIGHTER_DOWNTWO,
        RED_FIGHTER_LEFT,
        RED_FIGHTER_LEFTTWO,
        RED_FIGHTER_RIGHT,
        RED_FIGHTER_RIGHTTWO,
        RED_FIGHTER_LEFT_TILT,
        RED_FIGHTER_LEFT_TILTTWO,
        RED_FIGHTER_LEFT_FULLWAY,
        RED_FIGHTER_LEFT_FULLWAYTWO,
        RED_FIGHTER_RIGHT_TILT,
        RED_FIGHTER_RIGHT_TILTTWO,
        RED_FIGHTER_RIGHT_FULLWAY,
        RED_FIGHTER_RIGHT_FULLWAYTWO,
        RED_FIGHTER_LEFT_TILT_DOWN,
        RED_FIGHTER_LEFT_TILT_DOWNTWO,
        RED_FIGHTER_LEFT_FULLWAY_DOWN,
        RED_FIGHTER_LEFT_FULLWAY_DOWNTWO,
        RED_FIGHTER_RIGHT_TILT_DOWN,
        RED_FIGHTER_RIGHT_TILT_DOWNTWO,
        RED_FIGHTER_RIGHT_FULLWAY_DOWN,
        RED_FIGHTER_RIGHT_FULLWAY_DOWNTWO,
        BACKGROUND,
        SHIP_BULLET,
        ENEMY_BULLET_DOWN,
        ENEMY_BULLET_LEFT,
        ENEMY_BULLET_RIGHT,
        SHIP_EXPLOSION,
        SHIP_EXPLOSIONTWO,
        SHIP_EXPLOSIONTHREE,
        SHIP_EXPLOSIONFOUR,
        SHIP_EXPLOSIONFIVE,
        EXPLOSION,
        EXPLOSIONTWO,
        EXPLOSIONTHREE,
        EXPLOSIONFOUR,
        EXPLOSIONFIVE
    }

    private SpriteTexLoader() {}
    /**
     * Retrieves the file path for the a Sprite texture in the images file
     * @param sprite - the sprite file path to retrieve
     */
    private static String getSpriteFile(SpriteTex sprite) {

        String file = "";
        switch (sprite) {
            case SHIP:
                file = "ship.png";
                break;
            case BLUE_FIGHTER:
                file = "blue_fighter.png";
                break;
            case RED_FIGHTER:
                file = "red_fighter.png";
                break;
            case RED_FIGHTERTWO:
                file = "red_fighter2.png";
                break;
            case RED_FIGHTER_DOWN:
                file = "red_fighter_down.png";
                break;
            case RED_FIGHTER_DOWNTWO:
                file = "red_fighter2_down.png";
                break;
            case RED_FIGHTER_LEFT:
                file = "red_fighter_left_facing.png";
                break;
            case RED_FIGHTER_LEFTTWO:
                file = "red_fighter_left_facing2.png";
                break;
            case RED_FIGHTER_RIGHT:
                file = "red_fighter_right_facing.png";
                break;
            case RED_FIGHTER_RIGHTTWO:
                file = "red_fighter_right_facing2.png";
                break;
            case RED_FIGHTER_LEFT_TILT:
                file = "red_fighter_left_halfway_tilt.png";
                break;
            case RED_FIGHTER_LEFT_TILTTWO:
                file = "red_fighter_left_halfway_tilt2.png";
                break;
            case RED_FIGHTER_RIGHT_TILT:
                file = "red_fighter_right_halfway_tilt.png";
                break;
            case RED_FIGHTER_RIGHT_TILTTWO:
                file = "red_fighter_right_halfway_tilt2.png";
                break;
            case RED_FIGHTER_LEFT_FULLWAY:
                file = "red_fighter_left_full_tilt.png";
                break;
            case RED_FIGHTER_LEFT_FULLWAYTWO:
                file = "red_fighter_left_full_tilt2.png";
                break;
            case RED_FIGHTER_RIGHT_FULLWAY:
                file = "red_fighter_right_full_tilt.png";
                break;
            case RED_FIGHTER_RIGHT_FULLWAYTWO:
                file = "red_fighter_right_full_tilt2.png";
                break;
            case RED_FIGHTER_LEFT_TILT_DOWN:
                file = "red_fighter_left_halfway_tilt_down.png";
                break;
            case RED_FIGHTER_LEFT_TILT_DOWNTWO:
                file = "red_fighter_left_halfway_tilt2_down.png";
                break;
            case RED_FIGHTER_RIGHT_TILT_DOWN:
                file = "red_fighter_right_halfway_tilt_down.png";
                break;
            case RED_FIGHTER_RIGHT_TILT_DOWNTWO:
                file = "red_fighter_right_halfway_tilt2_down.png";
                break;
            case RED_FIGHTER_LEFT_FULLWAY_DOWN:
                file = "red_fighter_left_full_tilt_down.png";
                break;
            case RED_FIGHTER_LEFT_FULLWAY_DOWNTWO:
                file = "red_fighter_left_full_tilt2_down.png";
                break;
            case RED_FIGHTER_RIGHT_FULLWAY_DOWN:
                file = "red_fighter_right_full_tilt_down.png";
                break;
            case RED_FIGHTER_RIGHT_FULLWAY_DOWNTWO:
            file = "red_fighter_right_full_tilt2_down.png";
            break;
            case BACKGROUND:
                file = "galagabg.png";
                break;
            case SHIP_BULLET:
                file = "ship_bullet.png";
                break;
            case ENEMY_BULLET_DOWN:
                file = "enemy_bullet_down.png";
                break;
            case ENEMY_BULLET_LEFT:
                file = "enemy_bullet_left.png";
                break;
            case ENEMY_BULLET_RIGHT:
                file = "enemy_bullet_right.png";
                break;
            case EXPLOSION:
                file = "explosion1.png";
                break;
            case EXPLOSIONTWO:
                file = "explosion2.png";
                break;
            case EXPLOSIONTHREE:
                file = "explosion3.png";
                break;
            case EXPLOSIONFOUR:
                file = "explosion4.png";
                break;
            case EXPLOSIONFIVE:
                file = "explosion5.png";
                break;
            case SHIP_EXPLOSION:
                file = "ship_explosion1.png";
                break;
            case SHIP_EXPLOSIONTWO:
                file = "ship_explosion2.png";
                break;
            case SHIP_EXPLOSIONTHREE:
                file = "ship_explosion3.png";
                break;
            case SHIP_EXPLOSIONFOUR:
                file = "ship_explosion4.png";
                break;
        }
        return file;
    }

    /**
     * Returns a buffered image from the images directory of a particular sprite
     * @param sprite - the sprite texture to load
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static BufferedImage load(SpriteTex sprite)  throws IllegalArgumentException {

        if (sprite == null){
            throw new IllegalArgumentException("Sprite texture parameter must not be null");
        }
        BufferedImage img = null;
        try {
            String file = getSpriteFile(sprite);
            img = ImageIO.read(sInstance.getClass().getResource(file));
        }catch (IOException e){
            System.out.print("Could not open texture :" + sprite.toString());
            System.exit(1);
        }
        return img;
    }

}
