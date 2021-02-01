/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "servicios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ServiciosEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nombre;
    private Double precio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio", cascade = {CascadeType.REFRESH})
    private List<ServiciosRealizadosEntity> serviciosRealizados = new ArrayList<>();
    
    public ServiciosEntity() {
    }

    public ServiciosEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getServiciosRealizados() {
        return serviciosRealizados.size();
    }

    @Override
    public String toString() {
        return "ServiciosEntity{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", serviciosRealizados=" + serviciosRealizados + '}';
    }
    
    
}
