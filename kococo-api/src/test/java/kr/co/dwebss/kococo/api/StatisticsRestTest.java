package kr.co.dwebss.kococo.api;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RequestCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.entities.AnalysisDetails;
import kr.co.dwebss.kococo.core.entities.Record;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsRestTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;

    @Test
    public void testGetStatisticsByUserAppIdAndDateCd() throws URISyntaxException
    {
        //final String baseUrl = "http://localhost:"+randomServerPort+"/api/analysis/10";
        final String baseUrl = "http://localhost:8080/api/statistics?userappid=c0362dd4-97f4-488c-b31c-12cb23b534cf&dateCd=100301";

        HttpHeaders headers = new HttpHeaders();
 
        HttpEntity<Analysis> request = new HttpEntity<>(headers);
        
        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.GET, request, String.class);
         System.out.println(result.getStatusCodeValue());
         if(result.getStatusCodeValue()!=200) {
        	 System.out.println(result.getBody());
         }
        assertEquals(200, result.getStatusCodeValue());
    }
    
    @Test
    public void testGetStatisticsByUserAppIdAndRecordId() throws URISyntaxException
    {
        final String baseUrl = "http://localhost:8080/api/statistics/record?userappid=c0362dd4-97f4-488c-b31c-12cb23b534cf&recordId=14";

        HttpHeaders headers = new HttpHeaders();
 
        HttpEntity<Analysis> request = new HttpEntity<>(headers);
        
        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.GET, request, String.class);
         System.out.println(result.getStatusCodeValue());
         if(result.getStatusCodeValue()!=200) {
        	 System.out.println(result.getBody());
         }
        assertEquals(200, result.getStatusCodeValue());
    }
}
