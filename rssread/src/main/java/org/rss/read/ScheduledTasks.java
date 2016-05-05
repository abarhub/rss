package org.rss.read;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Alain on 25/10/2015.
 */
@Component
public class ScheduledTasks {

	public static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private TacheLecture tache0;

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		TacheLecture tache;
		//tache=new TacheLecture();
		tache=tache0;
		//System.out.println("The time is now " + dateFormat.format(new Date()));
		//logger.info("report : "+tache);
		tache.lecture_parametres();
	}
}
