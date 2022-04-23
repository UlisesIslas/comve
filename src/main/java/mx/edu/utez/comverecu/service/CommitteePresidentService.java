package mx.edu.utez.comverecu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.comverecu.entity.CommitteePresident;
import mx.edu.utez.comverecu.repository.ICommitteePresidentRepository;

@Service
public class CommitteePresidentService {
    
    @Autowired
    private ICommitteePresidentRepository presidentRepository;

    public List<CommitteePresident> findAll() {
        return presidentRepository.findAll();
    }

    public boolean hasPresident(long id) {
        return presidentRepository.totalCommitteePresidentCountByCommitteeId(id) > 0 ? true : false;
    }

    public CommitteePresident findById(long id) {
        return presidentRepository.getById(id);
    }

    public List<CommitteePresident> listPagination(long id) {
        return presidentRepository.listPagination(id);
    }

    public boolean save (CommitteePresident obj) {
        boolean flag = false;
        CommitteePresident tmp = presidentRepository.save(obj);
        if (!tmp.equals(null)) {
            flag = true;
        }
        return flag;
    }
    
}
