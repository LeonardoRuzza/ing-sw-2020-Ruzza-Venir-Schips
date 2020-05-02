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

    public CardLabel(SantoriniGUI santoriniGUI, Card card, int numberOfSelectableCards) {
        this.card = card;
        this.selectionLabel = new JLabel();
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/cards/"+card.getName().toLowerCase()+".png"));
            System.out.println(card.getName().toLowerCase()); //TODO da rimuovere (utile solo per testing)
        }catch (IOException e){
            System.out.println("Error while trying to open card label image");
            setText(card.getName());
            return;
        }
        if(buttonIcon == null){
            setText(card.getName());
            return;
        }
        setIcon(new ImageIcon(buttonIcon));
        addMouseListener(new CardListener(santoriniGUI, numberOfSelectableCards,this));
    }

    public CardLabel(SantoriniGUI santoriniGUI, Card card, int numberOfPlayers, boolean isSelected){
        this(santoriniGUI, card, numberOfPlayers);
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
        //setBounds(this.getBounds().x, this.getBounds().y,198,322);
        this.add(selectionLabel);
        selectionLabel.setVisible(true);
    }

    public void deselectCard(){
        if(selectionLabel != null){
            selectionLabel.setVisible(false);
            this.remove(selectionLabel);
        }
    }

}
