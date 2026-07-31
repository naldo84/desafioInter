package com.teste.inter.desafioInter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.inter.desafioInter.model.DigitoUnico;

@Repository
public interface DigitoUnicoRepository extends JpaRepository<DigitoUnico, Long> {

    List<DigitoUnico> findByUserId(Long userId);

} 