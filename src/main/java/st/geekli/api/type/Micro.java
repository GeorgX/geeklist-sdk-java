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

import java.util.Date;

public class Micro implements GeeklistType {

	private ShortCode shortCode;
	private String[] mentions;
	private Boolean isTrending, isActive;
	private String status, slug, permalink, id, authorId;
	private MicroReply reply;
	private Date createdAt, updatedAt, trendingAt;
	
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getTrendingAt() {
		return trendingAt;
	}

	public void setTrendingAt(Date trendingAt) {
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
