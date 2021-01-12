package app.frames;

import app.main.Client;
import app.properties.Config;
import app.intefaces.Frame_user_interface;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Frame which shows up when the game is ended
 */
public class EndGameFrame extends JFrame implements Frame_user_interface {

    private final JPanel pnl;
    int score;
    String nick;

    /**
     * Parametrized constructor
     * @param score scored result
     * @param nick player's nick
     */
    public EndGameFrame(int score, String nick) {
        Config cfg = Config.getInstance();
        this.score = score;
        this.nick = nick;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        pnl = new JPanel();
        BoxLayout boxLayout = new BoxLayout(pnl, BoxLayout.Y_AXIS);

        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setSize((int)(screenSize.width * 0.15), (int)(screenSize.height * 0.12));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        setResizable(false);
        setTitle(cfg.getProperty("title"));
        pnl.setLayout(boxLayout);
        pnl.setFocusable(true);

        JButton btn_scoreBoard = new JButton(cfg.getProperty("scores"));
        btn_scoreBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_scoreBoard.addActionListener(actionEvent -> {
            ScoreBoardFrame scoreBoardFrame = new ScoreBoardFrame();
            scoreBoardFrame.open();
        });

        JButton btn_exit = new JButton(cfg.getProperty("exit"));
        btn_exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_exit.addActionListener(actionEvent -> close());

        JLabel lbl_score = new JLabel("Player: " + nick + ", score: " + score);
        lbl_score.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnl.add(btn_scoreBoard);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.1))));
        pnl.add(lbl_score);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.1))));
        pnl.add(btn_exit);
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open() {
        add(pnl);
        pnl.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Saves nick and score to the file
     */
    public void saveToFile() {
        try {
            File file = new File("DynaBlaster_App/src/config/ScoreBoard");
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(score + "-" + nick + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot open the ScoreBoard file!");
        }
    }

    /**
     * Saves nick and score on the server
     */
    public void saveToServer() {
        String request = "";
        request += nick + "-" + score;
        try {
            Client.saveScore(request);
        } catch (IOException e) {
            System.out.println("Cannot save score on server!");
        }
    }

    /**
     * Method which determines what happen when the window is closed
     */
    public void close() {
        dispose();
        System.exit(1);
    }
}
