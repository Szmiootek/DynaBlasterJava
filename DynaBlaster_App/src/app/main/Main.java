package app.main;

import app.frames.ConnectionFrame;
import app.frames.DifficultyFrame;
import app.frames.LoggingFrame;
import app.frames.MapFrame;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Main class
 */

public class Main {

    /**
     * It is an string containing login written by user in the LoggingFrame
     */
    static public String nick;

    /**
     * It is a difficulty level
     */
    static public int difficulty_level;

    static int Switch = 0;
    static ConnectionFrame connectionFrame;
    static LoggingFrame loggingFrame;
    static DifficultyFrame difficultyFrame;
    static MapFrame mapFrame;

    /**
     * Main method
     * @param args System arguments
     */
    public static void main(String[] args) {
        connectionFrame = new ConnectionFrame();

        ComponentListener componentListener = new ComponentListener() {
            /**
             * Overrides componentListener method
             * @param componentEvent ComponentEvent
             */
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                
            }

            /**
             * Overrides componentListener method
             * @param componentEvent ComponentEvent
             */
            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            /**
             * Overrides componentListener method
             * @param componentEvent ComponentEvent
             */
            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            /**
             * Overrides componentListener method
             * @param componentEvent ComponentEvent
             */
            @Override
            public void componentHidden(ComponentEvent componentEvent) {
                switch (Switch) {
                    case 0 -> {
                        loggingFrame = new LoggingFrame();
                        loggingFrame.addComponentListener(this);
                        loggingFrame.open();
                        Switch++;
                    }
                    case 1 -> {
                        difficultyFrame = new DifficultyFrame();
                        difficultyFrame.addComponentListener(this);
                        difficultyFrame.open();
                        Switch++;
                    }
                    case 2 -> {
                        mapFrame = new MapFrame();
                        mapFrame.addComponentListener(this);
                        mapFrame.open();
                        Switch++;
                    }
                    default -> { }
                }
            }
        };

        connectionFrame.addComponentListener(componentListener);
        connectionFrame.open();
    }
}
