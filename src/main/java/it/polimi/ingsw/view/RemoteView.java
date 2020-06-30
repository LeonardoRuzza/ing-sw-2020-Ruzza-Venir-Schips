package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ChoiceResponseMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.ClosingConnectionParameter;
import it.polimi.ingsw.utils.GameMessage;

public class RemoteView extends View {
    private ClientConnection clientConnection;

    public RemoteView(ClientConnection clientConnection, Player player) {
        super(player);
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new RemoteView.MessageReceiver());
        clientConnection.asyncSend(getPlayer().clone());
    }

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            int x=-1, y=-1;
            String optional="";
            Worker.Gender gender=null;
            System.out.println("Received: " + message);
            if(message.isEmpty()){
                clientConnection.asyncSend("Input empty!");
                return;
            }
            try{
                String[] inputs = message.split(",");
                switch (inputs.length){
                    case 1:
                        if(inputs[0].toUpperCase().equals("FEMALE")){
                            gender = Worker.Gender.Female;
                        }
                        else {
                            if (GameMessage.isValidOptional(inputs[0].toUpperCase())) {
                                optional = inputs[0].toUpperCase();
                            } else {
                                if (!(inputs[0].toUpperCase().equals("MALE"))) {
                                    clientConnection.asyncSend("Input invalid!");
                                    return;
                                }
                                else{
                                    gender = Worker.Gender.Male;
                                }
                            }
                        }
                        break;
                    case 2:
                        x=Integer.parseInt(inputs[0]);
                        y=Integer.parseInt(inputs[1]);
                        break;
                    case 3:
                        x=Integer.parseInt(inputs[0]);
                        y=Integer.parseInt(inputs[1]);
                        if(inputs[2].toUpperCase().equals("FEMALE")){
                            gender = Worker.Gender.Female;
                        }
                        else{
                            if(GameMessage.isValidOptional(inputs[2].toUpperCase())){
                                optional = inputs[2].toUpperCase();
                            }
                            else{
                                if(!(inputs[2].toUpperCase().equals("MALE"))) {
                                    clientConnection.asyncSend("Input invalid!");
                                    return;
                                }
                                else {
                                    gender = Worker.Gender.Male;
                                }
                            }
                        }
                        break;
                    case 4:
                        x=Integer.parseInt(inputs[0]);
                        y=Integer.parseInt(inputs[1]);
                        if(inputs[2].toUpperCase().equals("FEMALE")){
                            gender = Worker.Gender.Female;
                        }
                        else{
                            if(!(inputs[2].toUpperCase().equals("MALE"))) {
                                clientConnection.asyncSend("Input invalid!");
                                return;
                            }
                            else {
                                gender = Worker.Gender.Male;
                            }
                        }
                        if(GameMessage.isValidOptional(inputs[3].toUpperCase())){
                            optional = inputs[3].toUpperCase();
                        }
                        else {
                            clientConnection.asyncSend("Input invalid!");
                            return;
                        }
                        break;
                    default:
                        clientConnection.asyncSend("Input invalid!");
                        return;

                }
                manageChoice(getPlayer(), x, y, optional, gender);
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
        int counterActivePlayers = 0;
        String nicknameThisPlayer = getPlayer().getNickname();
        String nicknamePlayerFromMessage = message.getPlayer().getNickname();
        Player[] players = message.getMatch().getPlayers();
        for (Player value : players) {
            if (value != null) {
                counterActivePlayers++;
            }
        }
        String resultMsg = "";

        boolean gameOverWin = message.getNextInstruction().equals(GameMessage.turnMessageWin);
        boolean gameOverDefeat = message.getNextInstruction().equals(GameMessage.turnMessageLose) || message.getNextInstruction().equals(GameMessage.turnMessageLoserNoWorker) ;

        if(message.getNextInstruction().equals(GameMessage.turnMessageFIRSTALLOCATION)){
            if(nicknameThisPlayer.equals(nicknamePlayerFromMessage)){

                resultMsg = GameMessage.turnMessageSelectFirstAllocation;
            }
            else{
                resultMsg = GameMessage.turnMessageWaitFirstAllocation;
            }
            showMessageSync(message.getMatch().getBoard());
            showMessageSync(resultMsg);
            return;
        }

        showMessageSync(message.getMatch().getBoard());
        if (gameOverWin) {
            if (nicknameThisPlayer.equals(nicknamePlayerFromMessage)) {
                resultMsg = GameMessage.turnMessageWin + "\n";
            } else {
                resultMsg = GameMessage.turnMessageLose + "\n";
            }
        }
        else {
            if (gameOverDefeat) {
                if (nicknameThisPlayer.equals(nicknamePlayerFromMessage)) {
                    resultMsg = message.getNextInstruction() + "\n";
                } else {
                    resultMsg = nicknamePlayerFromMessage +" "+ GameMessage.turnMessagePlayerLose + "\n";
                    if(counterActivePlayers<=2){ resultMsg += GameMessage.turnMessageWin + "\n";}
                    else {
                        if (nicknameThisPlayer.equals(message.getMatch().getPlayingNow().getNickname())){
                            showMessageSync(resultMsg);
                            resultMsg = GameMessage.turnMessageSelectYourWorker;
                        }
                    }
                }
            }
            else {
                if(message.getNextInstruction().equals(GameMessage.turnMessageFIRSTALLOCATIONEnded)){
                    if(nicknameThisPlayer.equals(nicknamePlayerFromMessage)) {
                        resultMsg = GameMessage.turnMessageSelectYourWorker;
                    }
                    else{
                        resultMsg = message.getNextInstruction() + GameMessage.turnMessageWaitAfterFirstAllocation;
                    }
                }
                if(nicknameThisPlayer.equals(nicknamePlayerFromMessage)){
                    resultMsg = message.getNextInstruction() + "\n"+ resultMsg;
                }
                else{
                    if(message.getNextInstruction().contains(GameMessage.turnMessageTurnEnd) && message.getMatch().getPlayingNow().getNickname().equals(nicknameThisPlayer)) {
                        resultMsg = GameMessage.turnMessageSelectYourWorker + "\n";
                    }
                }
            }
        }
        showMessageSync(resultMsg);

        if(gameOverWin || (gameOverDefeat && counterActivePlayers<3) || (gameOverDefeat && nicknameThisPlayer.equals(nicknamePlayerFromMessage) && counterActivePlayers==3)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    manageChoice(getPlayer(),-1,-1,GameMessage.removeObserver,null);
                }
            }).start();
            clientConnection.close(ClosingConnectionParameter.SINGLE);
        }
    }
}
