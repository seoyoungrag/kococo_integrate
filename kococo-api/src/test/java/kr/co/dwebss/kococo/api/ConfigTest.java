package kr.co.dwebss.kococo.api;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.dwebss.kococo.api.common.components.YAMLConfigStatistics;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigTest {

    @Autowired
    private YAMLConfigStatistics myConfig;
    
    @Test
    public void testPutRecord() throws URISyntaxException
    {
        System.out.println("getDateCodes: " + myConfig.getDateCodes());
        System.out.println("getDateValues: " + myConfig.getDateValues());
         
        assertEquals(0, 0);
    }
}
