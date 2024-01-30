package com.blodi.service;

import com.blodi.dto.BoardDTO;

import java.util.List;

// 465P 부터 시작
public interface BoardService {

    public BoardDTO findByBno(Long bno);

    public List<BoardDTO> findAll(BoardDTO boardDTO);

    public void register(BoardDTO boardDTO);

    public void modify(BoardDTO boardDTO);

    public void remove(Long bno);

    public List<BoardDTO> findByBoardType(String boardType);
}
