package org.rss.beans;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Created by Alain on 14/05/2016.
 */
public final class OutilsGeneriques {

	private static final Logger logger = LoggerFactory.getLogger(OutilsGeneriques.class);

	private static final String CONTEXT_REGROUPE="CONTEXT";

	private OutilsGeneriques(){
		// constructeur vide
	}

	public static boolean vide(String s){
		return s==null||s.length()==0;
	}

	public static void checkNotEmpty(String s,String msg){
		if(vide(s))
		{
			throw new IllegalArgumentException(msg);
		}
	}

	public static void addMdc(String key,String value){
		checkNotEmpty(key,"Cle vide");
		checkNotEmpty(value,"Valeur vide");
		MDC.put(key,value);
		recalculMdc();
	}

	private static void recalculMdc() {
		Map<String, String> mdc2= Maps.newTreeMap();
		Map<String, String> mdc = MDC.getCopyOfContextMap();
		if(mdc!=null)
		{
			mdc2.putAll(mdc);
		}
		mdc2.remove(CONTEXT_REGROUPE);
		String res="";
		if(mdc2.isEmpty()) {
			Joiner.MapJoiner joiner = Joiner.on(";").withKeyValueSeparator("=");
			res = joiner.join(mdc2);
		}
		MDC.put(CONTEXT_REGROUPE,res);
	}

	public static void removeMdc(String key){
		checkNotEmpty(key,"Cle vide");
		MDC.remove(key);
		recalculMdc();
	}
}
