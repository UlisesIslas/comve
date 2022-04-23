package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.State;

public interface IStateRepository extends JpaRepository<State, Long> {
    public State findById(long id);
}
