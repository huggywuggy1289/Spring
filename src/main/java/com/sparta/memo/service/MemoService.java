package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // component는 bean 객체로 등록시킴. 근데 service는 어떻게 오류가 안나지? service애너테이션에 접속하면
//component가 붙어있다.
public class MemoService { //bean 객체로 등록되었으며, memoService라는 이름으로 등록되었다. 확인방법은 3-5 / 09:01
    //controller에서 처리했던 주요 로직들을 모두 service 클래스로 옮겼다.


    private final MemoRepository memoRepository;

    // 생성자가 "하나"만 있다면 bean을 사용할때 써야할 autowired라는 애네테이션을 사용안해도 자동으로 부여된다.
    public MemoService(MemoRepository memoRepository) { //spring에서 따로 관리하는 부분이다.
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        //MemoResponseDto는 반환타입이다. controller에 return 을 써놓았으므로
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        Memo saveMemo = memoRepository.save(memo);
        // 이렇게 하면 메모 리포짓토리에 save 메서드가 만들어지고 파라미터에는 저장할 memo 객체 전달. 반환은 memo를 반환

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {

        // DB 조회
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        memo.update(requestDto);

        return id;

    }

    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        memoRepository.delete(memo); //entity 객체를 지우는 것. 여기서는 memo

        return id;

    }

    private Memo findMemo(Long id){
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
