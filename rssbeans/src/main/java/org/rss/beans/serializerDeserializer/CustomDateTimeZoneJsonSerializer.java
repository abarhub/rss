package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.rss.beans.flux.DateTimeZone;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;


/**
 * Created by Alain on 21/08/2016.
 */

public class CustomDateTimeZoneJsonSerializer extends StdSerializer<DateTimeZone> {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomDateTimeZoneJsonSerializer.class);

	public CustomDateTimeZoneJsonSerializer() {
		this(DateTimeZone.class);
	}

	public CustomDateTimeZoneJsonSerializer(Class<DateTimeZone> t) {
		super(t);
	}

	@Override
	public void serialize(DateTimeZone d,
	                      JsonGenerator jgen,
	                      SerializerProvider sp) throws IOException, JsonGenerationException {

		ZonedDateTime d2=d.toDate();
		jgen.writeStartObject();

		LocalDateTime d3=d2.toLocalDateTime();
		OffsetDateTime offset = d2.toOffsetDateTime();
		ZoneId zoneId = d2.getZone();

		Instant instant=d3.toInstant(ZoneOffset.UTC);
		jgen.writeNumberField("instant",instant.getEpochSecond());
		jgen.writeNumberField("offset",offset.toOffsetTime().getOffset().getTotalSeconds());
		jgen.writeStringField("zoneId",zoneId.getId());

		jgen.writeEndObject();
	}
}
