package app.frames;

import app.properties.Config;
import app.intefaces.Frame_user_interface;
import app.main.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

/**
 * In this frame the user can log into the application
 */

public class LoggingFrame extends JFrame implements Frame_user_interface, ComponentListener
{
    private final JLabel lbl_heading;
    private final JLabel lbl_login;
    private final JTextField txt_login;
    private final JButton btn_login;
    private final JButton btn_exit;
    private final JPanel pnl;
    private final int width;
    private final int height;

    /**
     * Basic constructor
     */
    public LoggingFrame()
    {
        Config cfg = Config.getInstance();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        pnl = new JPanel();
        BoxLayout boxLayout = new BoxLayout(pnl, BoxLayout.Y_AXIS);

        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setResizable(true);
        setSize((int)(screenSize.width * 0.15), (int)(screenSize.height * 0.25));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        width = getWidth();
        height = getHeight();
        setTitle(cfg.getProperty("title"));
        pnl.setLayout(boxLayout);

        lbl_heading = new JLabel(cfg.getProperty("heading"));
        lbl_heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_heading.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_login = new JLabel(cfg.getProperty("nickname"));
        lbl_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_login = new JTextField();
        txt_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_login.setHorizontalAlignment(JTextField.CENTER);
        txt_login.addKeyListener(new KeyListener() {
            /**
             * Method vulnerable on key type
             * @param e KeyEvent
             */
            @Override
            public void keyTyped(KeyEvent e) { }
            /**
             * Method vulnerable on key press - in this code it acts as a key binding (Enter)
             * @param e KeyEvent
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (!txt_login.getText().equals("")){
                        setVisible(false);
                    }
                }
            }
            /**
             * Method vulnerable on key release
             * @param e KeyEvent
             */
            @Override
            public void keyReleased(KeyEvent e) { }
        });
        txt_login.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Method vulnerable on text edition
             * @param e DocumentEvent
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                txt_login_change();
            }

            /**
             * Method vulnerable on text edition
             * @param e DocumentEvent
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                txt_login_change();
            }

            /**
             * Method vulnerable on text edition
             * @param e DocumentEvent
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                txt_login_change();
            }
        });
        btn_exit = new JButton(cfg.getProperty("exit"));
        btn_exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_exit.addActionListener(actionEvent -> close());
        btn_login = new JButton(cfg.getProperty("enter"));
        btn_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_login.addActionListener(actionEvent -> setVisible(false));
        btn_login.setEnabled(false);

        pnl.add(lbl_heading);
        pnl.add(Box.createRigidArea(new Dimension(0,(int)(this.getHeight() * 0.4))));
        pnl.add(lbl_login);
        pnl.add(Box.createRigidArea(new Dimension(0,2)));
        pnl.add(txt_login);
        pnl.add(Box.createRigidArea(new Dimension(0,10)));
        pnl.add(btn_login);
        pnl.add(Box.createRigidArea(new Dimension(0,10)));
        pnl.add(btn_exit);
    }

    /**
     * Method which determines what happen when the window is opened
     */
    public void open()
    {
        add(pnl);
        addComponentListener(this);
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
    public void componentHidden(ComponentEvent arg0) {
        Main.nick = txt_login.getText();
    }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentMoved(ComponentEvent arg0) {

    }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentResized(ComponentEvent arg0)
    {
        AffineTransform at = new AffineTransform(0.9 * (float) getWidth()/width,0.0f,0.0f,
                0.9 * (float) getHeight()/height,0.0f,0.0f);
        Font lbl_heading_newFont = lbl_heading.getFont().deriveFont(at);
        lbl_heading.setFont(lbl_heading_newFont);
        Font lbl_login_newFont = lbl_login.getFont().deriveFont(at);
        lbl_login.setFont(lbl_login_newFont);
        Font btn_exit_newFont = btn_exit.getFont().deriveFont(at);
        btn_exit.setFont(btn_exit_newFont);
        Font txt_login_newFont = txt_login.getFont().deriveFont(at);
        txt_login.setFont(txt_login_newFont);
        Font btn_login_newFont = btn_login.getFont().deriveFont(at);
        btn_login.setFont(btn_login_newFont);
        btn_exit.setSize(new Dimension(btn_exit.getWidth() * getWidth()/width,
                btn_exit.getHeight() * getHeight()/height));
        btn_login.setSize(new Dimension(btn_login.getWidth() * getWidth()/width,
                btn_login.getHeight() * getHeight()/height));
    }

    /**
     * Overrides componentListener method
     * @param arg0 ComponentEvent
     */
    @Override
    public void componentShown(ComponentEvent arg0) { }

    private void txt_login_change(){
        btn_login.setEnabled(!txt_login.getText().equals(""));
    }
}
