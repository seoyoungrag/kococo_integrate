package kr.co.dwebss.kococo.core.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.RecordOnly;

@RepositoryRestResource(collectionResourceRel = "recordOnly", path = "recordOnly")
public interface RecordOnlyRepository extends JpaRepository<RecordOnly,Integer>{
    List<RecordOnly> findByUserAppId(@Param("userAppId") String userAppId, Pageable pageable);
    List<RecordOnly> findByUserAppIdAndConsultingsNotNull(@Param("userAppId") String userAppId, Pageable pageable);
    List<RecordOnly> findByUserAppIdAndRecordStartDBetween(
    		@Param("userAppId") String userAppId, 
    		@Param("startD")LocalDate startD, 
    		@Param("endD")LocalDate endD);
}
