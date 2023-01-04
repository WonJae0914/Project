package com.example.board.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.Entity.Question;

public interface InformationQuestionRepository extends JpaRepository<Question, Integer>{
	
	Page<Question> findAll(Pageable pagealbe);
	 @Query("select "
	            + "distinct q "
	            + "from Question q " 
	            + "left outer join SiteUser u1 on q.author=u1 "
	            + "left outer join Answer a on a.question=q "
	            + "left outer join SiteUser u2 on a.author=u2 "
	            + "where "
	            + "   q.subject like %:kw% "
	            + "   or q.content like %:kw% "
	            + "   or u1.username like %:kw% "
	            + "   or a.content like %:kw% "
	            + "   or u2.username like %:kw% ")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable); 
	 
//	@Modifying // @Modifying 어노테이션은 @Query 어노테이션에서 작성된 조회를 제외한 데이터의 변경이 있는 삽입(Insert), 수정(Update), 삭제(Delete) 쿼리 사용시 필요한 어노테이션
//	@Query("update Question question set question.view = question.view + 1 where question.id = :id")
//	int updateView(@Param("id") Integer id);

}