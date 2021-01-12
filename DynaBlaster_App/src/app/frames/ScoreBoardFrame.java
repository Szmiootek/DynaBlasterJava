package app.frames;

import app.main.Client;
import app.properties.Config;
import app.intefaces.Frame_user_interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

/**
 * In this frame top 10 scores are shown
 */
public class ScoreBoardFrame extends JFrame implements Frame_user_interface {
    private final JPanel pnl;

    /**
     * Basic constructor
     */
    public ScoreBoardFrame() {

        Config cfg = Config.getInstance();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        pnl = new JPanel();
        BoxLayout boxLayout = new BoxLayout(pnl, BoxLayout.Y_AXIS);

        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize((int)(screenSize.width * 0.15), (int)(screenSize.height * 0.45));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        setTitle(cfg.getProperty("title"));
        setVisible(false);
        pnl.setLayout(boxLayout);

        JLabel lbl_scoreboard = new JLabel(cfg.getProperty("scoreboard"));
        lbl_scoreboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btn_ok = new JButton(cfg.getProperty("ok"));
        btn_ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_ok.addActionListener(actionEvent -> dispose());
        btn_ok.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });

        Map<Integer, String> scores = new TreeMap<>(Collections.reverseOrder());
        if (Client.isOffline) {
            try {
                FileReader fileReader = new FileReader("DynaBlaster_App/src/config/ScoreBoard");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String str;
                int index;
                while((str = bufferedReader.readLine()) != null){
                    index = str.indexOf('-');
                    try {
                        scores.put(Integer.parseInt(str.substring(0, index)), str.substring(index + 1));
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("StringIndexOutOfBoundsException has been thrown!");
                    }
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "No matching file found!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File exception has been thrown!");
            }
        } else {
            try {
                String[] parts = Client.getRanking().split("_");
                int index;
                for (String part : parts) {
                    index = part.indexOf('-');
                    try {
                        scores.put(Integer.parseInt(part.substring(0, index)), part.substring(index + 1));
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("StringIndexOutOfBoundsException has been thrown!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setMaximumSize(new Dimension(this.getWidth() - 40,
                this.getHeight() - lbl_scoreboard.getHeight() - btn_ok.getHeight() - 40));
        textArea.setFont(new Font("Lato", Font.BOLD, 16));

        int i = 0;
        Set<Map.Entry<Integer, String>> entrySet = scores.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet){
            textArea.append(entry.getValue() + "\t" + entry.getKey() + "\n");
            ++i;
            if (i >= 10){
                break;
            }
        }

        pnl.add(lbl_scoreboard);
        pnl.add(Box.createRigidArea(new Dimension(0,20)));
        pnl.add(textArea);
        pnl.add(Box.createRigidArea(new Dimension(0,20)));
        pnl.add(btn_ok);
        btn_ok.requestFocus();
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open()
    {
        this.add(pnl);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Method which determines what happen when the window is closed
     */
    public void close()
    {
        dispose();
    }
}
