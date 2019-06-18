
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	//Retrieves the list of quolets for a certain audit
	@Query("select q from Quolet q where q.audit.id=?1")
	Collection<Quolet> quoletsForAudit(int id);

	//Retrieves the list of quolets for a certain company
	@Query("select q from Quolet q where q.company.id=?1")
	Collection<Quolet> getQuoletsForCompany(int id);
}
