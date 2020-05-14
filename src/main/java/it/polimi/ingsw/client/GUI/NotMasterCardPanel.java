package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotMasterCardPanel extends JPanel {
    private final SantoriniGUI santoriniGUI;
    private List<Card> availableCards;
    CardListener cardListener;

    /**Create a new NotMasterCardPanel (a panel to show to a player who is not a master for the choose of his card).
     * <p>
     * @param santoriniGUI the santoriniGUI to associate to this
     * @param availableCards the List of available cards for the choose
     */
    public NotMasterCardPanel(SantoriniGUI santoriniGUI, List<Card> availableCards) {
        int x=0;
        int cardLabelHeight = 332;
        int cardLabelWidth = 198;
        int y = 431;
        this.santoriniGUI = santoriniGUI;
        this.availableCards = availableCards;
        cardListener = new CardListener(santoriniGUI, 1);
        setLayout(null);
        setSize(1920,1080);
        add(createChooseCardLabel());
        JPanel jPanelCards = new JPanel();
        jPanelCards.setLayout(null);
        jPanelCards.setBounds(141, y, 1638, cardLabelHeight);
        for(int i=0; i<4 && i<availableCards.size() ;i++){
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
        setOpaque(true);
        setBackground(new Color(0,0,0,0));
    }

    private JLabel createChooseCardLabel(){
        Image buttonIcon;
        try {
            buttonIcon = ImageIO.read(new File("src/main/resources/sys_label_1_card.png"));
        }catch (IOException e){
            System.out.println("Error while trying to open choose card label image (not Master)");
            return new JLabel("Error: image not found");
        }
        if(buttonIcon == null) return new JLabel("Choose your card:");
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(buttonIcon));
        jLabel.setBounds(651,188,619,207);
        return jLabel;
    }

}
