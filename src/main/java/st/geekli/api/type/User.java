package st.geekli.api.type;

public class User implements GeeklistType {

	private String id, name, screenName, blogLink, location, bio, createdAt, updatedAt, activeAt, trendingAt, email;
	private Stats stats;
	private Social social;
	private Avatar avatar;
	private Company company;
	private Boolean isBeta;
	private Boolean isFeatured;
	private Criteria criteria;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getBlogLink() {
		return blogLink;
	}

	public void setBlogLink(String blogLink) {
		this.blogLink = blogLink;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Social getSocial() {
		return social;
	}

	public void setSocial(Social social) {
		this.social = social;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getActiveAt() {
		return activeAt;
	}

	public void setActiveAt(String activeAt) {
		this.activeAt = activeAt;
	}

	public String getTrendingAt() {
		return trendingAt;
	}

	public void setTrendingAt(String trendingAt) {
		this.trendingAt = trendingAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getIsBeta() {
		return isBeta;
	}

	public void setIsBeta(Boolean isBeta) {
		this.isBeta = isBeta;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
}
