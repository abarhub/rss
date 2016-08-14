package org.rss.beans;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alain on 16/05/2016.
 */
public class OutilsGeneriquesTest {
	@Test
	public void vide() throws Exception {
		assertTrue(OutilsGeneriques.vide(""));
		assertTrue(OutilsGeneriques.vide(null));
		assertFalse(OutilsGeneriques.vide("abc"));
		assertFalse(OutilsGeneriques.vide("abc ddf"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkNotEmptyKO() throws Exception {
		OutilsGeneriques.checkNotEmpty("","Erreur");
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkNotEmptyKONull() throws Exception {
		OutilsGeneriques.checkNotEmpty(null,"Erreur");
	}

	@Test
	public void checkNotEmptyOK() throws Exception {
		OutilsGeneriques.checkNotEmpty("abc","Erreur");
	}

}