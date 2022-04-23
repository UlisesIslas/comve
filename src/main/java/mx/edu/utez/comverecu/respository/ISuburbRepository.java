package mx.edu.utez.comverecu.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.Suburb;

public interface ISuburbRepository extends JpaRepository<Suburb, Long> {
    
}
