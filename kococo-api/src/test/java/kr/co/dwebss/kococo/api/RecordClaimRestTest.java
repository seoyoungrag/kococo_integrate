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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.dwebss.kococo.core.entities.Analysis;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordClaimRestTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;

    @Test
    public void testPutRecordForClaim() throws URISyntaxException
    {
        //final String baseUrl = "http://localhost:"+randomServerPort+"/api/analysis/10";
        final String baseUrl = "http://localhost:8080/api/analysis/10";
        Analysis req = new Analysis();
        req.setAnalysisId(10);
        req.setClaimReasonCd(100101);
        req.setClaimContents("테스트");
        req.setAnalysisServerUploadPath("/storage/rec_data");
        URI uri = new URI(baseUrl);

        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");     
 
        HttpEntity<Analysis> request = new HttpEntity<>(req, headers);
        try {
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
        /*{
			  "analysisServerUploadPath" : "/storage/rec_data",
			  "claimReasonCd" : 100101,
			  "claimContents" : "테스트"
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
