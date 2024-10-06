package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String contents;

    @Column(name = "modified_at") // 필드명 정의
    private LocalDateTime modifiedAt; // 마지막 수정일 필드 추가

    public Memo(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.modifiedAt = LocalDateTime.now(); // 생성 시점의 날짜와 시간으로 초기화
    }
}


//package com.sparta.memo.entity;
//
//import com.sparta.memo.dto.MemoRequestDto;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
//@Getter
//@Setter
//@Table(name = "memo") // 매핑할 테이블의 이름을 지정
//@NoArgsConstructor
//public class Memo extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "username", nullable = false)
//    private String username;
//    @Column(name = "contents", nullable = false, length = 500)
//    private String contents;
//
//    public Memo(MemoRequestDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.contents = requestDto.getContents();
//    }
//
//    public void update(MemoRequestDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.contents = requestDto.getContents();
//    }
//}
