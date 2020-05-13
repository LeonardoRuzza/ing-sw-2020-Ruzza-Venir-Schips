package it.polimi.ingsw.client.GUI;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayButtonListener extends JFrame implements MouseListener {

    ClientGUI gui;
    JTextField nicknameTxtField;
    JLabel playBtn;
    JFrame frame;
    PlayButtonListener listener;

    public PlayButtonListener(JFrame f, JTextField txtField, JLabel btn, ClientGUI gui, PlayButtonListener playButtonListener){
        this.gui = gui;
        this.nicknameTxtField = txtField;
        this.playBtn = btn;
        this.frame = f;
        this.listener = playButtonListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(nicknameTxtField != null){
            if(nicknameTxtField.getText() != null){
                gui.outcomeGUI.add(nicknameTxtField.getText());
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

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
