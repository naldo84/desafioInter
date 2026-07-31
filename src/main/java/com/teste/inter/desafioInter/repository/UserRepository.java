package com.teste.inter.desafioInter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.inter.desafioInter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
