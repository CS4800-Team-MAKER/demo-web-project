package edu.csupomona.cs480.controller;

import java.util.List;
import com.google.common.math;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.UserManager;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.joda.time.DateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK";
	}
	@RequestMapping(value = "/cs480/ericRamirez", method = RequestMethod.GET)
	String broncoTest() {
		return "Team member: Eric Ramirez";
	}
	@RequestMapping(value = "/cs480/ericRamJSoup", method = RequestMethod.GET)
    String parsingPage() {
    	StringBuffer html = new StringBuffer();

        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\" />");
        html.append("<title>Bronco Life</title>");
        html.append("<meta name=\"description\" content=\"Bronco Latest News\" />");
        html.append("<meta name=\"keywords\" content=\"Bronco Gossip\" />");
        html.append("</head>");
        html.append("<body>");
        html.append("<div id='color'>We are Green & Gold! </div> />");
        html.append("</body>");
        html.append("</html>");

        Document doc = Jsoup.parse(html.toString());

        String description = doc.select("meta[name=description]").get(0).attr("content");

        String keywords = doc.select("meta[name=keywords]").first().attr("content");

        String color1 = doc.getElementById("color").text();
        String color2 = doc.select("div#color").get(0).text();
  
        return description + ", " + keywords + ", " + color1 + ", " + color2;
    }

	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}
	/*
	 * Assignment #3
	 */
	@RequestMapping(value = "/cs480/alan", method = RequestMethod.GET)
	String alansPage() {
		return "Alan Trieu";
	}
	
	@RequestMapping(value = "/cs480/alan/method", method = RequestMethod.GET)
	String alansMethod() {
		Gson gson = new Gson();
		
		String[] test = {"1", "2", "3"};
		String json = gson.toJson(test);
		
		return json;
	}

	@RequestMapping(value = "/cs480/minPark", method = RequestMethod.GET)
	String minsPage() {
		return "Team member: Min Park";
	}

	@RequestMapping(value = "/cs480/minPark/method", method = RequestMethod.GET)
	void minParksmethod() {
		String text = "Min Park's Method using Commons.io";
		File file = new File("test.txt");
		FileUtils.writeStringToFile(file, string);
	}
	
	@RequestMapping(value = "/cs480/klu", method = RequestMethod.GET)
	String kevinsPage() {
		return "Team member: Kevin Lu";
	}
	
	@RequestMapping(value = "/cs480/klu/method", method = RequestMethod.GET)
	int kevinsmethod() {
		DateTime date = new DateTime();
		return date.getDayOfMonth();
	}

	@RequestMapping(value = "/cs480/RamirezR", method = RequestMethod.GET)
	String robsPage() {
		return "Team member: Roberto";
	}
	
	//Assignment #4 Roberto, using google guava
	
	//BigInteger num = 0;
	//num = factorial(5);

}