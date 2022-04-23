package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.comverecu.entity.CityLink;

public interface ICityLinkRepository extends JpaRepository<CityLink, Long> {
    @Query(value = "SELECT COUNT(*) FROM city_link cl WHERE cl.city = :id", nativeQuery = true)
    public long totalCityLinkCountByCityId(@Param("id") long id);

    @Query(value = "SELECT * FROM city_link cl WHERE cl.user = :id", nativeQuery = true)
    public CityLink findByUserId(long id);
}
