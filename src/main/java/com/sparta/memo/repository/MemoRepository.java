package com.sparta.memo.repository;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class MemoRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Memo save(Memo memo) {
        // DB 저장 >> 엇 이건 service에도, controller에도 걸맞지 않아. 명백히 repository의 역할이다.
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO memo (username, contents) VALUES (?, ?)"; // ?는 동적인 값을 의미
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, memo.getUsername());
                    preparedStatement.setString(2, memo.getContents());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        memo.setId(id); //메모에 아이디 넣어주고

        return memo; //그대로 메모를 반환하는 것이다.
    }

    public List<MemoResponseDto> findAll() {

        //DB조회
        String sql = "SELECT * FROM memo";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id"); // Long 타입
                String username = rs.getString("username"); //String타입
                String contents = rs.getString("contents"); //String타입
                return new MemoResponseDto(id, username, contents); // 이렇게 MemoResponseDto라는 객체 한개가 되었다
            }
        });
    }


    public void update(Long id, MemoRequestDto requestDto) { //memo 내용 수정
        //long타입의 id와 MemoRequestDto타입의 requestDto라는 파라미터값을 받아와 아래의 내용대로 처리한다.

        String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
    }


    public void delete(Long id) {
        String sql = "DELETE FROM memo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Memo findById(Long id) { //공용 메서드는 가장 최하단에 두는 것이 좋다.
        // DB 조회
        String sql = "SELECT * FROM memo WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Memo memo = new Memo();
                memo.setUsername(resultSet.getString("username"));
                memo.setContents(resultSet.getString("contents"));
                return memo;
            } else {
                return null;
            }
        }, id);
    }

    @Transactional
    public Memo createMemo(EntityManager em) {
        Memo memo = em.find(Memo.class, 1);
        memo.setUsername("Robbie");
        memo.setContents("@Transactional 전파 테스트 중!");

        System.out.println("createMemo 메서드 종료");
        return memo;
    }

}
