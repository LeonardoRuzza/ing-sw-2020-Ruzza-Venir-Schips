package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker.Color;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel {
    private final int gridSide = 5;

    private SantoriniGUI gui;


    private JLabel boardPanel;
    private GridBoardPanel gridBoard;
    private PlayerCardPanel cardPanelP1;
    private PlayerCardPanel cardPanelP2;
    private PlayerCardPanel cardPanelP3;
    private ServerMessageReceiver messagePrinter;

    public GamePanel(SantoriniGUI gui){
        this.gui = gui;
        this.setLayout(null);
        this.setBounds(0,0,1920,1080);
        BufferedImage boardImg = null;
        try {
            boardImg = ImageIO.read(new File("src/main/resources/board_base.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        boardPanel = new JLabel(new ImageIcon(boardImg));
        boardPanel.setBounds(196,0,1724,970);
        cardPanelP1 = new PlayerCardPanel();
        cardPanelP2 = new PlayerCardPanel();
        cardPanelP3 = new PlayerCardPanel();
        cardPanelP1.setBounds(1571,540,349,540);
        cardPanelP2.setBounds(0,540,349,540);
        cardPanelP3.setBounds(0,0,349,540);
        messagePrinter = new ServerMessageReceiver();
        messagePrinter.setBounds(350,953,1221,127);
        gridBoard = new GridBoardPanel(5,5,30,29,112,112);
        gridBoard.setLocation(719,145);
        this.add(gridBoard);
        this.add(messagePrinter);
        this.add(cardPanelP1);
        this.add(cardPanelP2);
        this.add(cardPanelP3);
        this.add(boardPanel);
    }

    public void updateCardPanel(int numPlayer, @Nullable String nickName, @Nullable String cardName, @Nullable Worker.Color playerColor){
        switch (numPlayer){
            case 1:
                if(nickName!=null){
                    cardPanelP1.updateNamePlayer(nickName);
                }
                if (cardName != null) {
                    cardPanelP1.updateCardImg(cardName);
                }
                if (playerColor != null) {
                    cardPanelP1.updateColorNamePlayer(playerColor);
                }
                break;
            case 2:
                if(nickName!=null){
                    cardPanelP2.updateNamePlayer(nickName);
                }
                if (cardName != null) {
                    cardPanelP2.updateCardImg(cardName);
                }
                if (playerColor != null) {
                    cardPanelP2.updateColorNamePlayer(playerColor);
                }
                break;
            case 3:
                if(nickName!=null){
                    cardPanelP3.updateNamePlayer(nickName);
                }
                if (cardName != null) {
                    cardPanelP3.updateCardImg(cardName);
                }
                if (playerColor != null) {
                    cardPanelP3.updateColorNamePlayer(playerColor);
                }
                break;
        }
    }

    public void updateServerMessage(String message){
        if(messagePrinter!=null){
            messagePrinter.updateElem(message);
        }
    }

    public void updateGrid(Board board){
        for(int y = 0; y < gridSide; y++){
            for(int x = 0; x < gridSide; x++){
                CellPanel panel = this.gridBoard.getCell(y,x);
                Cell lastBusy = board.getLastBusyCell(x,y);
                if(lastBusy != null){
                    Worker workerIcCell = board.workerInCell(x,y);
                    Block blockInCell = board.blockInCell(x,y);
                    if(workerIcCell != null){
                        if(blockInCell != null){
                            if(panel.getBlockElem().getBlock() != blockInCell || panel.getBlockElem().getzCoord() != lastBusy.getzCoord()){
                                panel.getBlockElem().updateElem(blockInCell,lastBusy.getzCoord());
                            }
                            if(panel.getWorkerElem().getColor() != workerIcCell.getColor() && panel.getWorkerElem().getGender() != workerIcCell.getGender()){
                                panel.getWorkerElem().updateElem(workerIcCell.getGender(), workerIcCell.getColor());
                            }
                        }else{
                            panel.getBlockElem().updateNull();
                            if(panel.getWorkerElem().getColor() != workerIcCell.getColor() && panel.getWorkerElem().getGender() != workerIcCell.getGender()){
                                panel.getWorkerElem().updateElem(workerIcCell.getGender(), workerIcCell.getColor());
                            }
                        }
                    }else{
                        panel.getWorkerElem().updateNull();
                        if(blockInCell != null){
                            if(panel.getBlockElem().getBlock() != blockInCell || panel.getBlockElem().getzCoord() != lastBusy.getzCoord()){
                                panel.getBlockElem().updateElem(blockInCell,lastBusy.getzCoord());
                            }
                        }else{
                            panel.getBlockElem().updateNull();
                        }
                    }
                }else{
                    panel.getBlockElem().updateNull();
                    panel.getWorkerElem().updateNull();
                }
            }
        }
    }

    public void updateFrame(JFrame frame){
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private class GridBoardPanel extends JPanel{
        private int rows;
        private int columns;
        private int hgap;
        private int vgap;
        private int elemHeight;
        private int elemWidth;
        public GridBoardPanel(int rows, int columns, int hgap, int vgap, int elemWidth, int elemHeight){
            this.rows = rows;
            this.columns = columns;
            this.hgap = hgap;
            this.vgap = vgap;
            this.elemHeight = elemHeight;
            this.elemWidth = elemWidth;
            this.setLayout(null);
            this.setSize((int)((elemWidth*columns)+(columns*hgap)),(int)((elemHeight*rows)+(vgap*rows)));
            this.setOpaque(false);
            this.setBackground(new java.awt.Color(0,0,0,0));
            for(int y = 0; y<gridSide; y++){
                for (int x = 0; x < gridSide; x++){
                    this.addAsGrid(this.createGridPanel(x,y));
                }
            }
        }

        private JPanel createGridPanel(int x, int y) {
            CellPanel panel = new CellPanel(x,y,elemWidth,elemHeight);
            return panel;
        }

        private void addAsGrid(JPanel component){
            int x = (hgap+elemWidth)*(this.getComponentCount()%columns);
            int y = ((int)((this.getComponentCount())/columns)) *((int)(elemHeight+vgap));
            this.add(component);
            component.setBounds(x,y,elemWidth,elemHeight);
        }

        public CellPanel getCell(int row, int column){
            for(int x=0; x<rows*columns; x++){
                if(this.getComponent(x) instanceof CellPanel){
                    CellPanel panel = (CellPanel)this.getComponent(x);
                    if(panel.getRow() == row && panel.getColumn()==column){
                        return panel;
                    }
                }
            }
            return null;
        }
    }

    protected class CellPanel extends JPanel{
        int row;
        int column;
        CellButton cellButton;
        WorkerElem workerElem;
        BlockElem blockElem;
        public CellPanel(int x, int y, int width, int height){
            this.row = y;
            this.column = x;
            this.setLayout(null);
            this.setBounds(0,0,width,height);
            this.setOpaque(false);
            this.setBackground(new java.awt.Color(0,0,0,0));
            cellButton = new CellButton(x,y);
            workerElem = new WorkerElem(null,null);
            blockElem = new BlockElem(null,-1);
            blockElem.setBounds(0,0,width,height);
            workerElem.setBounds(0,0,width,height);
            cellButton.setBounds(0,0,width,height);
            this.add(cellButton);
            this.add(workerElem);
            this.add(blockElem);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public CellButton getCellButton() {
            return cellButton;
        }

        public WorkerElem getWorkerElem() {
            return workerElem;
        }

        public BlockElem getBlockElem() {
            return blockElem;
        }
    }

    protected class CellButton extends JLabel{
        private int xCoord;
        private int yCoord;
        private final Dimension dim = new Dimension(112,112);
        public CellButton(int x, int y){
            super("");
            this.xCoord = x;
            this.yCoord = y;
            this.setSize(dim);
            this.addMouseListener(new CellButtonListner(gui, this));
        }
        public int getxCoord() {
            return xCoord;
        }

        public int getyCoord() {
            return yCoord;
        }
    }

    private class ServerMessageReceiver extends JPanel{
        JLabel backGroundRec;
        JLabel messageText;
        public ServerMessageReceiver(){
            this.setLayout(null);
            this.setSize(1221,127);
            BufferedImage messageBackgroundImg = null;
            try {
                messageBackgroundImg = ImageIO.read(new File("src/main/resources/label_sys_message_game.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            backGroundRec = new JLabel(new ImageIcon(messageBackgroundImg));
            backGroundRec.setBounds(0,0,this.getWidth(),this.getHeight());
            messageText = new JLabel("");
            messageText.setBounds(74,47,1063,61);
            messageText.setFont(new Font("ComicSansMS",Font.BOLD,44));
            messageText.setForeground(java.awt.Color.WHITE);
            messageText.setHorizontalAlignment(JTextField.CENTER);
            this.add(messageText);
            this.add(backGroundRec);
        }
        public void updateElem(String newMessage){
            if(messageText != null){
                messageText.setText(newMessage);
            }
        }
    }

    private class BlockElem extends JLabel{
        private Block block;
        private int zCoord;
        public BlockElem(Block block, int zCoord) {
            if(block != null){
                this.block = block;
                this.zCoord = zCoord;
                if(block != null){
                    BufferedImage blockImg = null;
                    try {
                        blockImg = ImageIO.read(new File(selectBlock(block, zCoord)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.setIcon(new ImageIcon(blockImg));
                }
            }else{
                this.zCoord = 0;
                this.block = null;
                this.setIcon(null);
            }
        }

        public void updateNull(){
            this.zCoord = 0;
            this.block = null;
            this.setIcon(null);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        public void updateElem(Block block, int zCoord) {
            this.block = block;
            this.zCoord = zCoord;
            if(block != null){
                BufferedImage blockImg = null;
                try {
                    blockImg = ImageIO.read(new File(selectBlock(block, zCoord)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.setIcon(new ImageIcon(blockImg));
            }
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        private String selectBlock(Block block, int zCoord){
            String path = "src/main/resources/blok_";
            switch (block){
                case B1:
                    path = path + "lv1.png";
                    break;
                case B2:
                    path = path + "lv2.png";
                    break;
                case B3:
                    path = path + "lv3.png";
                    break;
                case DORSE:
                    switch(zCoord){
                        case 0:
                            path = path + "dorse_lv0.png";
                            break;
                        case 1:
                            path = path + "dorse_lv1.png";
                            break;
                        case 2:
                            path = path + "dorse_lv2.png";
                            break;
                        case 3:
                            path = path + "dorse.png";
                            break;
                    }
                    break;
            }
            return path;
        }

        public Block getBlock() {
            return block;
        }

        public int getzCoord() {
            return zCoord;
        }
    }

    private class WorkerElem extends JLabel{
        private Worker.Gender gender;
        private Worker.Color color;

        public WorkerElem(Worker.Gender gender, Worker.Color color){
            if(this.gender != null && this.color != null){
                this.color = color;
                this.gender = gender;
                if(this.gender != null && this.color != null){
                    BufferedImage wrkImg = null;
                    try {
                        wrkImg = ImageIO.read(new File(selectImage(color)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.setIcon(new ImageIcon(wrkImg));
                }
            }else{
                this.gender = null;
                this.color = null;
                this.setIcon(null);
            }
        }

        public void updateNull(){
            this.gender = null;
            this.color = null;
            this.setIcon(null);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        public void updateElem(Worker.Gender gender, Worker.Color color){
            this.color = color;
            this.gender = gender;
            if(this.gender != null && this.color != null){
                BufferedImage wrkImg = null;
                try {
                    wrkImg = ImageIO.read(new File(selectImage(color)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.setIcon(new ImageIcon(wrkImg));
            }
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        private String selectImage(Worker.Color color){
            String path = "src/main/resources/wrk_";
            switch (color){
                case RED:
                    path = path + "red" + "_small.png";
                    break;
                case GREEN:
                    path = path + "green" + "_small.png";
                    break;
                case YELLOW:
                    path = path + "yellow" + "_small.png";
                    break;
                case PURPLE:
                    path = path + "purple" + "_small.png";
                    break;
            }
            return path;
        }

        public Color getColor() {
            return color;
        }

        public Worker.Gender getGender() {
            return gender;
        }
    }

    protected class PlayerCardPanel extends JPanel{
        private JLabel cardInfoButton;
        private JLabel playerName;
        private JLabel card;
        private String nickName;
        private String cardName;

        private JPanel dialogPanelCardInfo;

        private CardInfoListner listnerCardDetail;

        public PlayerCardPanel(){
            this.setLayout(null);
            this.setSize(349,540);

            BufferedImage cardContainerImg = null;
            try {
                cardContainerImg = ImageIO.read(new File("src/main/resources/card_container.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            JLabel cardContainer = new JLabel(new ImageIcon(cardContainerImg));
            cardContainer.setBounds(0,0,this.getWidth(),this.getHeight());

            playerName = new JLabel("");
            playerName.setBounds(5,474,341,61);
            playerName.setFont(new Font("ComicSansMS",Font.BOLD,40));
            playerName.setHorizontalAlignment(JTextField.CENTER);

            card = new JLabel("");
            card.setBounds(61,16,229,384);

            cardInfoButton = new JLabel("");
            cardInfoButton.setBounds(card.getBounds());
            listnerCardDetail = new CardInfoListner(this);
            cardInfoButton.addMouseListener(listnerCardDetail);

            this.add(cardInfoButton);
            this.add(card);
            this.add(playerName);
            this.add(cardContainer);
        }

        private java.awt.Color switcherColor(Worker.Color color){
            switch (color){
                case RED:
                    return java.awt.Color.RED;
                case GREEN:
                    return java.awt.Color.GREEN;
                case YELLOW:
                    return java.awt.Color.YELLOW;
                case PURPLE:
                    return new java.awt.Color(128,0,128);
            }
            return null;
        }

        public void updateNamePlayer(String name){
            this.nickName = nickName;
            playerName.setText(name);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        public void updateColorNamePlayer(Worker.Color color){
            playerName.setForeground(switcherColor(color));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);
        }

        public void updateCardImg(String cardName){
            this.cardName = cardName;
            String cardPath = "src/main/resources/cards/"+ cardName.toLowerCase() +".png";

            BufferedImage cardImg = null;
            try {
                cardImg = ImageIO.read(new File(cardPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image cardImgScaled = cardImg.getScaledInstance(card.getWidth(),card.getHeight(),Image.SCALE_SMOOTH);
            card.setIcon(new ImageIcon(cardImgScaled));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateFrame(topFrame);

            dialogPanelCardInfo = this.initDialogPanelCardInfo();
        }

        public JPanel initDialogPanelCardInfo(){
            JPanel screenPanel = new JPanel();
            screenPanel.setLayout(null);
            screenPanel.setBounds(0,0,1920,1080);
            screenPanel.setOpaque(true);
            screenPanel.setBackground(new java.awt.Color(0,0,0,0));

            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(null);
            String path = "src/main/resources/dialog_base_gods_detail.png";
            BufferedImage backgroundImg = null;
            try {
                backgroundImg = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImg));

            JLabel nameGod = new JLabel(String.valueOf(this.getCardName().charAt(0)).toUpperCase() + this.getCardName().substring(1));
            nameGod.setFont(new Font("ComicSansMS",Font.BOLD,40));
            nameGod.setHorizontalAlignment(JTextField.CENTER);
            nameGod.setForeground(java.awt.Color.white);

            path = "src/main/resources/gods/"+ this.getCardName().toLowerCase() +".png";
            BufferedImage cardImg = null;
            try {
                cardImg = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image cardImgScaled = cardImg.getScaledInstance(500,512,Image.SCALE_SMOOTH);
            JLabel godImg = new JLabel(new ImageIcon(cardImgScaled));

            Card cardDesc = new Card(this.getCardName().toUpperCase());
            JTextArea godDescription = new JTextArea(cardDesc.getDesc());
            godDescription.setFont(new Font("ComicSansMS",Font.BOLD,54));
            //godDescription.setHorizontalAlignment(JTextField.CENTER);
            godDescription.setForeground(java.awt.Color.white);
            godDescription.setEditable(false);
            godDescription.setSelectionColor(new java.awt.Color(0,0,0,0));
            godDescription.setHighlighter(null);
            godDescription.setLineWrap(true);
            godDescription.setWrapStyleWord(true);
            godDescription.setOpaque(true);
            godDescription.setBackground(new java.awt.Color(0,0,0,0));

            path = "src/main/resources/btn_close.png";
            BufferedImage closeImg = null;
            try {
                closeImg = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel closeDescPanel = new JLabel(new ImageIcon(closeImg));
            closeDescPanel.addMouseListener(listnerCardDetail);

            closeDescPanel.setBounds(1627,0,73,70);
            resultPanel.add(closeDescPanel);
            godDescription.setBounds(474,171,1190,359);
            resultPanel.add(godDescription);
            nameGod.setBounds(613,33,482,79);
            resultPanel.add(nameGod);
            godImg.setBounds(-26,-85,500,512);
            resultPanel.add(godImg);
            backgroundLabel.setBounds(0,0,1700,775);
            resultPanel.add(backgroundLabel);

            resultPanel.setBounds(110,153,1700,775);
            screenPanel.add(resultPanel);

            return screenPanel;
        }

        public void showDialogPanelCardInfo(){
            dialogPanelCardInfo.setBounds(110,153,1700,775);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().add(dialogPanelCardInfo,0);
            updateFrame(topFrame);
        }

        public void closeDialogPanelCardInfo(){
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().remove(0);
            updateFrame(topFrame);
        }

        public String getNickName() {
            return nickName;
        }

        public String getCardName() {
            return cardName;
        }
    }
}
