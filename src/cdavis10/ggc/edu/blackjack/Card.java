package cdavis10.ggc.edu.blackjack;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class Card {	
	ArrayList<Integer> cardsList = new ArrayList<Integer>();	
	ArrayList<Integer> dealtCards = new ArrayList<Integer>();
	int value = 0;
	int card;
	
	public Card(){		
		cardsList.add(R.drawable.c1); 
		cardsList.add(R.drawable.d1);
		cardsList.add(R.drawable.h1);
		cardsList.add(R.drawable.s1);
		cardsList.add(R.drawable.c2);
		cardsList.add(R.drawable.d2);
		cardsList.add(R.drawable.h2);
		cardsList.add(R.drawable.s2);
		cardsList.add(R.drawable.c3);
		cardsList.add(R.drawable.d3);
		cardsList.add(R.drawable.h3);
		cardsList.add(R.drawable.s3);
		cardsList.add(R.drawable.c4);
		cardsList.add(R.drawable.d4);
		cardsList.add(R.drawable.h4);
		cardsList.add(R.drawable.s4);
		cardsList.add(R.drawable.c5);
		cardsList.add(R.drawable.d5);
		cardsList.add(R.drawable.h5);
		cardsList.add(R.drawable.s5);
		cardsList.add(R.drawable.c6);
		cardsList.add(R.drawable.d6);
		cardsList.add(R.drawable.h6);
		cardsList.add(R.drawable.s6);
		cardsList.add(R.drawable.c7);
		cardsList.add(R.drawable.d7);
		cardsList.add(R.drawable.h7);
		cardsList.add(R.drawable.s7);
		cardsList.add(R.drawable.c8);
		cardsList.add(R.drawable.d8);
		cardsList.add(R.drawable.h8);
		cardsList.add(R.drawable.s8);
		cardsList.add(R.drawable.c9);
		cardsList.add(R.drawable.d9);
		cardsList.add(R.drawable.h9);
		cardsList.add(R.drawable.s9);
		cardsList.add(R.drawable.c10);
		cardsList.add(R.drawable.d10);
		cardsList.add(R.drawable.h10);
		cardsList.add(R.drawable.s10);
		cardsList.add(R.drawable.jack_club);
		cardsList.add(R.drawable.jack_diamond);
		cardsList.add(R.drawable.jack_heart);
		cardsList.add(R.drawable.jack_spade);
		cardsList.add(R.drawable.queen_club);
		cardsList.add(R.drawable.queen_diamond);
		cardsList.add(R.drawable.queen_heart);
		cardsList.add(R.drawable.queen_spade);
		cardsList.add(R.drawable.king_club);
		cardsList.add(R.drawable.king_diamond);
		cardsList.add(R.drawable.king_heart);
		cardsList.add(R.drawable.king_spade);
	}
	
	public int dealCard(){
		
		try {
			Random rand = new Random();
			int r = rand.nextInt(51);
			card = cardsList.get(r);
			if (!dealtCards.contains(card)) {
				dealtCards.add(card);
			} else {
				r = rand.nextInt(51);
				card = cardsList.get(r);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			card = 0;
			System.out.println("DEAL CARD EXCEPTION: "+ e.getMessage());
		}
		return card;
	}
	
	public int getValue(int cardID){
		int thisCard = cardsList.indexOf(cardID);
		if (thisCard <= 3) {
			value = 0;		//<<-- value of the ACE will be determined in getScore()
		}if(thisCard > 3 && thisCard <= 7){
			value = 2;
		} if(thisCard > 7 && thisCard <= 11){
			value = 3;
		}if(thisCard > 11 && thisCard <= 15){
			value = 4;
		}if(thisCard >15 && thisCard <= 19){
			value = 5;
		}if(thisCard > 19 && thisCard <= 23){
			value = 6;
		}if(thisCard > 23 && thisCard <= 27){
			value = 7;
		}if(thisCard > 27 && thisCard <= 31){
			value = 8;
		}if(thisCard > 31 &&  thisCard <= 35){
			value = 9;
		}if(thisCard > 35){
			value = 10;
		}		
				System.out.println(" ThisCard: " + thisCard 
						+" value: " + value);
		
		return value;
	}
	
	
	public int getScore(ArrayList<Integer> hand) {
		int score = 0;
		int aceCount = 0;
		int handIndex = 0;

		if (hasBlackJack(hand)) {
			score = 21;
		} else {
			try {
				while (handIndex < cardsList.size()) {
					
					if (!isAce(hand.get(handIndex))) {
						score += getValue(hand.get(handIndex));// <-- if the card is not an ace, get the value.
					} else {
						aceCount++;
					}
					System.out.println("hand index: " + handIndex +" AceCount" + aceCount+ " score: " + score);
					handIndex ++;
					if(handIndex >= hand.size()){
						break;
					}
				}
				/*
				 * heres where I determine the value of an ace in the hand.
				 */
				if (aceCount > 0) {
					System.out.println("we have an ace! "+ aceCount);
					if(aceCount > 1 && hand.size() >= 2){
						score += aceCount;
						System.out.println(" more than one ace, handsize >2, score =:" + score);
					}else if(aceCount == 1){
						System.out.println(" one ace, calculating score");
						if(score <= 10){
							score+= 11;
							System.out.println(" ace = eleven, score:"+ score);
						}else{
							score += 1;
							System.out.println("ace = one, score: ");
						}
					}
					
				}

			} catch (Exception e) {
				System.out.println("SCORE EXCEPTION: " + e.getMessage());
			}
		}
		return score;
	}

	/*
	 * is a given card an ace?
	 */
	public boolean isAce(int CardID){
		int thisCard = cardsList.indexOf(CardID);
		if(thisCard <= 3){
			return true;
		}else{
			return false;
		}
		
	}
	
	/*
	 * is a given card a face?
	 */
	public boolean isFace(int CardID){
		int thisCard = cardsList.indexOf(CardID);
		if(thisCard > 39){
			return true;
		}else{
			return false;
		}
		
	}
	
	/*
	 * Does the hand have an ace in it?
	 */
	public boolean hasAce(ArrayList<Integer> hand){
		
		int i = 0;
		boolean hasAce = false;
		try {
			while(!hasAce && i < hand.size()){
				if(isAce(hand.get(i))){
					hasAce = true;
				}
				i++;
			}
		} catch (Exception e) {
			System.out.println("hasAce EXCEPTION MESSAGE: " + e.getMessage());
		}
		
		if(hasAce == true){
			return true;
		}else{
			return false;
		}
		
	}

	public boolean hasFace(ArrayList<Integer> hand){
		
		int i = 0;
		boolean hasFace = false;
		
		try {
			while(!hasFace && i < hand.size()){
				
				if(isFace(hand.get(i))){
					hasFace = true;
				}
				i++;
			}
		} catch (Exception e) {
			System.out.println("hasFace EXCEPTION MESSAGE: " + e.getMessage());
		}
		
		if(hasFace == true){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean hasBlackJack(ArrayList<Integer> hand){
		
		if(hasAce(hand) && hasFace(hand)){
			return true;
		}else{
			return false;
		}
		
		}
	
	public void newDeck(){
		dealtCards.clear();
	}

}
