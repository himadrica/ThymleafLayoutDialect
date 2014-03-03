package com.thym.layoutdialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.thym.layoutdialect.domain.MessageForm;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	private static List<MessageForm> messageFormRepository = new ArrayList<MessageForm>();

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		/*
		 * Date date = new Date(); DateFormat dateFormat =
		 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
		 * locale);
		 * 
		 * String formattedDate = dateFormat.format(date);
		 * 
		 * model.addAttribute("serverTime", formattedDate );
		 */

		return "index";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("messageInfo") MessageForm messageForm,
			BindingResult result) {

		if (result.hasErrors()) {
			logger.error("We have error in submitting form.");
			return "submitform";
		}

		addNewMessage(messageForm);
		
		logger.info("Total message = " + messageFormRepository.size());
		return "redirect:/list";

	}
	
	@RequestMapping(value = "/submit",method = RequestMethod.GET)
	public String submit(Model model) {
		model.addAttribute("messageInfo", new MessageForm());
		return "submitform";
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView listvalues() {
		
		logger.info(" list view rendring = " + messageFormRepository.size());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("messageInfo", new MessageForm());
		modelAndView.setViewName("submitform");
		modelAndView.addObject("messages", messageFormRepository);
		return modelAndView;
	}

	private void addNewMessage(MessageForm messageForm) {
		messageFormRepository.add(messageForm);
	}
}
