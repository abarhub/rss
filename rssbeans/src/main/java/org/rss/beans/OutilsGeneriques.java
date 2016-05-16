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

}
