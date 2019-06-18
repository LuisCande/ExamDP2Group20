
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Quolet extends DomainEntity {

	//Attributes

	private String	ticker;
	private Date	publicationMoment;
	private String	body;
	private String	picture;
	private Boolean	finalMode;

	//Relationships

	private Company	company;
	private Audit	audit;


	//Getters
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\d{6}-\\w{4}")
	public String getTicker() {
		return this.ticker;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Audit getAudit() {
		return this.audit;
	}

	//Setters
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}
}
