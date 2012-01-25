package st.geekli.api.type;

public class Stats implements GeeklistType {

	private Integer numberOfContributions, numberOfHighfives, numberOfMentions, numberOfCards, numberOfPings;

	public int getNumberOfContributions() {
		return numberOfContributions;
	}

	public void setNumberOfContributions(int numberOfContributions) {
		this.numberOfContributions = numberOfContributions;
	}

	public int getNumberOfHighfives() {
		return numberOfHighfives;
	}

	public void setNumberOfHighfives(int numberOfHighfives) {
		this.numberOfHighfives = numberOfHighfives;
	}

	public int getNumberOfMentions() {
		return numberOfMentions;
	}

	public void setNumberOfMentions(int numberOfMentions) {
		this.numberOfMentions = numberOfMentions;
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	public void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	public int getNumberOfPings() {
		return numberOfPings;
	}

	public void setNumberOfPings(int numberOfPings) {
		this.numberOfPings = numberOfPings;
	}
}
