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

    /**
     * On click this listener close the dialog and if the showed message contains close the frame
     * @param e Mouse Event
     */
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
