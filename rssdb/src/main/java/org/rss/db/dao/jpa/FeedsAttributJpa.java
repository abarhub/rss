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
	private CategorieJpa categorieJpa;

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

	public CategorieJpa getCategorieJpa() {
		return categorieJpa;
	}

	public void setCategorieJpa(CategorieJpa categorieJpa) {
		this.categorieJpa = categorieJpa;
	}

	@Override
	public String toString() {
		return "FeedsAttributJpa{" +
				"id=" + id +
				", feedsJpa=" + feedsJpa +
				", dejaLu=" + dejaLu +
				", categorieJpa=" + categorieJpa +
				'}';
	}
}
