package st.geekli.api.type;

public class Social implements GeeklistType {

	private String twitterScreenName;
	private Integer twitterFriendsCount, twitterFollowersCount;
	
	public String getTwitterScreenName() {
		return twitterScreenName;
	}
	
	public void setTwitterScreenName(String twitterScreenName) {
		this.twitterScreenName = twitterScreenName;
	}
	
	public int getTwitterFriendsCount() {
		return twitterFriendsCount;
	}
	
	public void setTwitterFriendsCount(int twitterFriendsCount) {
		this.twitterFriendsCount = twitterFriendsCount;
	}
	
	public int getTwitterFollowersCount() {
		return twitterFollowersCount;
	}
	
	public void setTwitterFollowersCount(int twitterFollowersCount) {
		this.twitterFollowersCount = twitterFollowersCount;
	}
}
