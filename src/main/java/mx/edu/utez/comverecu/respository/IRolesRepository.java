package mx.edu.utez.comverecu.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.comverecu.entity.Roles;

public interface IRolesRepository extends JpaRepository<Roles, Long> {
    
    public Roles findById(long id);
    Roles findByAuthority(String authority);

}
