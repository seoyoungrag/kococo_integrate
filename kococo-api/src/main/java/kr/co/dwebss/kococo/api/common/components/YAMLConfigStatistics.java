package kr.co.dwebss.kococo.api.common.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("statistics")
public class YAMLConfigStatistics {
  
    private List<Integer> dateCodes = new ArrayList<>();
    private List<Integer> dateValues = new ArrayList<>();
	public List<Integer> getDateCodes() {
		return dateCodes;
	}
	public void setDateCodes(List<Integer> dateCodes) {
		this.dateCodes = dateCodes;
	}
	public List<Integer> getDateValues() {
		return dateValues;
	}
	public void setDateValues(List<Integer> dateValues) {
		this.dateValues = dateValues;
	}
 
    
}