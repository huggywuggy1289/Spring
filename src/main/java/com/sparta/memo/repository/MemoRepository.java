package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc(); //내림차순으로 정렬하기
//    List<Memo> findAllByUsername(String username); //유저 네임으로 작성된 모든 메모 내용을 가져올수 있음.
}
