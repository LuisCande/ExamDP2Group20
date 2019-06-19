
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Company;
import domain.Quolet;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuoletServiceTest extends AbstractTest {

	// System under test: Audit ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private QuoletService	quoletService;

	@Autowired
	private CompanyService	companyService;


	@Test
	public void QuoletPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"company1", null, "audit1", "create", null
			},
		/*
		 * Positive: A auditor tries to create a audit.
		 * Requisite tested: 3.2. An actor who is authenticated as an auditor must be able to:
		 * Manage his or her audits, which includes listing them, showing them, creating them,
		 * updating, and deleting them. An audit can be updated or deleted as long as it is saved in draft mode.
		 * Data coverage : We created a audit with 5 out of 5 valid parameters.
		 * Exception expected: None. An auditor can create audits.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void QuoletNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.9% | Covered Instructions 112 | Missed Instructions 6 | Total Instructions 118

			{
				"rookie1", null, "audit1", "createNegative", ClassCastException.class
			},
		/*
		 * Negative: A auditor tries to create an audit with invalid data.
		 * Requisite tested: 3.2. An actor who is authenticated as an auditor must be able to:
		 * Manage his or her audits, which includes listing them, showing them, creating them,
		 * updating, and deleting them. An audit can be updated or deleted as long as it is saved in draft mode.
		 * Data coverage : We created an audit with 4 out of 5 valid parametequolet * Exception expected: ConstraintViolationException. Moment must be past.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void template(final String username, final String st, final String id, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			if (operation.equals("create")) {
				final Quolet quolet = this.quoletService.create(this.getEntityId(id));
				quolet.setBody("This is the statement ain't it cool?");

				final Company company = this.companyService.findOne(this.getEntityId(username));
				quolet.setCompany(company);

				this.quoletService.save(quolet);
			} else if (operation.equals("createNegative")) {
				final Quolet quolet = this.quoletService.create(this.getEntityId(id));
				quolet.setBody("This is the statement ain't it cool?");

				final Company company = this.companyService.findOne(this.getEntityId(username));
				quolet.setCompany(company);

				this.quoletService.save(quolet);

			}

			this.quoletService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
