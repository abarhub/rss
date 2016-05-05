package org.rss.read;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.rss.read.domrrs.ChannelRss;

import java.util.List;

/**
 * Created by Alain on 21/03/2015.
 */
public class ResultatRss {

	private List<String> errors;
	private ChannelRss res;

	public ResultatRss() {
		errors= Lists.newArrayList();
	}

	public boolean isError()
	{
		return !errors.isEmpty();
	}

	public void addError(String msg) {
		errors.add(msg);
	}

	public ChannelRss getRes() {
		return res;
	}

	public void setRes(ChannelRss res) {
		this.res = res;
	}

	public List<String> getErrors() {
		return ImmutableList.copyOf(errors);
	}

	public String getError() {
		return errors.get(0);
	}
}
