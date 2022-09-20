package me.blog.haema.domain.member.repository;

import me.blog.haema.domain.member.entity.persist.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, // Spring Data Jpa 사용하기 위해 상속
        PagingAndSortingRepository<Member, Long> { // Paging 사용하기 위해 상속

    // CrudRepository 를 상속 받지 않고 JpaRepository 를 상속받는 이유는
    // JpaRepository ─┬─ PagingAndSortingRepository ─ CrudRepository
    //                └─ QueryByExampleExecutor
    // 위와 같이 되어있기 때문에 상위 인터페이스를 상속받아서 사용할 수 있기 때문이다.

    Member findByEmail(String email);

    Page<Member> findAll(Pageable pageable);

    boolean existsByEmail(String email);
}
