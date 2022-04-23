package mx.edu.utez.comverecu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "request_attachments")
public class RequestAttachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request", nullable = false)
    @NotNull(message = "La solicitud no puede estar vacía")
    private Request request;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 2, message = "El nombre del archivo debe tener mínimo 2 caracteres")
    @Size(max = 100, message = "El nombre del archivo debe tener máximo 100 caracteres")
    @NotBlank(message = "El nombre del archivo no puede estar vacío")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
