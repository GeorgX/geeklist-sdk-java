package st.geekli.api.type;

public class Card implements GeeklistType {

	private String id, authorId, createdAt, updatedAt, happenedAt, happenedAtType, headline, permalink, slug;
	private Boolean isActive;
	private CardStats stats;
	private ShortCode shortCode;
	
	public String getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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

	public String getHappenedAt() {
		return happenedAt;
	}

	public void setHappenedAt(String happenedAt) {
		this.happenedAt = happenedAt;
	}

	public String getHappenedAtType() {
		return happenedAtType;
	}

	public void setHappenedAtType(String happenedAtType) {
		this.happenedAtType = happenedAtType;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public CardStats getStats() {
		return stats;
	}

	public void setStats(CardStats stats) {
		this.stats = stats;
	}

	public ShortCode getShortCode() {
		return shortCode;
	}

	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
