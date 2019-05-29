package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.Analysis;

@RepositoryRestResource(collectionResourceRel = "analysis", path = "analysis", exported = false)
public interface AnalysisRepository extends JpaRepository<Analysis,Integer>{
}
