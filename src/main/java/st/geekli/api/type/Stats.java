/*
 * Copyright (C) 2012 Stefan Hoth, Sebastian Mauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
