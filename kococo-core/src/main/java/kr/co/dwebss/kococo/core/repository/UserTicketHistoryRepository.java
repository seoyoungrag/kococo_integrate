package kr.co.dwebss.kococo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.co.dwebss.kococo.core.entities.User;
import kr.co.dwebss.kococo.core.entities.UserTicketHistory;

@RepositoryRestResource(collectionResourceRel = "userTicketHistory", path = "userTicketHistory")
public interface UserTicketHistoryRepository extends JpaRepository<UserTicketHistory,String>{
}
