package org.rss.db.dao.jpa;

import javax.persistence.*;

/**
 * Created by Alain on 11/09/2016.
 */
@Entity
@Table(name="FEEDS_ATTRIBUTS")
public class FeedsAttributJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER,targetEntity = FeedsGenericJpa.class)
	private FeedsJpa feedsJpa;

	@Column(nullable = false)
	private boolean dejaLu;

	@ManyToOne
	private UserJpa userJpa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDejaLu() {
		return dejaLu;
	}

	public void setDejaLu(boolean dejaLu) {
		this.dejaLu = dejaLu;
	}

	public FeedsJpa getFeedsJpa() {
		return feedsJpa;
	}

	public void setFeedsJpa(FeedsJpa feedsJpa) {
		this.feedsJpa = feedsJpa;
	}

	public UserJpa getUserJpa() {
		return userJpa;
	}

	public void setUserJpa(UserJpa userJpa) {
		this.userJpa = userJpa;
	}

	@Override
	public String toString() {
		return "FeedsAttributJpa{" +
				"id=" + id +
				", feedsJpa=" + feedsJpa +
				", dejaLu=" + dejaLu +
				", userJpa=" + userJpa +
				'}';
	}
}
