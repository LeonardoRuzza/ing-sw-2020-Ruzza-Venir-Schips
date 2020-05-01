package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardButton extends JButton {
    private Card card;
    private JLabel selectionLabel;

    public CardButton(SantoriniGUI santoriniGUI, Card card, int numberOfSelectableCards) {
        this.card = card;
        this.selectionLabel = new JLabel();
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("/src/main/resources/"+card.getName().toLowerCase()+".png"));
        }catch (IOException e){
            System.out.println("Error while trying to open card button image");
            setText(card.getName());
            return;
        }
        if(buttonIcon == null){
            setText(card.getName());
            return;
        }
        setIcon(new ImageIcon(buttonIcon));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        addActionListener(new CardListener(santoriniGUI, numberOfSelectableCards,this));
    }

    public CardButton(SantoriniGUI santoriniGUI, Card card, int numberOfPlayers, boolean isSelected){
        this(santoriniGUI, card, numberOfPlayers);
        if(isSelected) selectCard();
    }

    public Card getCard(){
        return card;
    }

    public void selectCard(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resource/selected_card.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open card selection label image");
            setText("Selected");
            return;
        }
        selectionLabel.setIcon(new ImageIcon(buttonIcon));
        this.setLayout(new BorderLayout());
        this.add(selectionLabel, BorderLayout.SOUTH);
        selectionLabel.setVisible(true);
    }

    public void deselectCard(){
        if(selectionLabel != null){
            selectionLabel.setVisible(false);
            this.remove(selectionLabel);
        }
    }

}
