package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.rss.beans.flux.DateTimeZone;

import java.io.IOException;

/**
 * Created by Alain on 21/08/2016.
 */
public class DateTimeJsonDeserializer extends StdDeserializer<DateTimeZone> {


	public DateTimeJsonDeserializer(Class<DateTimeJsonDeserializer> t) {
		super(t);
	}

	public DateTimeJsonDeserializer() {
		this(DateTimeJsonDeserializer.class);
	}


	@Override
	public DateTimeZone deserialize(JsonParser jp, DeserializationContext dc)
			throws IOException, JsonProcessingException {

		/*long id = 0;
		String name = null;
		String []languages = null;
		JsonToken currentToken = null;
		while ((currentToken = jp.nextValue()) != null) {
			switch (currentToken) {
				case VALUE_NUMBER_INT:
					if (jp.getCurrentName().equals("id")) {
						id = jp.getLongValue();
					}
					break;
				case VALUE_STRING:
					switch (jp.getCurrentName()) {
						case "name":
							name = jp.getText();
							break;
						case "languages":
							languages = jp.getText().split(";");
							break;
						default:
							break;
					}
					break;
				default:
					break;
			}
		}
		return new SWEngineer(id, name, languages);*/

	}

}
