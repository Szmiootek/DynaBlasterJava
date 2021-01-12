package app.frames;

import app.main.Client;
import app.properties.Config;
import app.properties.Difficulty;
import app.intefaces.Frame_user_interface;
import app.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;

/**
 * In this frame the difficulty level is chosen
 */
public class DifficultyFrame extends JFrame implements Frame_user_interface, ComponentListener {

    private final JButton btn_easy;
    private final JButton btn_medium;
    private final JButton btn_hard;
    private final JButton btn_scoreBoard;
    private final JLabel lbl_difficulty;
    private final JPanel pnl;
    private final int width;
    private final int height;

    /**
     * Basic constructor
     */
    public DifficultyFrame() {

        Config cfg = Config.getInstance();
        Difficulty difficulty = Difficulty.getInstance();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        pnl = new JPanel();
        BoxLayout boxLayout = new BoxLayout(pnl, BoxLayout.Y_AXIS);

        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setResizable(true);
        setSize((int)(screenSize.width * 0.15), (int)(screenSize.height * 0.25));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        setTitle(cfg.getProperty("title"));
        width = getWidth();
        height = getHeight();
        pnl.setLayout(boxLayout);
        pnl.setFocusable(true);

        lbl_difficulty = new JLabel(cfg.getProperty("difficulty"));
        lbl_difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn_easy = new JButton(cfg.getProperty("easy"));
        btn_easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_easy.addActionListener(actionEvent -> {
            Main.difficulty_level = 1;
            loadDifficulties(difficulty);
            setVisible(false);
        });
        btn_medium = new JButton(cfg.getProperty("medium"));
        btn_medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_medium.addActionListener(actionEvent -> {
            Main.difficulty_level = 2;
            loadDifficulties(difficulty);
            setVisible(false);
        });
        btn_hard = new JButton(cfg.getProperty("hard"));
        btn_hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_hard.addActionListener(actionEvent -> {
            Main.difficulty_level = 3;
            loadDifficulties(difficulty);
            setVisible(false);
        });
        btn_scoreBoard = new JButton(cfg.getProperty("scores"));
        btn_scoreBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_scoreBoard.addActionListener(actionEvent -> {
            ScoreBoardFrame scoreBoardFrame = new ScoreBoardFrame();
            scoreBoardFrame.open();
        });

        pnl.add(btn_scoreBoard);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.1))));
        pnl.add(lbl_difficulty);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.1))));
        pnl.add(btn_easy);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.05))));
        pnl.add(btn_medium);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.05))));
        pnl.add(btn_hard);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.05))));
    }

    private void loadDifficulties(Difficulty difficulty) {
        if (!Client.isOffline) {
            try {
                difficulty.loadFromServer();
            } catch (IOException e) {
                System.out.println("Could not get difficulties from the server!");
            }
        } else {
            difficulty.load(Main.difficulty_level);
        }
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open() {
        add(pnl);
        addComponentListener(this);
        pnl.requestFocus();
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

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentHidden(ComponentEvent arg0) { }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentMoved(ComponentEvent arg0) { }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentResized(ComponentEvent arg0) {
        AffineTransform at = new AffineTransform((float)getWidth()/width,0.0f,0.0f,
                (float)getHeight()/height,0.0f,0.0f);
        Font lbl_difficulty_newFont = lbl_difficulty.getFont().deriveFont(at);
        lbl_difficulty.setFont(lbl_difficulty_newFont);
        Font btn_easy_newFont = btn_easy.getFont().deriveFont(at);
        btn_easy.setFont(btn_easy_newFont);
        Font btn_medium_newFont = btn_medium.getFont().deriveFont(at);
        btn_medium.setFont(btn_medium_newFont);
        Font btn_hard_newFont = btn_hard.getFont().deriveFont(at);
        btn_hard.setFont(btn_hard_newFont);
        Font btn_scores_newFont = btn_scoreBoard.getFont().deriveFont(at);
        btn_scoreBoard.setFont(btn_scores_newFont);
        btn_easy.setSize(new Dimension((int)(btn_easy.getWidth() * (double)getWidth()/width),
                (int)(btn_easy.getHeight() * (double)getHeight()/height)));
        btn_medium.setSize(new Dimension((int)(btn_medium.getWidth() * (double)getWidth()/width),
                (int)(btn_medium.getHeight() * (double)getHeight()/height)));
        btn_hard.setSize(new Dimension((int)(btn_hard.getWidth() * (double)getWidth()/width),
                (int)(btn_hard.getHeight() * (double)getHeight()/height)));
        btn_scoreBoard.setSize(new Dimension((int)(btn_scoreBoard.getWidth() * (double)getWidth()/width),
                (int)(btn_scoreBoard.getHeight() * (double)getHeight()/height)));
    }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentShown(ComponentEvent arg0) { }
}
