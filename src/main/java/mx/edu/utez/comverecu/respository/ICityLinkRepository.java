package mx.edu.utez.comverecu.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.CityLink;

public interface ICityLinkRepository extends JpaRepository<CityLink, Long> {
    
}
