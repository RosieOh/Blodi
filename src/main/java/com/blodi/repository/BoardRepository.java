package com.blodi.repository;

import com.blodi.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{
    @Query("SELECT b FROM Board b WHERE b.bno = :bno")
    Optional<Board> findById(@Param("bno") Long bno);

    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType")
    List<Board> findByBoardType(@Param("boardType") String boardType);


    // 새로운 메서드 추가
    Page<Board> findByBoardType(String boardType, Pageable pageable);
}
