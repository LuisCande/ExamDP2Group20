
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet")
public class QuoletController extends AbstractController {

	//Services

	@Autowired
	private QuoletService	quoletService;


	//Listing by audit
	@RequestMapping(value = "/listByAudit", method = RequestMethod.GET)
	public ModelAndView listByPosition(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		quolets = this.quoletService.quoletsForAudit(varId);

		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		result.addObject("requestURI", "quolet/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Quolet quolet = this.quoletService.findOne(varId);

		if (quolet == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("quolet/display");
		result.addObject("quolet", quolet);
		result.addObject("requestURI", "quolet/display.do");

		return result;
	}

}
