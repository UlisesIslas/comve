package mx.edu.utez.comverecu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mx.edu.utez.comverecu.entity.Users;
import mx.edu.utez.comverecu.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(long id) {
        return userRepository.findById(id);
    }

    public Page<Users> listPagination(PageRequest page) {
        return userRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    public String findPasswordById(long id) {
        return userRepository.findPasswordById(id);
    }

    public boolean save(Users obj) {
        boolean flag = false;
        Users tmp = userRepository.save(obj);
        if (!tmp.equals(null)) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        Users tmp = userRepository.findById(id);
        if (!tmp.equals(null)) {
            userRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
}
