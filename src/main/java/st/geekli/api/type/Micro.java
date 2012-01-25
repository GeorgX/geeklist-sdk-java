package st.geekli.api.type;

public class Micro implements GeeklistType {

	private ShortCode shortCode;
	private String[] mentions;
	private Boolean isTrending, isActive;
	private String createdAt, updatedAt, trendingAt, status, slug, permalink, id, authorId;
	private MicroReply reply;
	
	public ShortCode getShortCode() {
		return shortCode;
	}
	
	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}
	
	public String[] getMentions() {
		return mentions;
	}
	
	public void setMentions(String[] mentions) {
		this.mentions = mentions;
	}

	public Boolean getIsTrending() {
		return isTrending;
	}

	public void setIsTrending(Boolean isTrending) {
		this.isTrending = isTrending;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getTrendingAt() {
		return trendingAt;
	}

	public void setTrendingAt(String trendingAt) {
		this.trendingAt = trendingAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public MicroReply getReply() {
		return reply;
	}

	public void setReply(MicroReply reply) {
		this.reply = reply;
	}
	
}
