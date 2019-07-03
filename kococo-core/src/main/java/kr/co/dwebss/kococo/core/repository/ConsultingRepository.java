package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.Consulting;

@RepositoryRestResource(collectionResourceRel = "consulting", path = "consulting", exported = false)
public interface ConsultingRepository extends JpaRepository<Consulting,Integer>{
}
