package kr.co.dwebss.kococo.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.Code;

@RepositoryRestResource(collectionResourceRel = "code", path = "code")
public interface CodeRepository extends JpaRepository<Code,Integer>{
    List<Code> findByCodeBetween(@Param("startCd") int startCd, @Param("endCd") int endCd);
}
