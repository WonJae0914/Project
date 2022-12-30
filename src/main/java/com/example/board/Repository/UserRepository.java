package com.example.board.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.Entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
	
	Optional<SiteUser> findByusername(String username);

}
