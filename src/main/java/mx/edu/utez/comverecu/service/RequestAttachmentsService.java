package mx.edu.utez.comverecu.service;

import mx.edu.utez.comverecu.entity.RequestAttachment;
import mx.edu.utez.comverecu.respository.IRequestAttachmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestAttachmentsService {

    @Autowired
    private IRequestAttachmentsRepository attachmentsRepository;

    public List<RequestAttachment> findAll() {
        return attachmentsRepository.findAll();
    }

    public RequestAttachment findByRequestId(long id){
        return attachmentsRepository.findAttachmentsByRequestId(id);
    }

    public RequestAttachment findById(long id) {
        return attachmentsRepository.findById(id);
    }

    public boolean save(RequestAttachment obj) {
        boolean flag = false;
        RequestAttachment tmp = attachmentsRepository.save(obj);
        if (!tmp.equals(null)) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        RequestAttachment tmp = attachmentsRepository.findById(id);
        if (!tmp.equals(null)) {
            attachmentsRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
