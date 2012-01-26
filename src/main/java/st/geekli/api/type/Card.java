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

public class Card implements GeeklistType {

	private String id, authorId, happenedAtType, headline, permalink, slug;
	private Boolean isActive;
	private CardStats stats;
	private ShortCode shortCode;
	private Date createdAt, updatedAt, happenedAt;
	
	public String getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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

	public Date getHappenedAt() {
		return happenedAt;
	}

	public void setHappenedAt(Date happenedAt) {
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
