package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private int number;
    private Card card;
    protected static Card[] deck;


    private Match match;
    private Worker[] workers = new Worker[2];
    private Worker selectedWorker;

//Builder
    public Player(String nickname, int number, Match match) {
        this.nickname = nickname;
        this.number = number;
        this.match = match;
    }

    public Player(String nickname, int number, Card card, Match match, Worker.Color color){ //secondo costruttore che sarà utilizzato dalle sottoclassi per la loro medesima istanziazione.
        this(nickname, number, match);
        this.card = card;
        workers[0] = new Worker(Worker.Gender.Male, color);
        workers[1] = new Worker(Worker.Gender.Female, color);
    }

//Getter and Setter
    public String getNickname() {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    //per la GUI
    protected void setSelectedWorker(Worker worker){ selectedWorker=worker;}

    //per la CLI
    protected void setSelectedWorker(Worker.Gender selectedGender){
        switch (selectedGender){
            case Male:
                selectedWorker=workers[0];
                break;
            case Female:
                selectedWorker=workers[1];
                break;
            default:
                break;
        }
    }

//Other Methods
    private void addToDeck(int[] numbersOfCards){  // Chiamato quando tutti i giocatori hanno loggato la partita e sono stati creati. Prima dell'inizio della partita vera e propria in pratica
        deck = new Card[match.getNumberOfPlayers];
        for(int i=0; i< match.getNumberOfPlayers; i++){
            deck[i] = new Card(numbersOfCards[i]);
        }
    }


    public void SelectedWorkerMove(int x, int y){           //probabilmente serve aggiungere un tipo di ritorno almeno boolean per confermare o meno lo spostamento
        Worker tempWorker;
        tempWorker=match.checkMove(x,y,selectedWorker);
        if(tempWorker.equals(selectedWorker)){
            match.forceMove(x,y,selectedWorker);
        }
    }

    public void SelectedWorkerBuild(int x, int y){          //probabilmente serve aggiungere un tipo di ritorno almeno boolean per confermare o meno la costruzione
        Block tempBlock;
        tempBlock=match.checkBuild(x,y,selectedWorker);
        if(tempBlock != null){
            match.forceBuild(x,y,selectedWorker);
        }


    }
}
