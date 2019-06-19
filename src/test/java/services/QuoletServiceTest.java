
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
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"company1", null, "audit1", "create", null
			},
		/*
		 * Positive: A company tries to create a quolet.
		 * Requisite tested: 3.2. An actor who is authenticated as an auditor must be able to:
		 * Manage his or her audits, which includes listing them, showing them, creating them,
		 * updating, and deleting them. An audit can be updated or deleted as long as it is saved in draft mode.
		 * Data coverage : We created a quolet with 6 out of 6 valid parameters.
		 * Exception expected: None. A company can create quolets.
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
			//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76

			{
				"rookie1", null, "audit1", "createNegative", ClassCastException.class
			},
		/*
		 * Negative: A rookie tries to create a quolet
		 * Requisite tested: 3.2. An actor who is authenticated as an auditor must be able to:
		 * Manage his or her audits, which includes listing them, showing them, creating them,
		 * updating, and deleting them. An audit can be updated or deleted as long as it is saved in draft mode.
		 * Data coverage : We tried to create a quolet with an invalid actor
		 * Exception expected: ClassCastException a rookie can not create quolets.
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
