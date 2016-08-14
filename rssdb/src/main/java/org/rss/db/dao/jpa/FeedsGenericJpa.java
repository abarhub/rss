package org.rss.db.dao.jpa;

import javax.persistence.*;

/**
 * Created by Alain on 13/02/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.INTEGER)
@Table(name="FEEDS")
public abstract class FeedsGenericJpa implements FeedsJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;


	@Column(nullable = false,length = 1000)
	private String url;

	@Column//(nullable = false)
	private String title;

	@Column(nullable = false,length = 5000)
	private String description;

	@Column(nullable = true,length = 5000)
	private String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
