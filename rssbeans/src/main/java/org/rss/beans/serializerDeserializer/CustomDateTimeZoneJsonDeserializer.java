package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.rss.beans.flux.DateTimeZone;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.*;


/**
 * Created by Alain on 21/08/2016.
 */


public class CustomDateTimeZoneJsonDeserializer extends JsonDeserializer<DateTimeZone> {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomDateTimeZoneJsonDeserializer.class);


	@Override
	public DateTimeZone deserialize(JsonParser jp, DeserializationContext dc)
			throws IOException, JsonProcessingException {

		long instant = 0;
		Instant instant2=null;
		String zoneId=null;
		int offset=0;
		ZoneOffset zoneOffset=null;
		ZoneId zoneId2=null;
		JsonToken currentToken = null;
		DateTimeZone res=null;
		while ((currentToken = jp.nextValue()) != null) {
			switch (currentToken) {
				case VALUE_NUMBER_INT:
					if (jp.getCurrentName().equals("instant")) {
						instant = jp.getLongValue();
						if(instant>0) {
							instant2 = Instant.ofEpochSecond(instant);
						}
					} else if (jp.getCurrentName().equals("offset")) {
						offset = jp.getIntValue();
						zoneOffset=ZoneOffset.ofTotalSeconds(offset);
					}
					break;
				case VALUE_STRING:
					if (jp.getCurrentName().equals("zoneId")) {
						zoneId = jp.getValueAsString();
						if(!OutilsGeneriques.vide(zoneId)){
							zoneId2=ZoneId.of(zoneId);
						}
					}
					break;
			}
		}

		if(instant2!=null&&zoneOffset!=null&&zoneId2!=null){
			ZonedDateTime z;
			LocalDateTime tmp=LocalDateTime.ofInstant(instant2,ZoneId.of("UTC"));
			z=ZonedDateTime.ofInstant(tmp,zoneOffset,zoneId2);
			res=new DateTimeZone(z);
		}


		return res;
	}

}
