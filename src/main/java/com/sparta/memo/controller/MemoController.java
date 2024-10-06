//package com.sparta.memo.controller;
//
//import com.sparta.memo.dto.MemoRequestDto;
//import com.sparta.memo.dto.MemoResponseDto;
//import com.sparta.memo.service.MemoService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class MemoController { //이 클래스가1
//    private final MemoService memoService; //jdbc를 사용하려면 이걸 생성
//    public MemoController(MemoService memoService) { //spring에서 따로 관리하는 부분이다.
//        this.memoService = memoService; //메모서비스를 직접만듬2
//        //1,2를 통해 제어의 흐름은 memocontroller > memoservice로 가고 있다는 것을 알 수 있다_강한 결합
//    }
//    @PostMapping("/memos")
//    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
//        return memoService.createMemo(requestDto); //서비스에 객체로 보낼형식
//        // 클라이언트로 리턴하자.
//    }
//    @GetMapping("/memos")
//    public List<MemoResponseDto> getMemos() {
//
//        return memoService.getMemos();
//    }
//    @PutMapping("/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        return memoService.updateMemo(id, requestDto); //업데이트할 부분은 아이디도 포함.
//    }
//    @DeleteMapping("/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        return memoService.deleteMemo(id);
//    }
//
//}

package com.sparta.memo.controller;
import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//메모 생성하기 API를 받을 수 있는 Controller와 메서드 생성
@RestController // controller역할을 하는 클래스임을 선언
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>(); //  DB와 연결을 하지 않았기 때문에 메모 데이터를 저장할 Java 컬렉션 생성
    // Map<key타입, value저장할 객체>
    @PostMapping("/memos")
    // ResponseDto클래스가 MemoRequestDto 클래스의 요청을 받아 반환하는 구조임을 나타냄.
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // ResponseDto는 데이터베이스와 소통하는 entity객체로 변환할 예정
        Memo memo = new Memo(requestDto);
        // Memo의 max아이디값 찾기(중복안됨)
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet())+1:1;
        memo.setId(maxId);
        // DB저장
        memoList.put(memo.getId(),memo);
        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        // db대신 우리가 받고 있는 컬렉션은 Map자료구조이므로, 조회할땐 이 Map을 list로 변환시켜야한다.
        // Stream API를 사용하면 데이터 변환, 필터링 등을 간결하게 처리가능
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        // map함수는 List내부의 Dto로 value Memo객체를 변환시킴.
        // 마지막으로, 변환된 MemoResponseDto 객체들을 List로 모으기 위해 toList() 메서드를 사용
        return responseList;
    }

}