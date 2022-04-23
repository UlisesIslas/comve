package mx.edu.utez.comverecu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "request")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "category", nullable = false)
    @NotNull(message = "La categoría no puede estar vacía")
    private Category category;

    @Column(name = "description", length = 255)
    @Size(min = 2, message = "La descripción debe tener mínimo 2 caracteres")
    @Size(max = 255, message = "La descripción debe tener máximo 255 caracteres")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @ManyToOne
    @JoinColumn(name = "president", nullable = false)
    @NotNull(message = "El presidente no puede estar vacío")
    private CommitteePresident president;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "payment_status", nullable = false)
    @NotNull(message = "El estatus de pago no puede estar vacío")
    private int paymentStatus;

    @Column(name = "payment_amount", nullable = true)
    private double paymentAmount;

    @Column(name = "status", nullable = false)
    @NotNull(message = "El estatus no puede estar vacío")
    private int status;

    public Request() {
        this.status = 2;
        this.paymentStatus = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommitteePresident getPresident() {
        return president;
    }

    public void setPresident(CommitteePresident president) {
        this.president = president;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
