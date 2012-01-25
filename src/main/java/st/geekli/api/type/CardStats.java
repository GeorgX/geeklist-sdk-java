package st.geekli.api.type;

public class CardStats implements GeeklistType {

	private Integer numberOfViews, views, highfives;

	public Integer getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Integer numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getHighfives() {
		return highfives;
	}

	public void setHighfives(Integer highfives) {
		this.highfives = highfives;
	}
}
