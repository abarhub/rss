package org.rss.beans;

/**
 * Created by Alain on 14/05/2016.
 */
public enum MDCKey {
	SERVEUR("serveur"),GET_URL("getUrl");

	private MDCKey(String keyName){
		OutilsGeneriques.checkNotEmpty(keyName,"Nom de cle vide");
		this.keyName=keyName;
	}

	private final String keyName;

	public String getKeyName() {
		return keyName;
	}
}
