package st.geekli.api.type;

public class Criteria implements GeeklistType {

	private String[] lookingFor;
	private String[] availableFor;
	
	public String[] getLookingFor() {
		return lookingFor;
	}
	
	public void setLookingFor(String[] lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String[] getAvailableFor() {
		return availableFor;
	}

	public void setAvailableFor(String[] availableFor) {
		this.availableFor = availableFor;
	}
}
