package com.blodi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    @NotEmpty
    @Size(max = 200)
    private String title;

    @NotEmpty
    @Size(max = 2000)
    private String content;

    @NotEmpty
    @Size(max = 50)
    private String boardType;

    @NotNull
    private Long fileId;

    @NotEmpty
    @Size(max = 50)
    private String writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<String> fileNames;

    // Getter 메서드
    public Long getBno() {
        return bno;
    }

    // Setter 메서드 (필요에 따라)
    public void setBno(Long bno) {
        this.bno = bno;
    }

//    public Board toEntity() {
//        Board build = Board.builder()
//                .bno(bno)
//                .title(title)
//                .content(content)
//                .writer(writer)
//                .fileId(fileId)
//                .boardType(boardType)
//                .build();
//        return build;
//    }



}
