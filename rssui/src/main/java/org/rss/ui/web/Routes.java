package org.rss.ui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Alain on 24/09/2016.
 */
@Controller
public class Routes {

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index() {
		return "forward:/index.html";
	}
	@RequestMapping(value = "/",method = RequestMethod.POST)
	public String index2() {
		return index();
	}
}
