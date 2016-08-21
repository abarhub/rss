package org.rss.beans.flux;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Alain on 13/02/2016.
 */
public class DateTimeZone implements Comparable<DateTimeZone> {

	public static final Logger LOGGER = LoggerFactory.getLogger(DateTimeZone.class);

	private final ZonedDateTime date;
	private final boolean utc;

	public DateTimeZone(ZonedDateTime date) {
		Preconditions.checkNotNull(date);
		this.date = date;
		utc = date.getZone().equals(ZoneId.of("UTC"));
	}

	/**
	 * Création à partir de la date
	 * La date est considérée comme étant UTC
	 *
	 * @param date
	 */
	public DateTimeZone(Date date) {
		Preconditions.checkNotNull(date);
		this.date = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
		utc = true;
	}

	@Override
	public String toString() {
		return "DateTimeZone{" +
				"date=" + date +
				'}';
	}

	@Override
	public int compareTo(DateTimeZone o) {
		if (o == null)
			return -1;
		ZonedDateTime d1, d2;
		d1 = date;
		d2 = o.date;
		if (d1 == null && d2 == null)
			return 0;
		if (d1 == null)
			return 1;
		if (d2 == null)
			return -1;
		return d1.compareTo(d2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DateTimeZone that = (DateTimeZone) o;

		return date != null ? date.equals(that.date) : that.date == null;

	}

	@Override
	public int hashCode() {
		return date != null ? date.hashCode() : 0;
	}

	public String format(String pattern) {
		Preconditions.checkNotNull(date);
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	private ZonedDateTime convToUTC() {
		if (utc) {
			return date;
		} else {
			Instant tmp = date.toInstant();
			return tmp.atZone(ZoneId.of("UTC"));
		}
	}

	public DateTimeZone toUTC() {
		if (utc) {
			return this;
		} else {
			return new DateTimeZone(convToUTC());
		}
	}

	public Date toDateUTC() {
		return Date.from(convToUTC().toInstant());
	}
}
