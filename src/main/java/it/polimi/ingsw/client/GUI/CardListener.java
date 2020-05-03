package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class CardListener implements MouseListener {
    private SantoriniGUI santoriniGUI;
    private List<Card> selectedCards = new ArrayList<>();
    private int numberOfSelectableCards;
    private List<CardLabel> connectedLabels = new ArrayList<>();
    private JPanel cardInfo;

    public CardListener(SantoriniGUI santoriniGUI, int numberOfSelectableCards){
        this.santoriniGUI = santoriniGUI;
        this.numberOfSelectableCards = numberOfSelectableCards;
    }

    public void addConnectedLabel(CardLabel cardLabel){
        connectedLabels.add(cardLabel);
    }

    public List<Integer> getNumbersOfSelectedCards(){
        List<Integer> result= new ArrayList<>();
        for(Card c: selectedCards){
            result.add(c.getNumber());
        }
        return result;
    }

    public List<Card> getSelectedCards(){
        return new ArrayList<>(selectedCards);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        Card card = cardLabel.getCard();
        if(selectedCards.contains(card)){
            cardLabel.deselectCard();
            selectedCards.remove(card);
        }
        else {
            if(selectedCards.size() == numberOfSelectableCards -1){
                selectedCards.add(card);
                cardLabel.selectCard();
                for(Card c:selectedCards){
                    santoriniGUI.sendNotification(c.getName());
                }
                selectedCards.clear();
                for(CardLabel cl: connectedLabels){
                    if(cl!=null) {
                        cl.removeMouseListener(this);
                    }
                }
                connectedLabels.clear();
            }
            else{
                if(selectedCards.size() >= numberOfSelectableCards) return; //maybe not possible case
                selectedCards.add(card);
                cardLabel.selectCard();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        Card card = cardLabel.getCard();
        Container cardContainer = cardLabel.getParent().getParent();
        String cardDescription = card.getDesc();
        JTextArea jTextArea= new JTextArea(cardDescription);
        jTextArea.setFont(new Font("ComicSansMS",Font.BOLD,20));
        jTextArea.setForeground(Color.BLACK);
        jTextArea.setEditable(false);
        jTextArea.setHighlighter(null);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setOpaque(true);
        jTextArea.setBackground(new Color(236,228,212,255));
        cardInfo = new JPanel();
        cardInfo.setLayout(null);
        cardInfo.add(jTextArea,0);
        cardInfo.setBounds(cardLabel.getBounds().x+100,cardLabel.getParent().getBounds().y+405,290,165);
        jTextArea.setBounds(0,0,cardInfo.getBounds().width,cardInfo.getBounds().height);
        cardContainer.add(cardInfo,0);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(cardLabel);
        topFrame.getContentPane().revalidate();
        topFrame.getContentPane().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        CardLabel cardLabel = (CardLabel) e.getSource();
        if(cardInfo!=null){
            cardInfo.setVisible(false);
            cardInfo.getParent().remove(cardInfo);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(cardLabel);
            topFrame.getContentPane().revalidate();
            topFrame.getContentPane().repaint();
        }
    }
}
