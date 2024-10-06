package com.sparta.memo.dto;

//import com.sparta.memo.entity.Memo;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//@Getter
//public class MemoResponseDto {
//    private Long id;
//    private String username;
//    private String contents;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//
//    public MemoResponseDto(Memo memo) {
//        this.id = memo.getId();
//        this.username = memo.getUsername();
//        this.contents = memo.getContents();
//        this.createdAt = memo.getCreatedAt(); //extends안하면 연동이 get으로 받아올수없음
//        this.modifiedAt = memo.getModifiedAt();
//    }
//}

import com.sparta.memo.entity.Memo;
import lombok.Getter;
//Client에 데이터를 반환할 때 사용할 MemoResponseDto 클래스 생성
@Getter
public class MemoResponseDto{
    private Long id;
    private String username;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
    }
}