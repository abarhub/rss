package org.rss.read.nettoyage;

import com.google.common.base.Preconditions;
import org.rss.beans.OutilsGeneriques;
import org.rss.read.domrrs.ChannelRss;
import org.rss.read.domrrs.ItemRss;
import org.rss.read.services.OutilsRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alain on 30/10/2016.
 */
public class ElementDejaEnvoye {

	private OutilsRead outilsRead;

	private List<Elt> liste=new ArrayList<>();

	public ElementDejaEnvoye(OutilsRead outilsRead) {
		Preconditions.checkNotNull(outilsRead);
		this.outilsRead = outilsRead;
	}

	public void ajoute(ChannelRss channel) {
		Preconditions.checkNotNull(channel);
		if(!CollectionUtils.isEmpty(channel.getListItem())) {
			Iterator<ItemRss> iter=channel.getListItem().iterator();
			while(iter.hasNext()){
				ItemRss itemRss=iter.next();
				Preconditions.checkNotNull(itemRss.getGuid());
				Preconditions.checkArgument(!StringUtils.isEmpty(itemRss.getGuid()));
				Elt elt=new Elt();
				elt.setGuid(itemRss.getGuid());
				elt.setDerniereMaj(outilsRead.convDate(itemRss.getPubDate()));

				Elt trouve=null;
				for(Elt e:liste){
					if(isGuidEquals(e,elt)){
						trouve=e;
						break;
					}
				}

				if(trouve==null){
					// element non présent => on le garde
					liste.add(elt);
				} else {
					if (elt.getDerniereMaj() == null) {
						// element déja présent => on le supprime
						iter.remove();
					} else if (trouve.getDerniereMaj() == null
							|| OutilsGeneriques.estInf(trouve.getDerniereMaj(), elt.getDerniereMaj())) {
						// element plus récent => on le garde
						trouve.setDerniereMaj(elt.getDerniereMaj());
					} else {
						// element déja présent => on le supprime
						iter.remove();
					}
				}
			}
		}
	}

	private boolean isGuidEquals(Elt elt, Elt e) {
		return e.getGuid().equals(elt.getGuid());
	}
}
