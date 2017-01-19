package model;

import java.io.Serializable;

import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class PlayingCardImpl implements PlayingCard, Serializable {
	private Suit suit;
	private Value value;
	
	public PlayingCardImpl(Suit suit, Value value){
		this.suit = suit;
		this.value = value;
	}
	
	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public int getScore() {
		switch(this.value)
		{
			case Ace:
				return 1;
			case Two:
				return 2;
			case Three:
				return 3;
			case Four:
				return 4;
			case Five:
				return 5;
			case Six:
				return 6;
			case Seven:
				return 7;
			case Eight:
				return 8;
			case Nine:
				return 9;
			case Ten:
			case Jack:
			case Queen:
			case King:
				return 10;
			default:
				return -1;
		}
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		
		result.append(this.getSuit() + " " + this.getValue() + " worth " + this.getScore());
		
		return result.toString();
	}
}
