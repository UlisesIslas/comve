package mx.edu.utez.comverecu.repository;
import mx.edu.utez.comverecu.entity.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRequestRepository extends JpaRepository<Request, Long>, PagingAndSortingRepository<Request, Long>{
    public Request findById(long id);
    @Query(value = "SELECT r.* FROM request r INNER JOIN committee_president cp ON cp.id = r.president INNER JOIN committee c ON c.id = cp.committee WHERE c.id = :id", nativeQuery = true)
    List<Request> findAllByCommitteeId(@Param("id") long id);
    @Query(value = "SELECT r.* FROM request r INNER JOIN committee_president p ON p.id = r.president INNER JOIN committee com ON com.id = p.committee WHERE com.id = :id AND r.payment_amount IS NOT NULL AND r.payment_status = 1;", nativeQuery = true)
    List<Request> findAllUnpaidByCommitteeId(@Param("id") long id);
    @Query(value = "SELECT r.* FROM request r INNER JOIN committee_president cp ON cp.id = r.president INNER JOIN committee c ON " +
            "c.id = cp.committee INNER JOIN suburb s ON s.id = c.suburb WHERE s.id = :id", nativeQuery = true)
    List<Request> findRequestBy(@Param("id") long id);
}
