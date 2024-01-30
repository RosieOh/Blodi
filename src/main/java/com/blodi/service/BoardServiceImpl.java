package com.blodi.service;

import com.blodi.entity.Board;
import com.blodi.dto.BoardDTO;
import com.blodi.entity.enums.BoardType;
import com.blodi.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        Board noticeBoard = new Board();
        noticeBoard.setTitle("공지사항");
        noticeBoard.setContent("내용");
        noticeBoard.setBoardType(BoardType.NOTICE.toString());
        board.add(noticeBoard);

        Board studentCTL = new Board();
        studentCTL.setTitle("학생 포트폴리오 게시판");
        studentCTL.setContent("내용");
        studentCTL.setBoardType(BoardType.StudentCTL.toString());
        board.add(studentCTL);

        Board teacherCTL = new Board();
        teacherCTL.setTitle("선생님용 학생 E-PortFolio 게시판");
        teacherCTL.setContent("내용");
        teacherCTL.setBoardType(BoardType.TeacherCTL.toString());
        board.add(teacherCTL);

        Board qna = new Board();
        qna.setTitle("질의응답");
        qna.setContent("내용");
        qna.setBoardType(BoardType.Qna.toString());
        board.add(qna);

        // 생성한 게시판들을 저장
        boardRepository.saveAll(board);
    }


    @Override
    public BoardDTO findByBno(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        log.info("모가 문제고 " + result.toString());
        BoardDTO dto = modelMapper.map(result, BoardDTO.class);
        log.info("모가 문제고 " + dto.toString());
        return dto;
    }

    @Override
    public List<BoardDTO> findAll(BoardDTO boardDTO) {
        List<Board> lst = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board1 : lst) {
            Board board = Board.builder()
                    .bno(boardDTO.getBno())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .boardType(boardDTO.getBoardType())
                    .writer(boardDTO.getWriter())
                    .fileId(boardDTO.getFileId())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> lst = boardRepository.findByBoardType(boardType);

        if (lst != null && !lst.isEmpty()) {
            List<BoardDTO> boardList = lst.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardList;
        } else {
            // 빈 리스트 또는 null 반환하도록 수정
            return Collections.emptyList(); // 또는 return null;
        }
    }

    // 등록 작업 처리
    @Override
    public void register(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .boardType(boardDTO.getBoardType())
                .writer(boardDTO.getWriter())
                .fileId(boardDTO.getFileId())
                .build();
        boardRepository.save(board);
    }


    // 수정 작업 처리
    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    // 삭제 작업 처리
    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }
}
