package com.example.board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.Entity.Question;

public interface QnaRepository extends JpaRepository<Question, Integer> {

}
