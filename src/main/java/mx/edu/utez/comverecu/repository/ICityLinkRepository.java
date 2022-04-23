package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.CityLink;

public interface ICityLinkRepository extends JpaRepository<CityLink, Long> {
    
}
