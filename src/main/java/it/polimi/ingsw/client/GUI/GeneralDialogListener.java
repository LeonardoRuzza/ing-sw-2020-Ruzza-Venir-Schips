package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class GeneralDialogListener extends JFrame implements MouseListener {
    GamePanel panel;
    String message;
    public GeneralDialogListener(GamePanel panel, String message){
        this.panel = panel;
        this.message = message;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(panel != null){
            if(message != null){
                if(message.contains("quit")){
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                    topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
                }else if(message.contains("lose")){
                    panel.hideGeneralDialog();
                }
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
