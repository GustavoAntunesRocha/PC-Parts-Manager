package br.com.antunes.gustavo.pcpartsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.pcpartsproject.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	Users findByEmail(String email);

}
