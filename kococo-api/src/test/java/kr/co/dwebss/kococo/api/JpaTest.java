package kr.co.dwebss.kococo.api;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.dwebss.kococo.core.entities.Record;
import kr.co.dwebss.kococo.core.repository.RecordRepository;

/*
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
*/
public class JpaTest {
	/*
	@Autowired
	RecordRepository recordRepository;
    @LocalServerPort
    int randomServerPort;
    
    @Test
    public void jpaTest() throws URISyntaxException
    {
    	
    	List<Record> RecordList = recordRepository.findByUserAppId("c0362dd4-97f4-488c-b31c-12cb23b534cf");
         
        assertEquals(RecordList.size(), RecordList.size());
    }
    */
}
