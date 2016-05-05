package org.rss.read;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.Ignore;
import org.junit.Test;
import org.rss.read.domrrs.ChannelRss;

import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Created by Alain on 09/05/2016.
 */
public class ParseRssTest {

	@Test
	public void read1() throws Exception {
		ParseRss parse;
		parse=new ParseRss();
		//content= Files.toString("", Charset.forName("UTF-8"));
		URL url = Resources.getResource("fluxRss1.xml");
		String content = Resources.toString(url, Charsets.UTF_8);
		assertNotNull(content);
		assertTrue(content.length()>0);
		ResultatRss resultat = parse.read(content);
		assertNotNull(resultat);
		assertFalse(resultat.isError());
		assertNotNull(resultat.getRes());
		ChannelRss res = resultat.getRes();
		assertNotNull(res);
		assertEquals("Scripting News",res.getTitle());
		assertEquals("http://www.scripting.com/",res.getLink());
		assertEquals("A weblog about scripting and stuff like that.",res.getDescription());
		assertEquals(9,res.getListItem().size());
	}

	@Test
	@Ignore("TODO: a corriger (fichier fluxRss2.xml)")
	public void read2() throws Exception {
		ParseRss parse;
		parse=new ParseRss();
		//content= Files.toString("", Charset.forName("UTF-8"));
		URL url = Resources.getResource("fluxRss2.xml");
		String content = Resources.toString(url, Charsets.UTF_8);
		assertNotNull(content);
		assertTrue(content.length()>0);
		ResultatRss resultat = parse.read(content);
		assertNotNull(resultat);
		assertFalse(resultat.isError());
		assertNotNull(resultat.getRes());
		ChannelRss res = resultat.getRes();
		assertNotNull(res);
		assertEquals("ActusnewsWire : Diffuseur d'informations réglementées",res.getTitle());
		assertEquals("http://www.actusnews.com/",res.getLink());
		assertEquals("Les communiqués des 15 derniers jours sur Actusnews.com.",res.getDescription());
		assertEquals(20,res.getListItem().size());
	}

}