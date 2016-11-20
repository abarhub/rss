package org.rss.read.nettoyage;

import org.rss.read.domrrs.ChannelRss;
import org.rss.read.services.OutilsRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alain on 30/10/2016.
 */
@Service
public class SupprimeElementDejaPresent {

	@Autowired
	private OutilsRead outilsRead;

	private Map<String,ElementDejaEnvoye> map;

	@PostConstruct
	public void init(){
		map=new ConcurrentHashMap<>();
	}

	public ChannelRss supprime(String url, ChannelRss channel) {
		ElementDejaEnvoye elementDejaEnvoye;
		if(map.containsKey(url)){
			elementDejaEnvoye=map.get(url);
			elementDejaEnvoye.ajoute(channel);
		} else {
			elementDejaEnvoye=new ElementDejaEnvoye(outilsRead);
			elementDejaEnvoye.ajoute(channel);
			map.put(url,elementDejaEnvoye);
		}
		return channel;
	}

	public void clear() {
		map.clear();
	}
}
