package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController { //이 클래스가1

    private final MemoService memoService; //jdbc를 사용하려면 이걸 생성

    public MemoController(MemoService memoService) { //spring에서 따로 관리하는 부분이다.
        this.memoService = memoService; //메모서비스를 직접만듬2
        //1,2를 통해 제어의 흐름은 memocontroller > memoservice로 가고 있다는 것을 알 수 있다_강한 결합
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto); //서비스에 객체로 보낼형식
        // 클라이언트로 리턴하자.

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto); //업데이트할 부분은 아이디도 포함.
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        return memoService.deleteMemo(id);
    }

}