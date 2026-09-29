package example.day11;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface MemberRepository extends JpaRepository<MemberEntity,Long>{
    // JPA 사용시 기본적인 CRUD 메소드 제공/ save, findAll, findbyId, deleteById
    // * 메소드쿼리(망명규칙) 또는 네이티브쿼리 추가로 정의 가능
    // findByxxx : xxx에 필드명을 넣어서 조회 추상메소드 만들기/ findByxxx and,or xxx
    MemberEntity findByMid(String mid); // mid 일치하면 엔티티 조회
    Optional<MemberEntity> findByMname(String mname); // mname 일치하면 엔티티 조회
    
}
