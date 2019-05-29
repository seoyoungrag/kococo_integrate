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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.entities.AnalysisDetails;
import kr.co.dwebss.kococo.core.entities.Record;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordRestTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;
    @Test
    public void testPostRecord() throws URISyntaxException
    {
        //final String baseUrl = "http://localhost:"+randomServerPort+"/api/record/";
    	final String baseUrl = "http://localhost:8080/api/record/";
        
        URI uri = new URI(baseUrl);
        AnalysisDetails analysisDetails = new AnalysisDetails(200101, LocalDateTime.now().plusSeconds(10), LocalDateTime.now().plusSeconds(124));
        AnalysisDetails analysisDetails2 = new AnalysisDetails(200102, LocalDateTime.now().plusSeconds(512), LocalDateTime.now().plusSeconds(5612));
        AnalysisDetails analysisDetails3 = new AnalysisDetails(200103, LocalDateTime.now().plusSeconds(8454), LocalDateTime.now().plusSeconds(12144));
        List set2 = new ArrayList();
        set2.add(analysisDetails);
        set2.add(analysisDetails2);
        set2.add(analysisDetails3);
        Analysis analysis = new Analysis(LocalDateTime.now(), LocalDateTime.now().plusHours(3), LocalDateTime.now()+"_testFileNm.wav","/rec_data/",set2);
        List set = new ArrayList();
        set.add(analysis);
        Record record = new Record(null, "7dc9e960-b0db-4c1c-81b5-2c8f2ce7ca4f", LocalDateTime.now(), LocalDateTime.now().plusHours(8), set);
        record.setSleepStatusCdId(100103);
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");     
 
        HttpEntity<Record> request = new HttpEntity<>(record, headers);
        /*
        try {
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
        {
        	  "userAppId" : "c0362dd4-97f4-488c-b31c-12cb23b534cf",
        	  "recordStartDt" : "2019-05-24T12:00:16.614",
        	  "recordEndDt" : "2019-05-24T20:00:16.614",
        	  "analysisList" : [ {
        	    "analysisStartDt" : "2019-05-24T12:00:16.613",
        	    "analysisEndDt" : "2019-05-24T15:00:16.613",
        	    "analysisFileNm" : "2019-05-24T12:00:16.613_testFileNm.wav",
        	    "analysisFileAppPath" : "/rec_data/",
        	    "analysisDetailsList" : [ {
        	      "termTypeCd" : 200101,
        	      "termStartDt" : "2019-05-24T12:00:26.612",
        	      "termEndDt" : "2019-05-24T12:02:20.613"
        	    }, {
        	      "termTypeCd" : 200102,
        	      "termStartDt" : "2019-05-24T12:08:48.613",
        	      "termEndDt" : "2019-05-24T13:33:48.613"
        	    }, {
        	      "termTypeCd" : 200103,
        	      "termStartDt" : "2019-05-24T14:21:10.613",
        	      "termEndDt" : "2019-05-24T15:22:40.613"
        	    } ]
        	  } ]
        	}
        */
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        System.out.println(result.getStatusCodeValue());
        if(result.getStatusCodeValue()!=200) {
       	 System.out.println(result.getBody());
        }
         
        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }
    /*
    @Test
    public void testPutRecord() throws URISyntaxException
    {
        //final String baseUrl = "http://localhost:"+randomServerPort+"/api/record/complete";
    	final String baseUrl = "http://localhost:8080/api/record/consulting/69";
        
        URI uri = new URI(baseUrl);
        Analysis analysis = new Analysis();
        analysis.setAnalysisId(46); //recordId(69)와 analysisId(46)이 db에 없으면 오류 발생
        analysis.setAnalysisServerUploadPath("firebase/upload/");
        List<Analysis> analysisList = new ArrayList<Analysis>();
        analysisList.add(analysis);
        Record record = new Record("컨설팅 제목2","컨설팅 내용2",14,analysisList);
        HttpHeaders headers = new HttpHeaders();
 
        HttpEntity<Record> request = new HttpEntity<>(record, headers);
        try {
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
        {
        	  "userAppId" : "c0362dd4-97f4-488c-b31c-12cb23b534cf",
        	  "recordStartDt" : "2019-05-24T12:00:16.614",
        	  "recordEndDt" : "2019-05-24T20:00:16.614",
        	  "analysisList" : [ {
        	    "analysisStartDt" : "2019-05-24T12:00:16.613",
        	    "analysisEndDt" : "2019-05-24T15:00:16.613",
        	    "analysisFileNm" : "2019-05-24T12:00:16.613_testFileNm.wav",
        	    "analysisFileAppPath" : "/rec_data/",
        	    "analysisDetailsList" : [ {
        	      "termTypeCd" : 200101,
        	      "termStartDt" : "2019-05-24T12:00:26.612",
        	      "termEndDt" : "2019-05-24T12:02:20.613"
        	    }, {
        	      "termTypeCd" : 200102,
        	      "termStartDt" : "2019-05-24T12:08:48.613",
        	      "termEndDt" : "2019-05-24T13:33:48.613"
        	    }, {
        	      "termTypeCd" : 200103,
        	      "termStartDt" : "2019-05-24T14:21:10.613",
        	      "termEndDt" : "2019-05-24T15:22:40.613"
        	    } ]
        	  } ]
        	}
        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);

        System.out.println(result.getStatusCodeValue());
        if(result.getStatusCodeValue()!=200) {
       	 System.out.println(result.getBody());
        }
         
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }
*/    
}
