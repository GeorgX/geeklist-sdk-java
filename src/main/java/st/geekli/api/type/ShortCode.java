package st.geekli.api.type;

public class ShortCode implements GeeklistType {

	private String gklstUrl, id;

	public String getGklstUrl() {
		return gklstUrl;
	}

	public void setGklstUrl(String gklstUrl) {
		this.gklstUrl = gklstUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
