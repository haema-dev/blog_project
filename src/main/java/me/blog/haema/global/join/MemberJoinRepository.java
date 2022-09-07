package me.blog.haema.global.join;

import me.blog.haema.domain.persist.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJoinRepository extends JpaRepository<Member, Long> {

    // CrudRepository 를 상속 받지 않고 JpaRepository 를 상속받는 이유는
    // JpaRepository ─┬─ PagingAndSortingRepository ─ CrudRepository
    //                └─ QueryByExampleExecutor
    // 위와 같이 되어있기 때문에 상위 인터페이스를 상속받아서 사용할 수 있기 때문이다.
}
