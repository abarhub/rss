package org.rss.read.services;

import com.google.common.collect.Lists;
import org.rss.beans.param.RssUrl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Alain on 25/10/2015.
 */
@Component
public class ParamUrl {

	private final List<RssUrl> liste_url;

	public ParamUrl() {
		liste_url= Lists.newArrayList();
	}

	public void setListUrl(List<RssUrl> liste_url0)
	{
		liste_url.clear();
		if(liste_url0!=null&&!liste_url0.isEmpty())
		{
			for(RssUrl u:liste_url0)
			{
				liste_url.add(u);
			}
		}
	}

	public List<RssUrl> getListUrl(){
		return Lists.newArrayList(liste_url);
	}
}
