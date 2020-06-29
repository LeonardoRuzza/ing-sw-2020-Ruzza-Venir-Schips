package it.polimi.ingsw.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FirstPlayerSelectionPanel extends JPanel {
    private final SantoriniGUI gui;

    /**
     * Implements a panel which shows the player cards and asks to select the first player
     * @param gui SantoriniGui
     * @param players Players Data
     */
    public FirstPlayerSelectionPanel(SantoriniGUI gui, List<MessageToGUI.PlayerSummary> players){
        this.gui = gui;
        this.setLayout(null);
        this.setBounds(0,0,1920,1080);
        Image backgroundImg = gui.loadImage("background_lobby");
        Image sysImg = gui.loadImage("sys_label_selectfirstplayer");

        JLabel labelBackground = new JLabel(new ImageIcon(backgroundImg));
        labelBackground.setBounds(0, 0,1920, 1080);
        JLabel sysLabelMessage = new JLabel(new ImageIcon(sysImg));
        sysLabelMessage.setBounds(651, 188,619, 207);

        JPanel p1 = createMiniPlayerPanel(players.get(0).getNickname(), players.get(0).getCardName());
        JPanel p2 = createMiniPlayerPanel(players.get(1).getNickname(), players.get(1).getCardName());
        if(players.size() == 2){
            p1.setLocation(539,413);
            p2.setLocation(1019,413);
            this.add(p1);
            this.add(p2);
            this.add(sysLabelMessage);
            this.add(labelBackground);
            return;
        }
        JPanel p3 = createMiniPlayerPanel(players.get(2).getNickname(), players.get(2).getCardName());
        p1.setLocation(59,413);
        p2.setLocation(539,413);
        p3.setLocation(1019,413);
        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(sysLabelMessage);
        this.add(labelBackground);
    }

    /**
     * Creates a single player panel
     * @param pName player name
     * @param pCard player card name
     * @return JPanel
     */
    public JPanel createMiniPlayerPanel(String pName, String pCard){
        Image nicknameImg = gui.loadImage("label_nickname_selectfirstplayer");
        Image imgP1 = gui.loadImage("cards/"+pCard.toLowerCase());
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBounds(0,0,365, 448);
        resultPanel.setOpaque(true);
        resultPanel.setBackground(new Color(255,255,255,0));

        JLabel labelNicknameBackgroundP1 = new JLabel(new ImageIcon(nicknameImg));
        labelNicknameBackgroundP1.setBounds(0, 361, 365, 87);

        JLabel playerNameP1 = new JLabel(pName);
        playerNameP1.setFont(new Font("ComicSansMS",Font.BOLD,40));
        playerNameP1.setHorizontalAlignment(JTextField.CENTER);
        playerNameP1.setForeground(Color.white);
        playerNameP1.setBounds(30,370,304,54);

        JLabel playerCardP1 = new JLabel(new ImageIcon(imgP1.getScaledInstance(198, 333,Image.SCALE_SMOOTH)));
        playerCardP1.setBounds(83, 0, 198, 333);
        playerCardP1.addMouseListener(new FirstPlayerSelectionListener(gui, pName));

        resultPanel.add(playerNameP1);
        resultPanel.add(labelNicknameBackgroundP1);
        resultPanel.add(playerCardP1);
        return resultPanel;
    }

}
