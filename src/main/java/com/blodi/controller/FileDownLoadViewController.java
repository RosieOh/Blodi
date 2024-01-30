package com.blodi.controller;

import com.blodi.dto.FileDTO;
import com.blodi.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is a sample class that demonstrates the usage of the @author tag.
 *
 * @author John Doe
 * @version 1.0
 */


@Controller
@Log4j2
@RequiredArgsConstructor
public class FileDownLoadViewController {

    private final FileService fileService;

    // 파일 다운로드를 처리하는 컨트롤러 메서드
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        // 파일 서비스를 통해 fileId로 파일 정보를 가져옵니다.
        FileDTO fileDto = fileService.getFile(fileId);

        // 파일 경로를 얻습니다.
        Path path = Paths.get(fileDto.getFilePath());

        // 파일 스트림에서 Resource를 생성합니다.
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        // 응답 헤더를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        try {
            // 파일 이름을 UTF-8로 인코딩합니다.
            String encodedFilename = new String(fileDto.getOriginFilename().getBytes("UTF-8"), "ISO-8859-1");

            // Content-Disposition 헤더에 인코딩된 파일 이름을 설정합니다.
            headers.setContentDispositionFormData("attachment", encodedFilename);

            // 컨텐츠 타입을 이진 데이터로 설정합니다.
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (IOException e) {
            log.error("파일 이름 인코딩 중 오류 발생: {}", e.getMessage());
            // 예외를 적절하게 처리해야 합니다.
        }

        // Resource와 헤더를 사용하여 응답 엔터티를 반환합니다.
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
