package it.polimi.ingsw.client.GUI;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayButtonListner extends JFrame implements MouseListener {

    ClientGUI gui;
    JTextField nicknameTxtField;
    JLabel playBtn;
    JFrame frame;
    PlayButtonListner listner;

    public PlayButtonListner(JFrame f, JTextField txtField, JLabel btn, ClientGUI gui, PlayButtonListner playButtonListner){
        this.gui = gui;
        this.nicknameTxtField = txtField;
        this.playBtn = btn;
        this.frame = f;
        this.listner = playButtonListner;
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
            playBtn.addMouseListener(listner);
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
