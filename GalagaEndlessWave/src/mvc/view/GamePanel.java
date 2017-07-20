package mvc.view;


import images.SpriteTexLoader;
import mvc.controller.Game;
import mvc.model.Highscores;
import mvc.model.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for managing game states and what is being drawn
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class GamePanel extends JPanel {

    // Background image
    private BufferedImage backgroundImage;

    private int fScroll;

    private int nY;
    private int nY2;

    public GamePanel() {
        this.setPreferredSize(GameFrame.FRAME_DIM);

        // Set up background image
        backgroundImage = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BACKGROUND);
        fScroll = 1;
        nY = 0;
        nY2 = 0;
    }
    @Override
    public void paintComponent(Graphics g) {
        // Call the super paintComponent of the panel
        super.paintComponent(g);

        //Draw two backgrounds
        g.drawImage(backgroundImage, 0, nY, GameFrame.FRAME_DIM.width, GameFrame.FRAME_DIM.height, null);
        g.drawImage(backgroundImage, 0, nY2, GameFrame.FRAME_DIM.width, GameFrame.FRAME_DIM.height, null);

        //g.fillRect(0, 0, GameFrame.FRAME_DIM.width,  GameFrame.FRAME_DIM.height);

        // Set y position of backgrounds one frame length apart
        nY++;
        nY2 = nY - GameFrame.FRAME_DIM.height;

        // If background hits the bottom, set it back to the top
        if (nY == GameFrame.FRAME_DIM.height) {
            nY = 0;
        }

        // If game in main menu
        if (Game.getbMainMenu()) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.setColor(Color.WHITE);
            g.drawString("GALAGA", 450, 200);
            g.drawString("Start Game", 450, 350);
            g.drawString("Highscores", 450, 500);

            ArrayList<Sprite> menuItems = Game.getDrawableMenuItems();

            for (Sprite sprite : menuItems) {
                sprite.draw(g);
            }
        }
        // If game state is game over
        else if (Game.getbGameOver()) {
                Game.getScore().showScore();

            // add score to high scores
                Highscores.add(Game.getScore().getnScore());

            // sort high scores
                Game.getHighScores().sort();

            // try to write highscores
            try {
                Game.getHighScores().write();
            } catch (IOException e) {

            }
            // show player their score
                Game.getScore().draw(g);
        }
        // If game state is high scores
        else if (Game.getbHighScores()) {
            Game.getHighScores().draw(g);
        }
            else if (Game.getbHighScores() == false && Game.getbMainMenu() == false) {
            //Start redrawing all the objects of the game.
            ArrayList<Sprite> sprites = Game.getDrawableSprites();

            for (Sprite sprite : sprites) {
                sprite.draw(g);
            }
        }
    }


}
