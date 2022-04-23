package mx.edu.utez.comverecu.respository;
import mx.edu.utez.comverecu.entity.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRequestRepository extends JpaRepository<Request, Long>, PagingAndSortingRepository<Request, Long>{
    public Request findById(long id);
    @Query(value = "SELECT r.* FROM committee c INNER JOIN users u ON u.committee = c.id INNER JOIN request r ON r.user = u.id WHERE c.id = :id", nativeQuery = true)
    List<Request> findAllByCommitteeId(@Param("id") long id);
    @Query(value = "SELECT r.* FROM committee c INNER JOIN users u ON u.committee = c.id INNER JOIN request r ON r.user = u.id WHERE c.id = 1 AND r.payment_amount IS NOT NULL AND r.payment_status = 1", nativeQuery = true)
    List<Request> findAllUnpaidByCommitteeId(@Param("id") long id);

}
