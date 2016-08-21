package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.rss.beans.flux.DateTimeZone;

import java.io.IOException;

/**
 * Created by Alain on 21/08/2016.
 */
public class DateTimeZoneJSonSerializer extends StdSerializer<DateTimeZone> {

	public DateTimeZoneJSonSerializer(Class<DateTimeZone> t) {
		super(t);
	}

	public DateTimeZoneJSonSerializer() {
		this(DateTimeZone.class);
	}

	/*@Override
	public void doSerialize( JsonWriter writer, DateTimeZone person, JsonSerializationContext ctx, JsonSerializerParameters params ) {
		writer.value( person.getFirstname() + " " + person.getLastname() );
	}*/


	@Override
	public void serialize(DateTimeZone swe,
	                      JsonGenerator jgen,
	                      SerializerProvider sp) throws IOException, JsonGenerationException {

		StringBuilder lang = new StringBuilder();
		jgen.writeStartObject();
		jgen.writeNumberField("id", swe.getId());
		jgen.writeStringField("name", swe.getName());

		for (String s: swe.getLanguages()) {
			lang.append(s).append(";");
		}
		jgen.writeStringField("languages", lang.toString());

		jgen.writeEndObject();
	}
}
