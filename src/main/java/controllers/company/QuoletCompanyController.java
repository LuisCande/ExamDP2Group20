
package controllers.company;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.QuoletService;
import controllers.AbstractController;
import domain.Company;
import domain.Quolet;

@Controller
@RequestMapping("quolet/company")
public class QuoletCompanyController extends AbstractController {

	//Services

	@Autowired
	private QuoletService	quoletService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		final Company c = (Company) this.actorService.findByPrincipal();
		quolets = this.quoletService.getQuoletsForCompany(c.getId());

		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		result.addObject("requestURI", "quolet/company/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.create(varId);
		result = this.createEditModelAndView(quolet);

		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Quolet quolet;
		Collection<Quolet> quolets;

		quolet = this.quoletService.findOne(varId);

		if (quolet == null || quolet.getFinalMode() == true || quolet.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		this.quoletService.delete(quolet);

		result = new ModelAndView("quolet/list");

		quolets = this.quoletService.getQuoletsForCompany(this.actorService.findByPrincipal().getId());

		result.addObject("quolets", quolets);
		result.addObject("requestURI", "quolet/company/list");

		return result;
	}

	//Edit GET

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.findOne(varId);

		if (quolet == null || quolet.getFinalMode() == true || quolet.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(quolet);

		return result;
	}

	//Edit POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			quolet = this.quoletService.reconstruct(quolet, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(quolet);
		} catch (final Throwable oops) {
			final Collection<Quolet> quolets = this.quoletService.getQuoletsForCompany(this.actorService.findByPrincipal().getId());
			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("requestURI", "quolet/company/list");
			result.addObject("message", "quolet.reconstruct.error");
			return result;
		}

		if (quolet.getId() != 0)
			result = this.createEditModelAndView(quolet);

		try {
			this.quoletService.save(quolet);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(quolet, "quolet.commit.error");
		}
		return result;
	}

	//Other methods

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Quolet quolet) {
		ModelAndView result;

		result = this.createEditModelAndView(quolet, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Quolet quolet, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "quolet/company/edit.do");

		return result;

	}
}
