package it.polimi.ingsw.client.GUI;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VolumeButtonListner extends JFrame implements MouseListener {

    JLabel mute;
    JLabel volume;
    Clip sound;
    JFrame frame;
    boolean active = true;

    public VolumeButtonListner(JLabel mute, JLabel volume, Clip sound, JFrame frame){
        this.mute = mute;
        this.volume = volume;
        this.sound = sound;
        this.frame = frame;
    }

    public void setSound(Clip sound) {
        this.sound = sound;
    }
    public boolean getActive() {
        return active;
    }

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
