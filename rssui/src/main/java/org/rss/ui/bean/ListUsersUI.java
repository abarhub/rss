package org.rss.ui.bean;

import java.util.List;

/**
 * Created by Alain on 02/10/2016.
 */
public class ListUsersUI {

	List<UserUI> listUserUI;

	public List<UserUI> getListUserUI() {
		return listUserUI;
	}

	public void setListUserUI(List<UserUI> listUserUI) {
		this.listUserUI = listUserUI;
	}

	@Override
	public String toString() {
		return "ListUsersUI{" +
				"listUserUI=" + listUserUI +
				'}';
	}
}
