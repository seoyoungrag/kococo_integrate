package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.Payment;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
public interface PaymentRepository extends JpaRepository<Payment,String>{
}
