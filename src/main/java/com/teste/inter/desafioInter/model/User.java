package com.teste.inter.desafioInter.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String nome;

    @Column(length = 512)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DigitoUnico> resultados = new ArrayList<>();

    @Column(length = 2048)
    private String chavePublica;


    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public List<DigitoUnico> getResultados() {
        return resultados;
    }


    public void setResultados(List<DigitoUnico> resultados) {
        this.resultados = resultados;
    }


    public String getChavePublica() {
        return chavePublica;
    }


    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }    
}
