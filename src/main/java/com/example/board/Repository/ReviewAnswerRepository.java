package com.example.board.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.Entity.Answer;
public interface ReviewAnswerRepository extends JpaRepository<Answer, Integer>{
}