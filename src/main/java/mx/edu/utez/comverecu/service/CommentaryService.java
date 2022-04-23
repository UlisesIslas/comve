package mx.edu.utez.comverecu.service;

import mx.edu.utez.comverecu.entity.Commentary;
import mx.edu.utez.comverecu.repository.ICommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService {

    @Autowired
    private ICommentaryRepository commentaryRepository;

    public List<Commentary> findAll() {
        return commentaryRepository.findAll();
    }

    public List<Commentary> findAllByRequestId(long id) {
        return commentaryRepository.findAllByRequestId(id);
    }

    public Commentary findById(long id) {
        return commentaryRepository.findById(id);
    }

    public boolean save(Commentary obj) {
        boolean flag = false;
        Commentary tmp = commentaryRepository.save(obj);
        if (!tmp.equals(null)) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        Commentary tmp = commentaryRepository.findById(id);
        if (!tmp.equals(null)) {
            commentaryRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

}
