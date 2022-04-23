package mx.edu.utez.comverecu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.comverecu.entity.CommitteePresident;

public interface ICommitteePresidentRepository extends JpaRepository<CommitteePresident, Long> {

    @Query(value = "SELECT cp.* FROM committee_president cp INNER JOIN committee c ON c.id = cp.committee INNER JOIN suburb s ON s.id = c.suburb INNER JOIN city ci ON ci.id = s.city INNER JOIN city_link cl ON cl.city = ci.id WHERE cl.id = :id", nativeQuery = true)
    public List<CommitteePresident> listPagination(@Param("id") long id);

    @Query(value = "SELECT COUNT(*) FROM committee_president cp WHERE cp.committee = :id", nativeQuery = true)
    public long totalCommitteePresidentCountByCommitteeId(@Param("id") long id);

    public CommitteePresident findByUserId(long id);
    
}
