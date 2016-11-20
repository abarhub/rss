package org.rss.ui.bean;

import java.util.List;

/**
 * Created by Alain on 31/10/2016.
 */
public class ListCategorieUi {

	private List<CategorieUi> categorieUiList;

	public List<CategorieUi> getCategorieUiList() {
		return categorieUiList;
	}

	public void setCategorieUiList(List<CategorieUi> categorieUiList) {
		this.categorieUiList = categorieUiList;
	}

	@Override
	public String toString() {
		return "ListCategorieUi{" +
				"categorieUiList=" + categorieUiList +
				'}';
	}
}
