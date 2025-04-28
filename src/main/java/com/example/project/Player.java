package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<Card>();
        allCards = new ArrayList<Card>(); //this originally not here
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public void addallCard(Card c){
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){  
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();

        if (isRoyalFlush()) {
            return "Royal Flush";
        }
        if (isStraightFLush()) {
            return "Straight Flush";
        }
        if (isFourofaKind()) {
            return "Four of a Kind";
        }
        if (isFullHouse()) {
            return "Full House";
        }
        if (isFlush()) {
            return "Flush";
        }
        if (isStraight()) {
            return "Straight";
        }
        if (isThreeofaKind()) {
            return "Three of a Kind";
        }
        if (isTwoPair()) {
            return "Two Pair";
        }
        if (isPair()) {
            return "A Pair";
        }
        if (allCards.get(4).getRank() != communityCards.get(0).getRank() && allCards.get(4).getRank() != communityCards.get(1).getRank() && allCards.get(4).getRank() != communityCards.get(2).getRank()) { 
            return "High Card"; //if the highest doesn't match any of the community cards
        }
        return "Nothing";
    }

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> frequencyArr = new ArrayList<Integer>(); //arrayList for the frequency of rankings

        for (int i = 0; i < 13; i++) { //makes each index 0
            frequencyArr.add(0);
        }

        for (int j = 0; j < 5; j++) { //loops through the hand
            for (int k = 0; k < 13; k++) { //loops through the rank list, searching for a match
                if (allCards.get(j).getRank().equals(ranks[k])) {
                    frequencyArr.set(k, frequencyArr.get(k) + 1);
            }
        }
    }
    return frequencyArr;
    }
    
    public ArrayList<Integer> findSuitFrequency(){

        ArrayList<Integer> frequencyArr = new ArrayList<Integer>(); //arrayList for the frequency of rankings

        for (int i = 0; i < 13; i++) { //Initualizes each index to 0
            frequencyArr.add(0);
        }

        for (int j = 0; j < 5; j++) { //loops through the hand
            for (int k = 0; k < 13; k++) { //loops through the suit list, searching for a match
                if (allCards.get(j).getSuit().equals(suits[k])) {
                    frequencyArr.set(k, frequencyArr.get(k) + 1);
            }
        }
    }
    return frequencyArr;
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }



    public boolean isRoyalFlush() {
        sortAllCards();
        if (Utility.getRankValue(allCards.get(0).getRank()) == 10 && isFlush()) {
            return true;
        } else {
        return false;
        }
    }

    public boolean isStraightFLush() {
        if (isFlush() && isStraight()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFourofaKind() { //same as three of a kind, except looking for 4
            ArrayList<Integer> rankings = new ArrayList<Integer>();
            rankings = findRankingFrequency();
            for (int i = 0; i < rankings.size(); i++) {
                if (rankings.get(i) == 4) {
                    return true;
                }
            }
            return false;
        }

    public boolean isFullHouse() {
        if (isThreeofaKind() && isPair()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFlush() {
        String matchingSuit = allCards.get(0).getSuit(); //all cards have to be the same suit, so get the any of them
        for (int i = 1; i < 5; i++) {
            if (!allCards.get(i).getSuit().equals(matchingSuit)) { //if any don't match the suit, return false
                return false;
            }
        }
        return true;
    }

    
    public boolean isStraight() {
        sortAllCards();
        for (int i = 0; i < allCards.size() - 1; i++) {
            if (Utility.getRankValue(allCards.get(i).getRank()) + 1 != Utility.getRankValue(allCards.get(i + 1).getRank())) {
                return false;
            }
        }
        return true;
    }

    
    public boolean isThreeofaKind() {
        ArrayList<Integer> rankings = new ArrayList<Integer>();
        rankings = findRankingFrequency();
        for (int i = 0; i < rankings.size(); i++) { //goes through the entire frequency list, and if it equals 3, return true
            if (rankings.get(i) == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isTwoPair() {
        ArrayList<Integer> rankings = new ArrayList<Integer>();
        rankings = findRankingFrequency();
        int count = 0;
        for (int i = 0; i < rankings.size(); i++) {
            if (rankings.get(i) == 2) { //counts up all the times 2 appears in the list
                count++;
                rankings.remove(i);
                i--; //compensates for shift due to removal
            }
        }

        if (count == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPair() { //same as three of a pair. but looking for 2
        ArrayList<Integer> rankings = new ArrayList<Integer>();
        rankings = findRankingFrequency();
        for (int i = 0; i < rankings.size(); i++) {
            if (rankings.get(i) == 2) {
                return true;
            }
        }
        return false;
    }

    public void sortAllCards() {
        for (int i = 0; i < allCards.size(); i++) {
            int min_index = i;
            for (int j = i + 1; j < allCards.size(); j++) {
                int x = Utility.getRankValue(allCards.get(min_index).getRank());
                int y = Utility.getRankValue(allCards.get(j).getRank());
                if (x > y) {
                    min_index = j;
                }
            }
            if (min_index != i) {
                // Swap
                Card temp = allCards.get(i);
                allCards.set(i, allCards.get(min_index));
                allCards.set(min_index, temp);
            }
        }
    }
}
