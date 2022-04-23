package mx.edu.utez.comverecu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.comverecu.entity.CityLink;
import mx.edu.utez.comverecu.respository.ICityLinkRepository;

@Service
public class CityLinkService {

    @Autowired
    private ICityLinkRepository linkRepository;

    public List<CityLink> findAll() {
        return linkRepository.findAll();
    }

    public CityLink findOne(long id) {
        return linkRepository.getById(id);
    }

    public boolean save(CityLink obj) {
        boolean flag = false;
        CityLink tmp = linkRepository.save(obj);
        if (!tmp.equals(null)) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        CityLink tmp = linkRepository.getById(id);
        if (!tmp.equals(null)) {
            linkRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
