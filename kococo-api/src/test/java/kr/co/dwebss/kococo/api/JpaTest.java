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

import kr.co.dwebss.kococo.core.repository.RecordRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaTest {

	@Autowired
	RecordRepository recordRepository;
    @LocalServerPort
    int randomServerPort;
    
    @Test
    public void testPutRecord() throws URISyntaxException
    {
    	
    	List RecordList = recordRepository.findByUserAppId("23e34679-cfa1-49a4-9b07-badffe08235f");
         
        assertEquals(RecordList.size(), RecordList.size());
    }
}
