package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardLabel extends JLabel {
    private Card card;
    private JLabel selectionLabel;

    public CardLabel(Card card) {
        this.card = card;
        this.selectionLabel = new JLabel();
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/cards/"+card.getName().toLowerCase()+".png"));
        }catch (IOException e){
            System.out.println("Error while trying to open card label image");
            setText(card.getName());
            return;
        }
        if(buttonIcon == null){
            setText(card.getName());
            return;
        }
        setIcon(new ImageIcon(buttonIcon.getScaledInstance(198,332, Image.SCALE_SMOOTH)));
    }

    public CardLabel(Card card, boolean isSelected){
        this(card);
        if(isSelected) selectCard();
    }

    public Card getCard(){
        return card;
    }

    public void selectCard(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/selected_card.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open card selection label image");
            setText("Selected");
            return;
        }
        selectionLabel.setIcon(new ImageIcon(buttonIcon));
        selectionLabel.setBounds(0, 0,198,332);
        this.add(selectionLabel);
        selectionLabel.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    public void deselectCard(){
        if(selectionLabel != null){
            selectionLabel.setVisible(false);
            this.remove(selectionLabel);
        }
    }

}
