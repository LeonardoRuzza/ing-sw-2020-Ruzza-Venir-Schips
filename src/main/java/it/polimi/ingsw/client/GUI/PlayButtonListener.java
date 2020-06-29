package it.polimi.ingsw.client.GUI;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayButtonListener extends JFrame implements MouseListener {

    private final SantoriniGUI gui;
    private JTextField nicknameTxtField;
    private JLabel playBtn;
    private JFrame frame;
    private PlayButtonListener listener;

    public PlayButtonListener(JFrame f, JTextField txtField, JLabel btn, SantoriniGUI gui, PlayButtonListener playButtonListener){
        this.gui = gui;
        this.nicknameTxtField = txtField;
        this.playBtn = btn;
        this.frame = f;
        this.listener = playButtonListener;
    }

    /**
     * On click this method will send to client gui the name selected by the user and will remove the button listner
     * @param e Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(nicknameTxtField != null){
            if(nicknameTxtField.getText() != null){
                gui.sendNotification(nicknameTxtField.getText());
                playBtn.removeMouseListener(this);
                return;
            }
        }
        if(gui == null && nicknameTxtField == null && playBtn != null){
            playBtn.addMouseListener(listener);
            frame.getContentPane().remove(0);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
        }
    }

    /**
     * Mouse Pressed Event
     * @param e Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Mouse Released Event
     * @param e Mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    /**
     * Mouse Entered Event
     * @param e Mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    /**
     * Mouse Exited Event
     * @param e Mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
