package org.rss.beans;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.rss.beans.flux.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Created by Alain on 14/05/2016.
 */
public final class OutilsGeneriques {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutilsGeneriques.class);

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

	public static ZonedDateTime getDate(int annee, int mois, int jour,
	                              int heure, int minutes, int secondes,
	                              String zone, int decalage){
		ZonedDateTime zonedDateTime;

		Preconditions.checkArgument(annee>1900);
		Preconditions.checkArgument(mois>0);
		Preconditions.checkArgument(mois<13);
		Preconditions.checkArgument(jour>0);
		Preconditions.checkArgument(jour<32);
		Preconditions.checkArgument(heure>=0);
		Preconditions.checkArgument(heure<24);
		Preconditions.checkArgument(minutes>=0);
		Preconditions.checkArgument(minutes<60);
		Preconditions.checkArgument(secondes>=0);
		Preconditions.checkArgument(secondes<60);
		Preconditions.checkNotNull(zone);
		Preconditions.checkArgument(!zone.isEmpty());

		LocalDateTime localDateTime=LocalDateTime.of(annee,mois,jour,heure,minutes,secondes);
		ZoneId zoneId=ZoneId.ofOffset(zone, ZoneOffset.ofHours(decalage));
		zonedDateTime=ZonedDateTime.of(localDateTime,zoneId);
		return zonedDateTime;
	}

	public static ZonedDateTime getDate(int annee, int mois, int jour,
	                                    int heure, int minutes, int secondes,
	                                    String zoneId){
		ZonedDateTime zonedDateTime;

		Preconditions.checkArgument(annee>1900);
		Preconditions.checkArgument(mois>0);
		Preconditions.checkArgument(mois<13);
		Preconditions.checkArgument(jour>0);
		Preconditions.checkArgument(jour<32);
		Preconditions.checkArgument(heure>=0);
		Preconditions.checkArgument(heure<24);
		Preconditions.checkArgument(minutes>=0);
		Preconditions.checkArgument(minutes<60);
		Preconditions.checkArgument(secondes>=0);
		Preconditions.checkArgument(secondes<60);
		Preconditions.checkNotNull(zoneId);
		Preconditions.checkArgument(!zoneId.isEmpty());

		LocalDateTime localDateTime=LocalDateTime.of(annee,mois,jour,heure,minutes,secondes);
		ZoneId zoneId2=ZoneId.of(zoneId);
		zonedDateTime=ZonedDateTime.of(localDateTime,zoneId2);
		return zonedDateTime;
	}

	public static ZonedDateTime getDateUTC(int annee, int mois, int jour,
	                                    int heure, int minutes, int secondes){
		return getDate(annee,mois,jour,heure,minutes,secondes,"UTC",0);
	}

	public static boolean estSupEgal(DateTimeZone d1,DateTimeZone d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)>=0;
	}


	public static boolean estSup(DateTimeZone d1,DateTimeZone d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)>0;
	}

	public static boolean estInfEgal(DateTimeZone d1,DateTimeZone d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)<=0;
	}

	public static boolean estInf(DateTimeZone d1,DateTimeZone d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)<0;
	}

	public static boolean estEgal(DateTimeZone d1,DateTimeZone d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)==0;
	}


	public static boolean estSupEgal(ZonedDateTime d1,ZonedDateTime d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)>=0;
	}


	public static boolean estSup(ZonedDateTime d1,ZonedDateTime d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)>0;
	}

	public static boolean estInfEgal(ZonedDateTime d1,ZonedDateTime d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)<=0;
	}

	public static boolean estInf(ZonedDateTime d1,ZonedDateTime d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)<0;
	}

	public static boolean estEgal(ZonedDateTime d1,ZonedDateTime d2){
		Preconditions.checkNotNull(d1);
		Preconditions.checkNotNull(d2);
		return d1.compareTo(d2)==0;
	}

	public static boolean equals(String s1,String s2){
		if(vide(s1)&&vide(s2)){
			return true;
		} else if(vide(s1)||vide(s2)){
			return false;
		} else {
			return s1.equals(s2);
		}
	}
}
