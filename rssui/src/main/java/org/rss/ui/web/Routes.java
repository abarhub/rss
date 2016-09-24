package org.rss.ui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Alain on 24/09/2016.
 */
@Controller
public class Routes {

	@RequestMapping("/")
	public String index() {
		return "forward:/index.html";
	}
}
