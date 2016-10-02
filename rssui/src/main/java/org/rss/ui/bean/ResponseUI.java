package org.rss.ui.bean;

/**
 * Created by Alain on 02/10/2016.
 */
public class ResponseUI {

	private boolean ok;
	private String messageError;

	public ResponseUI(){
		ok=true;
	}

	public ResponseUI(String messageError){
		ok=false;
		this.messageError=messageError;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	@Override
	public String toString() {
		return "ResponseUI{" +
				"ok=" + ok +
				", messageError='" + messageError + '\'' +
				'}';
	}
}
