package mx.edu.utez.comverecu.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.Committee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICommitteeRepository extends JpaRepository<Committee, Long>, PagingAndSortingRepository<Committee, Long> {
    public Committee findById(long id);
}
