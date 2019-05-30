package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.AnalysisDetails;

@RepositoryRestResource(collectionResourceRel = "analysisDetails", path = "analysisDetails", exported = false)
public interface AnalysisDetailRepository extends JpaRepository<AnalysisDetails,Integer>{
}
