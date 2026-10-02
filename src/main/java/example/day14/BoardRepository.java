package example.day14;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface BoardRepository extends JpaRepository<BoardEntity,Long>{
 
}
