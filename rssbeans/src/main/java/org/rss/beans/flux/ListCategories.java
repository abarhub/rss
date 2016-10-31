package org.rss.beans.flux;

import java.util.List;

/**
 * Created by Alain on 31/10/2016.
 */
public class ListCategories {

	private List<Categorie> categorieList;

	public List<Categorie> getCategorieList() {
		return categorieList;
	}

	public void setCategorieList(List<Categorie> categorieList) {
		this.categorieList = categorieList;
	}

	@Override
	public String toString() {
		return "ListCategories{" +
				"categorieList=" + categorieList +
				'}';
	}
}
