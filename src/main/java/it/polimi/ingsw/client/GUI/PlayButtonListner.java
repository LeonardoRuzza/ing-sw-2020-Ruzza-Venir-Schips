package it.polimi.ingsw.client.GUI;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayButtonListner extends JFrame implements MouseListener {

    ClientGUI gui;
    JTextField nicknameTxtField;
    JLabel playBtn;

    public PlayButtonListner(JTextField txtField, JLabel btn, ClientGUI gui){
        this.gui = gui;
        this.nicknameTxtField = txtField;
        this.playBtn = btn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(nicknameTxtField != null){
            if(nicknameTxtField.getText() != null){
                gui.outcomeGUI.add(nicknameTxtField.getText());
                playBtn.removeMouseListener(this);
            }
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
