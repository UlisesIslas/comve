package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.City;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICityRepository extends JpaRepository<City, Long> , PagingAndSortingRepository<City, Long> {
    public City findById(long id);
}
