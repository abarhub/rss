package org.rss.ui.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Alain on 24/10/2015.
 */
@RestController
public class GreetingController {

	public static final Logger logger = LoggerFactory.getLogger(GreetingController.class);


	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		long no;
		no=counter.incrementAndGet();
		logger.info("hello {} ({})",name,no);
		return new Greeting(no,
				String.format(template, name));
	}
}
