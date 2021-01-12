package app.frames;

import app.properties.Config;
import app.properties.Levels;
import app.objects.elements.Map;

import javax.swing.*;
import java.awt.*;

/**
 * This is the frame which handles the map
 */
public class MapFrame extends JFrame {

    int level;
    Map pnl_map;

    /**
     * Basic constructor
     */
    public MapFrame() {
        Config cfg = Config.getInstance();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultLookAndFeelDecorated(true);
        setSize(screenSize.width, screenSize.height);
        setTitle(cfg.getProperty("title"));

        pnl_map = new Map();
        pnl_map.setSize(new Dimension(Integer.parseInt(cfg.getProperty("start_width")), Integer.parseInt(cfg.getProperty("start_height"))));
        pnl_map.setFocusable(true);
        pnl_map.requestFocus();
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open()
    {
        add(pnl_map);
        level = Config.Scenario.elementAt(Levels.getNmb_lvl());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Method which determines what happen when the window is closed
     */
    public void close() {
        dispose();
        System.exit(1);
    }
}
