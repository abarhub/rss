package org.rss.read.services;

import com.google.common.collect.Lists;
import org.rss.read.ResultatRss;
import org.rss.read.domrrs.ChannelRss;
import org.rss.read.domrrs.ItemRss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Alain on 18/10/2015.
 */
@Service
public class ParseRss {

	public static final Logger LOGGER = LoggerFactory.getLogger(ParseRss.class);

	public ParseRss() {
	}

	public ResultatRss read(String contenuRss){
		ResultatRss res;

		res=new ResultatRss();

		try {
			parseRss(contenuRss,res);
		} catch (ParserConfigurationException | IOException | SAXException e) {
			LOGGER.error("Error:"+e.getLocalizedMessage(),e);
			//LOGGER.error("flux en erreur :"+contenuRss);
			res.addError("Error:"+e.getLocalizedMessage());
		}

		return res;
	}

	private void parseRss(String buf,ResultatRss res) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(buf)));
		String root,channel;
		ChannelRss header;

		header=null;
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		root=doc.getDocumentElement().getNodeName();
		if(root==null||!root.equals("rss"))
		{
			res.addError("la balise racine xml n'est pas correcte !");
		}

		if(!res.isError()) {
			NodeList nList = doc.getElementsByTagName("channel");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				channel=nNode.getNodeName();
				if(channel==null||!channel.equals("channel"))
				{
					res.addError("la balise channel xml n'est pas correcte !");
				}
				else if(header!=null) {
					res.addError("la balise channel xml est en plusieurs exemplaires !");
				}
				else {

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						String titre,link,description,language,lastBuild,pubDate0;

						titre=donneNode(eElement,"title");
						link=donneNode(eElement,"link");
						description=donneNode(eElement,"description");
						language=donneNode(eElement,"language");
						lastBuild=donneNode(eElement,"lastBuildDate");
						pubDate0=donneNode(eElement,"pubDate");

						header=new ChannelRss();
						header.setTitle(titre);
						header.setLink(link);
						header.setDescription(description);
						header.setLanguage(language);
						header.setLastBuildDate(lastBuild);
						header.setPubDate(pubDate0);
						header.setListItem(Lists.newArrayList());
						res.setRes(header);

						NodeList nList2 = eElement.getElementsByTagName("item");

						for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
							Node nNode2 = nList2.item(temp2);

							if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

								Element eElement2 = (Element) nNode2;

								String desc, pubDate, guid,lien;
								ItemRss item;

								desc = donneNode(eElement2, "description");
								pubDate = donneNode(eElement2, "pubDate");
								guid = donneNode(eElement2, "guid");
								titre=donneNode(eElement2,"title");
								lien=donneNode(eElement2,"link");

								item = new ItemRss();
								item.setDescription(desc);
								item.setPubDate(pubDate);
								item.setGuid(guid);
								item.setTitle(titre);
								item.setLink(lien);

								header.getListItem().add(item);

								LOGGER.info("ajout de "+item);
							}
						}
					}
				}
				if(res.isError())
					break;
			}
		}

	}

	private String donneNode(Element eElement, String nom) {
		String s=null;
		if(eElement!=null) {
			NodeList tmp = eElement.getElementsByTagName(nom);
			if(tmp!=null&&tmp.getLength()>0) {
				s = tmp.item(0).getTextContent();
			}
		}
		if(s==null)
			s="";
		return s;
	}
}
