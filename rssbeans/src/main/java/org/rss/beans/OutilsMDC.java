package org.rss.beans;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.MDC;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.rss.beans.OutilsGeneriques.checkNotEmpty;

/**
 * Created by Alain on 14/05/2016.
 */
public final class OutilsMDC {

	public static final String CONTEXT_REGROUPE = "CONTEXT";

	private OutilsMDC() {
	}


	public static void addMdc(MDCKey key,String value){
		checkNotNull(key,"Cle vide");
		OutilsGeneriques.checkNotEmpty(value,"Valeur vide");
		MDC.put(key.getKeyName(),value);
		recalculMdc();
	}

	private static void recalculMdc() {
		Map<String, String> mdc2 = Maps.newTreeMap();
		Map<String, String> mdc = MDC.getCopyOfContextMap();
		if (mdc != null) {
			mdc2.putAll(mdc);
		}
		mdc2.remove(CONTEXT_REGROUPE);
		String res = "";
		if (!mdc2.isEmpty()) {
			Joiner.MapJoiner joiner = Joiner.on(";").withKeyValueSeparator("=");
			res = joiner.join(mdc2);
		}
		MDC.put(CONTEXT_REGROUPE, res);
	}

	public static void removeMdc(MDCKey key) {
		checkNotNull(key, "Cle vide");
		MDC.remove(key.getKeyName());
		recalculMdc();
	}

	public static Map<String,String> getCopyMDC(){
		return ImmutableMap.copyOf(MDC.getCopyOfContextMap());
	}
}
