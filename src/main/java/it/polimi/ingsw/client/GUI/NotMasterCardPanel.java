package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotMasterCardPanel extends JPanel {
    private SantoriniGUI santoriniGUI;
    private List<Card> availableCards;

    public NotMasterCardPanel(SantoriniGUI santoriniGUI, List<Card> availableCards) {
        this.santoriniGUI = santoriniGUI;
        this.availableCards = availableCards;
        setLayout(new BorderLayout());
        add(createChooseCardLabel(), BorderLayout.NORTH);
        JPanel jPanelCards = new JPanel();
        jPanelCards.setLayout(new FlowLayout());
        for(int i=0; i<4 && i<availableCards.size() ;i++){
            jPanelCards.add(new CardButton(santoriniGUI, availableCards.get(i), 1));
        }
        ((FlowLayout)jPanelCards.getLayout()).setHgap(30);
        add(jPanelCards, BorderLayout.SOUTH);
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
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }

}
