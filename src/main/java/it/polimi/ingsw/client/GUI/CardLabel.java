package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardLabel extends JLabel {
    private SantoriniGUI santoriniGUI;
    private final Card card;
    private JLabel selectionLabel;
    private JFrame topFrame;

    /**Create a new CardLabel.
     * <p>
     * @param card the card to associate to the label
     */
    public CardLabel(Card card, SantoriniGUI santoriniGUI) {
        this.santoriniGUI = santoriniGUI;
        this.card = card;
        this.selectionLabel = new JLabel();
        Image buttonIcon;
        buttonIcon = this.santoriniGUI.loadImage("cards/"+card.getName().toLowerCase());
        if(buttonIcon == null){
            setText(card.getName());
            return;
        }
        setIcon(new ImageIcon(buttonIcon.getScaledInstance(198,332, Image.SCALE_SMOOTH)));
    }

    /**Create a new CardLabel and select them if necessary.
     *<p>
     * @param card the card to associate to the label
     * @param isSelected the condition of the selection
     * @param jFrame the JFrame
     */
    public CardLabel(Card card, boolean isSelected,JFrame jFrame, SantoriniGUI santoriniGUI){
        this(card, santoriniGUI);
        this.topFrame = jFrame;
        if(isSelected) selectCard();
    }

    /**Getter for the card.
     * <p>
     * @return {@code Card}
     */
    public Card getCard(){
        return card;
    }

    /**Add to the CardLabel a new label (selectionLabel) to show that is now selected.
     * <p>
     */
    public void selectCard(){
        Image buttonIcon;
        buttonIcon = santoriniGUI.loadImage("selected_card");
        if(buttonIcon == null) {
            setText("Selected");
        }
        else{
            selectionLabel.setIcon(new ImageIcon(buttonIcon));
        }
        selectionLabel.setBounds(0, 0,198,332);
        this.add(selectionLabel);
        selectionLabel.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if(topFrame == null) topFrame = this.topFrame;
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    /**Remove to the card (if present) the added label (selectionLabel) that show the card is not yet selected.
     * <p>
     */
    public void deselectCard(){
        if(selectionLabel != null){
            selectionLabel.setVisible(false);
            this.remove(selectionLabel);
        }
    }

}
