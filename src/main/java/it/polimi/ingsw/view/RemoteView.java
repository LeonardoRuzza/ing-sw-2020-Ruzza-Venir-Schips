package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ChoiceResponseMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.GameMessage;

public class RemoteView extends View {
    private ClientConnection clientConnection;
    private Player player;

    public RemoteView(ClientConnection clientConnection, Player player) {
        super(player);
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new RemoteView.MessageReceiver());
    }

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            int x=-1, y=-1;
            String optional="";
            Worker.Gender gender=Worker.Gender.Male;
            System.out.println("Received: " + message);
            if(message.isEmpty()){
                clientConnection.asyncSend("Input empty!");
                return;
            }
            try{
                String[] inputs = message.split(",");
                switch (inputs.length){
                    case 1:
                        if(!(inputs[0].toUpperCase().equals("MALE"))){
                            if(inputs[0].toUpperCase().equals("FEMALE")){
                                gender = Worker.Gender.Female;
                            }
                            else{
                                if(GameMessage.isValidOptional(inputs[0].toUpperCase())){
                                    optional = inputs[0].toUpperCase();
                                }
                                else {
                                    clientConnection.asyncSend("Input invalid!");
                                    return;
                                }
                            }
                        }
                        break;
                    case 2:
                        x=Integer.parseInt(inputs[0]);
                        y=Integer.parseInt(inputs[1]);
                        break;
                    default:
                        x=Integer.parseInt(inputs[0]);
                        y=Integer.parseInt(inputs[1]);
                        if(GameMessage.isValidOptional(inputs[2].toUpperCase())){
                            optional = inputs[2].toUpperCase();
                        }
                        else {
                            clientConnection.asyncSend("Input invalid!");
                            return;
                        }
                        break;
                }
                manageChoice(player, x, y, optional, gender);
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Input invalid!");
            }
        }
    }

    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }
    protected void showMessageSync(Object message) {
        clientConnection.send(message);
    }

    @Override
    public void update(ChoiceResponseMessage message) {
        int counterActivePlayers=0;
        Player[] players = message.getMatch().getPlayers();
        for (Player value : players) {
            if (value != null) {
                counterActivePlayers++;
            }
        }
        showMessageSync(message.getMatch().getBoard());
        String resultMsg = "";
        boolean gameOverWin = message.getNextInstruction().equals(GameMessage.turnMessageWin);
        boolean gameOverDefeat = message.getNextInstruction().equals(GameMessage.turnMessageLose) || message.getNextInstruction().equals(GameMessage.turnMessageLoserNoWorker) ;
        if (gameOverWin) {
            if (message.getPlayer() == getPlayer()) {
                resultMsg = GameMessage.turnMessageWin + "\n";
            } else {
                resultMsg = GameMessage.turnMessageLose + "\n";
            }
        }
        else {
            if (gameOverDefeat) {
                if (message.getPlayer() == getPlayer()) {
                    resultMsg = message.getNextInstruction() + "\n";
                } else {
                    resultMsg = message.getPlayer().getNickname()+ "lose. " + "\n";
                    if(counterActivePlayers<=2) resultMsg += GameMessage.turnMessageWin + "\n";
                }
            }
            else {
                if(message.getPlayer() == getPlayer()){
                    resultMsg = message.getNextInstruction() + "\n";
                }
                else{
                    if(message.getNextInstruction().equals(GameMessage.turnMessageTurnEnd) && message.getMatch().getPlayingNow().getNickname().equals(getPlayer().getNickname())) {
                        resultMsg = GameMessage.turnMessageSelectYourWorker + "\n";
                    }
                }
            }
        }
        showMessageSync(resultMsg);

        if((gameOverWin && message.getPlayer() == getPlayer()) || (gameOverDefeat && counterActivePlayers<3 && message.getPlayer() != getPlayer())){
            clientConnection.close(false);
        }
        if(gameOverDefeat && message.getPlayer() == getPlayer() && counterActivePlayers==3) {
            clientConnection.close(true);
        }
    }
}
