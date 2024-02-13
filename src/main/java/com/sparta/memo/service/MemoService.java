package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoService {
    //controller에서 처리했던 주요 로직들을 모두 service 클래스로 옮겼다.


    private final JdbcTemplate jdbcTemplate; //jdbc를 사용하려면 이걸 생성

    public MemoService(JdbcTemplate jdbcTemplate) { //spring에서 따로 관리하는 부분이다.
        this.jdbcTemplate = jdbcTemplate;
    }
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        //MemoResponseDto는 반환타입이다. controller에 return 을 써놓았으므로
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);
        // 이렇게 하면 메모 리포짓토리에 save 메서드가 만들어지고 파라미터에는 저장할 memo 객체 전달. 반환은 memo를 반환

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {

        // DB 조회
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll(); //전달할 파라미터는 없는 상태이다.
    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id); //접근지정자를 수정하므로서 오류 사라짐.
        if(memo != null) {
            // memo 내용 수정
            memoRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo memo =memoRepository.findById(id);
        if(memo != null) {
            // memo 삭제
            memoRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
