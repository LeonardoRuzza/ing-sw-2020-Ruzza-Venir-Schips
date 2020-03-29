package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.*;

public class FactoryPlayer {
    public static Player getPlayer(String nickname, int number, Match match, Worker.Color color, int cardNum){
        switch(cardNum){
            case 0:
                return new PlayerApollo(nickname,number,new Card(cardNum),match,color);
            case 1:
                return new PlayerArtemis(nickname,number,new Card(cardNum),match,color);
            case 2:
                return new PlayerAthena(nickname,number,new Card(cardNum),match,color);
            case 3:
                return new PlayerAtlas(nickname,number,new Card(cardNum),match,color);
            case 4:
                return new PlayerDemeter(nickname,number,new Card(cardNum),match,color);
            case 5:
                return new PlayerHephaesthus(nickname,number,new Card(cardNum),match,color);
            case 6:
                return new PlayerMinotaur(nickname,number,new Card(cardNum),match,color);
            case 7:
                return new PlayerPan(nickname,number,new Card(cardNum),match,color);
            case 8:
                return new PlayerPrometheus(nickname,number,new Card(cardNum),match,color);
            case 9:
                return new PlayerAres(nickname,number,new Card(cardNum),match,color);
            case 10:
                return new PlayerChronus(nickname,number,new Card(cardNum),match,color);
            case 11:
                return new PlayerHera(nickname,number,new Card(cardNum),match,color);
            case 12:
                return new PlayerHestia(nickname,number,new Card(cardNum),match,color);
            case 13:
                return new PlayerHypnus(nickname,number,new Card(cardNum),match,color);
            default:
                return null;
        }
    }
}
