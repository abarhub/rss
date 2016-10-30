package org.rss.read.nettoyage;

import org.rss.beans.flux.DateTimeZone;

/**
 * Created by Alain on 30/10/2016.
 */
public class Elt {

	private String guid;
	private DateTimeZone derniereMaj;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public DateTimeZone getDerniereMaj() {
		return derniereMaj;
	}

	public void setDerniereMaj(DateTimeZone derniereMaj) {
		this.derniereMaj = derniereMaj;
	}

	@Override
	public String toString() {
		return "Elt{" +
				"guid='" + guid + '\'' +
				", derniereMaj=" + derniereMaj +
				'}';
	}
}
