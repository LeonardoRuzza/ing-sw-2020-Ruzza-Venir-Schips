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
    private JPanel jPanelCards;
    private List<Card> availableCards;
    private SantoriniGUI santoriniGUI;
    private int numberOfPlayers;
    private int currentStatePanel;
    CardListener cardListener;

    public int getNumbOfAvailableCards(){
        return availableCards.size();
    }
    public int getCurrentStatePanel(){
        return currentStatePanel;
    }
    public void setCurrentStatePanel(int currentStatePanel){
        this.currentStatePanel = currentStatePanel;
    }

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
            CardLabel cardLabel = new CardLabel(availableCards.get(i));
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
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/sys_label_"+n+"_card.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open choose card label image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Choose "+n+" cards:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }

    private JLabel createNextButton(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/btn_next.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open next button image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Next");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(1779,885,150,89);
        jLabel.addMouseListener(new NextListener(this));
        return jLabel;
    }

    private JLabel createPrevButton(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/btn_prev.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open prev button image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Prev");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(52,885,150,89);
        jLabel.addMouseListener(new PrevListener(this));
        return jLabel;
    }

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
                cardLabel = new CardLabel(cardListener.getSelectedCards().get(indexSelectedCard), true);
                cardLabel.addMouseListener(cardListener);
                cardListener.addConnectedLabel(cardLabel);
                jPanelCards.add(cardLabel);
                cardLabel.setBounds(x, 0, cardLabelWidth, cardLabelHeight);
                cardLabel.setBorder(null);
                x+=480;
                continue;
            }
            cardLabel= new CardLabel(availableCards.get(i));
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

    public static JLabel createWaitingCardLabel(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/sys_label_wait_players.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open card's waiting label image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Waiting others players choose of cards");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }
}
