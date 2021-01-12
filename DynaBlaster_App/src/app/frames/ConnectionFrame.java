package app.frames;

import app.properties.Config;
import app.intefaces.Frame_user_interface;
import app.main.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Frame responsible for inserting IP and Port by the player
 */
public class ConnectionFrame extends JFrame implements Frame_user_interface {
    private final JTextField txt_ip;
    private final JTextField txt_port;
    private final JPanel pnl;

    /**
     * Non-arg constructor
     */
    public ConnectionFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        pnl = new JPanel();
        BoxLayout boxLayout = new BoxLayout(pnl, BoxLayout.Y_AXIS);

        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setSize((int)(screenSize.width * 0.20), (int)(screenSize.height * 0.25));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        setResizable(true);
        setTitle("Connection menu");
        pnl.setLayout(boxLayout);

        JLabel lbl_ip = new JLabel("Server IP:");
        lbl_ip.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lbl_port = new JLabel("Port: ");
        lbl_port.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_ip = new JTextField();
        txt_ip.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_ip.setHorizontalAlignment(JTextField.CENTER);
        txt_port = new JTextField();
        txt_port.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_port.setHorizontalAlignment(JTextField.CENTER);
        JButton btn_exit = new JButton("Exit");
        btn_exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_exit.addActionListener(actionEvent -> System.exit(1));
        JButton btn_online = new JButton("Play online");
        btn_online.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_online.addActionListener(event -> {
            Config cfg = Config.getInstance();
            try {
                cfg.setIP(txt_ip.getText());
                cfg.setPort(Integer.parseInt(txt_port.getText()));
                cfg.loadFromServer();
            } catch (IOException | NullPointerException | NumberFormatException e) {
                System.out.println("Could not connect to the server!");
                JOptionPane.showMessageDialog(null, "Server offline!");
            }
            if(!Client.checkIfOffline()){
                setVisible(false);
                System.out.println("Connection established!");
            }
        });
        JButton btn_offline = new JButton("Play offline");
        btn_offline.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_offline.addActionListener(ActionEvent -> {
            Config cfg = Config.getInstance();
            cfg.load();
            setVisible(false);
        });

        pnl.add(lbl_ip);
        pnl.add(Box.createRigidArea(new Dimension(0,2)));
        pnl.add(txt_ip);
        pnl.add(Box.createRigidArea(new Dimension(0,10)));
        pnl.add(lbl_port);
        pnl.add(Box.createRigidArea(new Dimension(0,2)));
        pnl.add(txt_port);
        pnl.add(Box.createRigidArea(new Dimension(0,10)));
        pnl.add(btn_online);
        pnl.add(Box.createRigidArea(new Dimension(5,0)));
        pnl.add(btn_offline);
        pnl.add(Box.createRigidArea(new Dimension(0,10)));
        pnl.add(btn_exit);
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open() {
        add(pnl);
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
