package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final JdbcTemplate jdbcTemplate; //jdbc를 사용하려면 이걸 생성

    public MemoController(JdbcTemplate jdbcTemplate) { //spring에서 따로 관리하는 부분이다.
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.createMemo(requestDto); //서비스에 객체로 보낼형식
        // 클라이언트로 리턴하자.

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMemos();
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.updateMemo(id, requestDto); //업데이트할 부분은 아이디도 포함.
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteMemo(id);

    }

}