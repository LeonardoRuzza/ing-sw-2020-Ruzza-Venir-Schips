package it.polimi.ingsw.client.GUI;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VolumeButtonListener extends JFrame implements MouseListener {

    private JLabel mute;
    private JLabel volume;
    private Clip sound;
    private JFrame frame;
    private boolean active = true;

    public VolumeButtonListener(JLabel mute, JLabel volume, Clip sound, JFrame frame){
        this.mute = mute;
        this.volume = volume;
        this.sound = sound;
        this.frame = frame;
    }

    /**
     * Setter for the clip which is playing
     * @param sound Playing Clip
     */
    public void setSound(Clip sound) {
        this.sound = sound;
    }

    /**
     * Return if sound is playing or not
     * @return True if active False otherwise
     */
    public boolean getActive() {
        return active;
    }

    /**
     * On mouse click this method will play or stop music
     * @param e Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(active){
            sound.stop();
            mute.setVisible(true);
            volume.setVisible(false);
            active = false;
        }else{
            sound.loop(Clip.LOOP_CONTINUOUSLY);
            mute.setVisible(false);
            volume.setVisible(true);
            active = true;
        }
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
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
