package mvc.model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * A class for high scores
 * @author Nishant Badal
 * @version 1.0
 * @dateCreated 12/8/16
 */
public class Highscores {

    /**
     * Member vars
     */
    // Array of high scores
    private static Integer[] highScores;
    // Counter to add to array
    private static int nCounter;

    /**
     * Constructor
     * @throws IOException
     */
    public Highscores() throws IOException {
        highScores = new Integer[11];
        nCounter = 0;
        this.read();
    }

    /**
     * Add high scores
     * @param nScore
     */
    public static void add(int nScore) {
        if (nCounter < 11) {
            highScores[nCounter] = nScore;
            nCounter++;
            System.out.println(nCounter);
        }
    }

    /**
     * Sort array greates to least
     */
    public void sort() {
        Arrays.sort(highScores, Collections.reverseOrder());
    }

    /**
     * Draw high scores
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);

        g2d.drawString("Highscores:", 300, 100);
        g2d.drawString("1: " + highScores[0], 300, 200);
        g2d.drawString("2: " + highScores[1], 300, 250);
        g2d.drawString("3: " + highScores[2], 300, 300);
        g2d.drawString("4: " + highScores[3], 300, 350);
        g2d.drawString("5: " + highScores[4], 300, 400);
        g2d.drawString("6: " + highScores[5], 300, 450);
        g2d.drawString("7: " + highScores[6], 300, 500);
        g2d.drawString("8: " + highScores[7], 300, 550);
        g2d.drawString("9: " + highScores[8], 300, 600);
        g2d.drawString("10: " + highScores[9], 300, 650);

    }

    /**
     * Read .txt file of high scores
     * @throws IOException
     */
    public void read() throws IOException {
        URL url = getClass().getResource("highscores.txt");
        System.out.println(url.getPath());
        File inputFile = new File(url.getPath());
        Scanner fileRead = new Scanner(inputFile);

        while (fileRead.hasNextInt()) {
            this.add(fileRead.nextInt());
        }

        fileRead.close();
    }

    /**
     * Write to text file of high scores
     * @throws IOException
     */
    public void write() throws IOException {
        URL url = getClass().getResource("highscores.txt");
        PrintWriter outputFile = new PrintWriter(url.getPath());

        sort();

        for (int i = 0; i < 10; i++) {
            outputFile.println(highScores[i]);
        }

        outputFile.close();
    }
}
