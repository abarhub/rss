package org.rss.read;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${app.timeScheluler:5000}")
	private long fixedRate;

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		TacheLecture tache;
		tache=tache0;
		tache.lecture_parametres();
	}
}
