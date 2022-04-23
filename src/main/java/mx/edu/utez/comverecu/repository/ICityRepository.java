package mx.edu.utez.comverecu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.edu.utez.comverecu.entity.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ICityRepository extends JpaRepository<City, Long> , PagingAndSortingRepository<City, Long> {
    public City findById(long id);

    @Query(value = "SELECT c.* FROM city c WHERE c.state = :id", nativeQuery = true)
    public List<City> findAllCitiesByStateId(@Param("id") long id);
}
