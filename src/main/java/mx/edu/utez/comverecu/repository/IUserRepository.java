package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.comverecu.entity.Users;

public interface IUserRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users, Long> {
    public Users findById(long id);

    Users findByUsername(String username);

    @Query(value = "SELECT u.password FROM users u WHERE u.id = :id", nativeQuery = true)
    String findPasswordById(@Param("id")long id);
}
