package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MasterCardPanel extends JPanel {
    private final JPanel jPanelCards;
    private List<Card> availableCards;
    private final SantoriniGUI santoriniGUI;
    private final int numberOfPlayers;
    private int currentStatePanel;
    private CardListener cardListener;

    /**Getter for the size of availableCards.
     * <p>
     * @return {@code int}
     */
    public int getNumbOfAvailableCards(){
        return availableCards.size();
    }

    /**Getter for the currentStatePanel.
     * <p>
     * @return {@code int}
     */
    public int getCurrentStatePanel(){
        return currentStatePanel;
    }

    /**Setter for the currentStatePanel.
     * <p>
     * @param currentStatePanel the number of the new state of the panel
     */
    protected void setCurrentStatePanel(int currentStatePanel){
        this.currentStatePanel = currentStatePanel;
    }

    /**Create a new MasterCardPanel (the panel to show for the selection of the cards to the master player).
     * <p>
     * @param santoriniGUI the santoriniGUI to associate to this
     * @param availableCards the List of available cards
     * @param numberOfPlayers the number of the players of the match
     */
    public MasterCardPanel(SantoriniGUI santoriniGUI, List<Card> availableCards, int numberOfPlayers){
        int x=0;
        int cardLabelHeight = 332;
        int cardLabelWidth = 198;
        int y = 431;
        this.availableCards = new ArrayList<>(availableCards);
        this.numberOfPlayers = numberOfPlayers;
        this.santoriniGUI = santoriniGUI;
        currentStatePanel = 0;
        cardListener = new CardListener(santoriniGUI, numberOfPlayers);
        setSize(1920,1080);
        setLayout(null);
        add(createChooseCardLabel(numberOfPlayers));
        jPanelCards = new JPanel();
        jPanelCards.setLayout(null);
        jPanelCards.setBounds(141, y, 1638, cardLabelHeight);
        for(int i=0; i<4 && i<availableCards.size() ; i++){
            CardLabel cardLabel = new CardLabel(availableCards.get(i),santoriniGUI);
            cardLabel.addMouseListener(cardListener);
            cardListener.addConnectedLabel(cardLabel);
            jPanelCards.add(cardLabel);
            cardLabel.setBounds(x, 0, cardLabelWidth, cardLabelHeight);
            cardLabel.setBorder(null);
            x+=480;
        }
        jPanelCards.setOpaque(true);
        jPanelCards.setBackground(new Color(0,0,0,0));
        add(jPanelCards);
        add(createNextButton());
        add(createPrevButton());
        setOpaque(true);
        setBackground(new Color(0,0,0,0));
    }

    private JLabel createChooseCardLabel(int n){
        Image buttonIcon;
        buttonIcon = santoriniGUI.loadImage("sys_label_"+n+"_card");
        if(buttonIcon == null) return new JLabel("Choose "+n+" cards:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }

    private JLabel createNextButton(){
        Image buttonIcon;
        buttonIcon = santoriniGUI.loadImage("btn_next");
        if(buttonIcon == null) return new JLabel("Next");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(1779,885,89,150);
        jLabel.addMouseListener(new NextListener(this, cardListener));
        return jLabel;
    }

    private JLabel createPrevButton(){
        Image buttonIcon;
        buttonIcon = santoriniGUI.loadImage("btn_prev");
        if(buttonIcon == null) return new JLabel("Prev");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(52,885,89,150);
        jLabel.addMouseListener(new PrevListener(this, cardListener));
        return jLabel;
    }

    /**Refresh the jPanelCards after to be called by PrevLabel or NextLabel removing the 4 old cards and adding new 4 cards.
     * Only cards selected are created maintaining the same Card instance connected.
     * <p>
     * @param n is the number from start to show cards
     */
    public void setjPanelCards(int n){
        int x=0;
        int cardLabelHeight = 332;
        int cardLabelWidth = 198;
        for(Component cl: jPanelCards.getComponents()){
            jPanelCards.remove(cl);
        }
        for(int i=n; i<n+4 && i<availableCards.size() ;i++) {
            boolean isSelected = cardListener.getNumbersOfSelectedCards().contains(availableCards.get(i).getNumber());
            CardLabel cardLabel;
            if(isSelected){
                int indexSelectedCard=0;
                for(Card c: cardListener.getSelectedCards()){
                    if(availableCards.get(i).getNumber() == c.getNumber()){
                        indexSelectedCard = cardListener.getSelectedCards().indexOf(c);
                    }
                }
                cardLabel = new CardLabel(cardListener.getSelectedCards().get(indexSelectedCard), true, (JFrame) SwingUtilities.getWindowAncestor(this),santoriniGUI);
                cardLabel.addMouseListener(cardListener);
                cardListener.addConnectedLabel(cardLabel);
                jPanelCards.add(cardLabel);
                cardLabel.setBounds(x, 0, cardLabelWidth, cardLabelHeight);
                cardLabel.setBorder(null);
                x+=480;
                continue;
            }
            cardLabel= new CardLabel(availableCards.get(i),santoriniGUI);
            cardLabel.addMouseListener(cardListener);
            cardListener.addConnectedLabel(cardLabel);
            jPanelCards.add(cardLabel);
            cardLabel.setBounds(x, 0, cardLabelWidth, cardLabelHeight);
            cardLabel.setBorder(null);
            x+=480;
        }
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(jPanelCards);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }
}
