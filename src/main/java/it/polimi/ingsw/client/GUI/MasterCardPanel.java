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
        this.availableCards = new ArrayList<>(availableCards);
        this.numberOfPlayers = numberOfPlayers;
        this.santoriniGUI = santoriniGUI;
        currentStatePanel = 0;
        setLayout(new BorderLayout());
        add(createChooseCardLabel(numberOfPlayers), BorderLayout.NORTH);
        JPanel jPanelTotal = new JPanel();
        jPanelTotal.setLayout(new BorderLayout());
        jPanelCards = new JPanel();
        jPanelCards.setLayout(new FlowLayout());
        for(int i=0; i<4 && i<availableCards.size() ;i++){
            jPanelCards.add(new CardButton(santoriniGUI, availableCards.get(i), numberOfPlayers));
        }
        ((FlowLayout)jPanelCards.getLayout()).setHgap(30); //settare il gap adatto
        jPanelTotal.add(jPanelCards,BorderLayout.NORTH);
        jPanelTotal.add(createNextButton(), BorderLayout.EAST);
        jPanelTotal.add(createPrevButton(), BorderLayout.WEST);
        this.add(jPanelTotal, BorderLayout.SOUTH);

    }

    private JLabel createChooseCardLabel(int n){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resource/sys_label_"+n+"_card.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open choose card label image");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Choose "+n+" cards:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }

    private JButton createNextButton(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/btn_next.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open next button image");
            return new JButton("Error: image not found");
        }
        if(buttonIcon == null) return new JButton("Next");
        JButton jButton = new JButton();
        jButton.setIcon(new ImageIcon(buttonIcon));
        jButton.setHorizontalAlignment(SwingConstants.RIGHT);
        jButton.addActionListener(new NextListener(this));
        return jButton;
    }

    private JButton createPrevButton(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/btn_prev.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open prev button image");
            return new JButton("Error: image not found");
        }
        if(buttonIcon == null) return new JButton("Prev");
        JButton jButton = new JButton();
        jButton.setIcon(new ImageIcon(buttonIcon));
        jButton.setHorizontalAlignment(SwingConstants.LEFT);
        jButton.addActionListener(new PrevListener(this));
        return jButton;
    }

    public void setjPanelCards(int n){
        for(int i=0; i<4 && i<availableCards.size(); i++) {
            jPanelCards.remove(i);
        }
        for(int i=n; i<n+4 && i<availableCards.size() ;i++) {
            boolean isSelected = CardListener.getNumbersOfSelectedCards().contains(availableCards.get(i).getNumber());
            if(isSelected){
                int indexSelectedCard=0;
                for(Card c: CardListener.getSelectedCards()){
                    if(availableCards.get(i).getNumber() == c.getNumber()){
                        indexSelectedCard = CardListener.getSelectedCards().indexOf(c);
                    }
                }
                jPanelCards.add(new CardButton(santoriniGUI, CardListener.getSelectedCards().get(indexSelectedCard), numberOfPlayers, true));
                return;
            }
            jPanelCards.add(new CardButton(santoriniGUI, availableCards.get(i), numberOfPlayers));
        }
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
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }
}
