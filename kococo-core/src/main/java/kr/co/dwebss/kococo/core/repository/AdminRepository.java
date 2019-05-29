package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.Admin;

@RepositoryRestResource(collectionResourceRel = "admin", path = "admin")
public interface AdminRepository extends JpaRepository<Admin,String>{
}
