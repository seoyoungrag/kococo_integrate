package kr.co.dwebss.kococo.api;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.dwebss.kococo.core.entities.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;
    @Test
    public void testPutRecord() throws URISyntaxException
    {
        //final String baseUrl = "http://localhost:"+randomServerPort+"/api/record/complete";
    	final String baseUrl = "http://localhost:8080/api/userappid/c0362dd4-97f4-488c-b31c-12cb23b534cf";
        
    	User user = new User();
    	user.setUserAge(32);
    	user.setUserGender('M');
    	user.setUserWeight(78);
    	user.setUserHeight(173);
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
 
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        /*
        try {
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
        */
        /*
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
        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);

        System.out.println(result.getStatusCodeValue());
        if(result.getStatusCodeValue()!=200) {
       	 System.out.println(result.getBody());
        }
         
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }
}
