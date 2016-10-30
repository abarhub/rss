package org.rss.beans.metier;

import java.util.List;

/**
 * Created by Alain on 02/10/2016.
 */
public class SearchUsersResponseDTO {

	private List<UserDTO> listUserDTO;

	public List<UserDTO> getListUserDTO() {
		return listUserDTO;
	}

	public void setListUserDTO(List<UserDTO> listUserDTO) {
		this.listUserDTO = listUserDTO;
	}

	@Override
	public String toString() {
		return "SearchUsersResponseDTO{" +
				"listUserDTO=" + listUserDTO +
				'}';
	}
}
