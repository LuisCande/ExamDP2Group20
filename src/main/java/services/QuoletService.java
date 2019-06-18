
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	//Managed repository

	@Autowired
	private QuoletRepository	quoletRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Quolet create(final int id) {
		final Quolet p = new Quolet();

		p.setCompany((Company) this.actorService.findByPrincipal());
		p.setTicker(this.generateTicker());
		p.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		p.setFinalMode(false);
		final Audit audit = this.auditService.findOne(id);
		p.setAudit(audit);

		return p;
	}
	public Quolet findOne(final int id) {
		Assert.notNull(id);

		return this.quoletRepository.findOne(id);
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Quolet save(final Quolet p) {
		Assert.notNull(p);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		final Quolet saved = this.quoletRepository.save(p);

		return saved;
	}

	public void delete(final Quolet p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		//Assertion to make sure that the entity is not final
		Assert.isTrue(p.getFinalMode() == false);

		this.quoletRepository.delete(p);
	}

	//Reconstruct

	public Quolet reconstruct(final Quolet p, final BindingResult binding) {
		Assert.notNull(p);
		Quolet result;

		if (p.getId() == 0)
			result = this.create(p.getAudit().getId());
		else
			result = this.quoletRepository.findOne(p.getId());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(result.getFinalMode() == false);

		result.setBody(p.getBody());
		result.setPicture(p.getPicture());
		result.setFinalMode(p.getFinalMode());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Controlling the size of the body length
		Assert.isTrue(result.getBody().length() <= 100);

		//Assertion that the user modifying this quolet has the correct privilege.
		Assert.isTrue(result.getAudit().getPosition().getCompany() == this.actorService.findByPrincipal());

		//Assertion that the user modifying this quolet has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCompany().getId());

		return result;

	}

	//Generates the first half of the unique tickers.
	private String generateNumber() {
		final Date date = new Date();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0].trim().substring(2, 4);
		final String month = campos[1].trim();
		final String day = campos[2].trim();

		final String res = year + month + day;
		return res;
	}

	//Generates the second half of the unique tickers.
	private String generateString() {
		final Random c = new Random();
		final Random t = new Random();
		String randomString = "";
		int i = 0;
		final int longitud = 4;
		while (i < longitud) {
			final int rnd = t.nextInt(2);
			if (rnd == 0) {
				randomString += ((char) ((char) c.nextInt(10) + 48)); //numeros
				i++;
			} else if (rnd == 1) {
				randomString += ((char) ((char) c.nextInt(26) + 65)); //mayus
				i++;
			}
		}
		return randomString;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker() {
		final String res = this.generateNumber() + "-" + this.generateString();
		return res;

	}

	//Other methods
	public void flush() {
		this.quoletRepository.flush();
	}

	//Retrieves the list of quolets for a certain audit
	public Collection<Quolet> quoletsForAudit(final int id) {
		return this.quoletRepository.quoletsForAudit(id);
	}

	//Retrieves the list of quolets for a certain company
	public Collection<Quolet> getQuoletsForCompany(final int id) {
		return this.quoletRepository.getQuoletsForCompany(id);
	}
}
