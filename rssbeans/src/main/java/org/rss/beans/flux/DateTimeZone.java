package org.rss.beans.flux;

import java.util.Date;

/**
 * Created by Alain on 13/02/2016.
 */
public class DateTimeZone implements Comparable<DateTimeZone> {

	private Date date;

	public DateTimeZone(){

	}

	public DateTimeZone(Date date){
		this.date=date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "DateTimeZone{" +
				"date=" + date +
				'}';
	}

	@Override
	public int compareTo(DateTimeZone o) {
		if(o==null)
			return -1;
		Date d1,d2;
		d1=date;
		d2=o.getDate();
		if(d1==null&&d2==null)
			return 0;
		if(d1==null)
			return 1;
		if(d2==null)
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
}
