package kr.co.dwebss.kococo.api.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.entities.AnalysisDetails;
import kr.co.dwebss.kococo.core.entities.Code;
import kr.co.dwebss.kococo.core.entities.Record;
import kr.co.dwebss.kococo.core.repository.AnalysisRepository;
import kr.co.dwebss.kococo.core.repository.CodeRepository;
import kr.co.dwebss.kococo.core.repository.RecordRepository;

@RestController
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class StatisticsController implements ResourceProcessor<RepositoryLinksResource> {

    Logger logger = LoggerFactory.getLogger(StatisticsController.class);
 
	@Autowired
	RecordRepository recordRepository;
	@Autowired
	CodeRepository codeRepository;
	@Autowired
	AnalysisRepository analysisRepository;
	String STRING_FOR_STATISTICS_TERM_COD_NULL = "999999";
	
	@RequestMapping(value = "/api/statistics", method = RequestMethod.GET, produces = { "application/hal+json" })
	public Resource<ReturnStatistics> getStatistics(
			//사용자앱id, 기간코드
			@RequestParam(name="userappid", required=true) String userappid, @RequestParam(name="dateCd", required=true) Integer dateCd
			) {
		Optional<Code> cdOpt = codeRepository.findById(dateCd);
		if(!cdOpt.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 dateCd, dateCd="+dateCd, null);
		}
		Code cd = cdOpt.get();
		List<Record> recordList = null;
		int getCodeValue = Integer.parseInt(Optional.ofNullable(cd.getCodeValue()).orElse(STRING_FOR_STATISTICS_TERM_COD_NULL));
		if(getCodeValue == 0 || getCodeValue == Integer.parseInt(STRING_FOR_STATISTICS_TERM_COD_NULL)) {
			if(getCodeValue == Integer.parseInt(STRING_FOR_STATISTICS_TERM_COD_NULL)) {
				logger.error("dataCd값이 Code테이블에는 존재하지만, code_value 값에 값이 없음. datecd="+dateCd);
			}
			recordList = recordRepository.findByUserAppId(userappid, null);
		}else if(getCodeValue < 10) {
			LocalDate startD = LocalDate.now().minusDays(getCodeValue);
			LocalDate endD = LocalDate.now().plusDays(1);
			recordList = recordRepository.findByUserAppIdAndRecordStartDBetween(userappid, startD, endD); 
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 dateCd, code_value가 10을 넘을 수 없음, db를 체크해야 합니다. dateCd="+dateCd, null);
		}
		//기간 동안 수면시간, 코골이, 이갈이, 무호흡 총합계를 구한다.
		//평균 vo를 별도로 만들어서 점수를 set 한후 return 할 것임.
		ReturnStatistics rs = new ReturnStatistics();
		for(Record rc : recordList) {
			ReturnStatistics tmp = getSleepTimes(rc);
			rs.recordedTimes += tmp.recordedTimes;
			rs.snoringTimes += tmp.snoringTimes;
			rs.grindingTimes += tmp.grindingTimes;
			rs.osaTimes += tmp.osaTimes;
			
		}
		//점수를 내기위해 각각 평균시간을 계산한 vo를 생성한다.
		ReturnStatistics rsForAver = new ReturnStatistics();
		if(rs.recordedTimes == 0) {
			rs.sleepScore = 0;
			rs.timesBadSleepForMinus = 0;
			rs.timesBadSleep = 0;
			rs.timeDiffFromAppro = 0;
			rs.description = "데이터가 없습니다.";
		}else {
			rsForAver.recordedTimes = rs.recordedTimes/recordList.size();
			rsForAver.snoringTimes = rs.snoringTimes/recordList.size();
			rsForAver.grindingTimes = rs.grindingTimes/recordList.size();
			rsForAver.osaTimes = rs.osaTimes/recordList.size();
			rsForAver = getSleepScore(rsForAver);
			//점수계산이 끝났으니, 리턴할 vo에 set한다.
			rs.sleepScore = rsForAver.sleepScore < 0 ? 0 : rsForAver.sleepScore;
			rs.timesBadSleepForMinus = rsForAver.timesBadSleepForMinus;
			rs.timesBadSleep = rsForAver.timesBadSleep;
			rs.timeDiffFromAppro = rsForAver.timeDiffFromAppro;
			rs.description = rsForAver.description;
		}
        Resource<ReturnStatistics> rtnObj = new Resource<ReturnStatistics>(rs);
		return rtnObj;
		
		
	}

	@RequestMapping(value = "/api/statistics/record", method = RequestMethod.GET, produces = { "application/hal+json" })
	public Resource<ReturnStatistics> getStatisticsForRecord(
			//사용자앱id, 기간코드
			@RequestParam(name="userappid", required=true) String userappid, @RequestParam(name="recordId", required=true) Integer recordId
			) {
		Optional<Record> recOpt = recordRepository.findById(recordId);
		if(!recOpt.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "record가 없음, recordId, recordId="+recordId, null);
		}
		ReturnStatistics rs = new ReturnStatistics();
		ReturnStatistics tmp = getSleepTimes(recOpt.get());
		rs.recordedTimes += tmp.recordedTimes;
		rs.snoringTimes += tmp.snoringTimes;
		rs.grindingTimes += tmp.grindingTimes;
		rs.osaTimes += tmp.osaTimes;
		getSleepScore(rs);
		rs.sleepScore = rs.sleepScore < 0 ? 0 : rs.sleepScore;
		
        Resource<ReturnStatistics> rtnObj = new Resource<ReturnStatistics>(rs);
		return rtnObj;
		
		
	}
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(StatisticsController.class).getStatistics(null, null))
				.withRel("statistics"));
		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(StatisticsController.class).getStatisticsForRecord(null, null))
				.withRel("statistics/record"));
		return resource;
	}

	public ReturnStatistics getSleepScore(ReturnStatistics rs) {
		//수면 적정 기준 시간, 기준 시간 미달 차감 점수, 기준 시간 초과 차감 점수를 가져온다.
		List<Code> cdList = codeRepository.findByCodeBetween(300100, 300199);
		int timeAppropriate = 8;
		int excessMinus = 6;
		int underMinus = 12;
		for(Code cd : cdList) {
			if(cd.getCode()==300101) {
				timeAppropriate = Integer.parseInt(Optional.ofNullable(cd.getCodeValue()).orElse(String.valueOf((timeAppropriate))));
			}else if(cd.getCode()==300102) {
				underMinus = Integer.parseInt(Optional.ofNullable(cd.getCodeValue()).orElse(String.valueOf((underMinus))));
			}else if(cd.getCode()==300103) {
				excessMinus = Integer.parseInt(Optional.ofNullable(cd.getCodeValue()).orElse(String.valueOf((excessMinus))));
			}
		}
		//수면시간에서 기준 수면 시간에 대한 점수를 계산한다.
		//기준 시간 기준으로 더 잤을 때, 덜 잤을 때 계산
		rs.timeDiffFromAppro = ((rs.recordedTimes/60)/60)/1000 - timeAppropriate;
		if(rs.timeDiffFromAppro>0) {
			//더 잤으면
			rs.sleepScore -= (excessMinus * rs.timeDiffFromAppro);
			rs.description = "sleepScore = sleepScore{100} -(excessMinus{"+excessMinus+"} * rs.timeDiffFromAppro{"+rs.timeDiffFromAppro+"}){"+(excessMinus * rs.timeDiffFromAppro)+"}";
		}else {
			//덜 잤으면
			rs.sleepScore -= (underMinus * rs.timeDiffFromAppro);
			rs.description = "sleepScore = sleepScore{100} -(underMinus{"+underMinus+"} * rs.timeDiffFromAppro{"+rs.timeDiffFromAppro+"}){"+(underMinus * rs.timeDiffFromAppro)+"}";
		}
		//나쁜 잠 차감식 = 수면점수 - (나쁜 잠 / 총 수면시간 *100)
		//나쁜 잠 = 코골이+이갈이+무호흡 총합
		rs.timesBadSleep = rs.snoringTimes + rs.grindingTimes + rs.osaTimes;
		rs.timesBadSleepForMinus =(long) (((double)rs.timesBadSleep / (double)rs.recordedTimes) * 100);
		rs.sleepScore -= rs.timesBadSleepForMinus;
		rs.description += "-( ( (rs.snoringTimes{"+rs.snoringTimes+"} + rs.grindingTimes{"+rs.grindingTimes+"} + rs.osaTimes{"+rs.osaTimes+"})/rs.recordedTimes{"+rs.recordedTimes+"} ) *100 ){"+rs.timesBadSleepForMinus+"}";
		return rs;
	}
	public ReturnStatistics getSleepTimes(Record record) {
		ReturnStatistics rtnObj = new ReturnStatistics();
		//녹음된 총 시간은은 녹음종료시간-녹음시작시간
		rtnObj.recordedTimes = Duration.between(record.getRecordStartDt(), record.getRecordEndDt()).toMillis();
		for(Analysis ans : record.getAnalysisList()) {
			boolean isSnoring = false;
			for(AnalysisDetails ansd : ans.getAnalysisDetailsList()) {
				//코골이는 200101, 이갈이는 200102, 무호흡은 200103
				//코골이는 분석 단위로 한번만 체크하면 되고, 체크되면 분석파일의 전체시간이 코골이 시간이 됨
				if(ansd.getTermTypeCd()==200101) {
					isSnoring = true;
				}
				//이갈이는 시작시간과 종료시간을 누적
				if(ansd.getTermTypeCd()==200102) {
					rtnObj.grindingTimes += Duration.between(ansd.getTermStartDt(), ansd.getTermEndDt()).toMillis();
				}
				//무호흡도 시작시간과 종료시간을 누적
				if(ansd.getTermTypeCd()==200103) {
					rtnObj.osaTimes += Duration.between(ansd.getTermStartDt(), ansd.getTermEndDt()).toMillis();
				}
			}
			//코골이면 코골이 시간에 합산한다.
			if(isSnoring) {
				rtnObj.snoringTimes += Duration.between(ans.getAnalysisStartDt(), ans.getAnalysisEndDt()).toMillis();
			}
		}
		
		return rtnObj;
	}
}

class ReturnStatistics {
	long timesBadSleepForMinus;
	long timesBadSleep;
	long timeDiffFromAppro;
	int sleepScore = 100; //수면점수 100에서부터 차감한다.
	long recordedTimes = 0; //녹음된 총 시간(녹음버튼 시작-종료시간, 분석으로 녹음된 시간이 아님)
	long snoringTimes = 0; //코골이 시간
	long osaTimes = 0; //무호흡 시간
	long grindingTimes = 0; //이갈이 시간
	String description = "";
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSnoringScore() {
		return sleepScore;
	}
	public long getRecordedTimes() {
		return recordedTimes;
	}
	public long getSnoringTimes() {
		return snoringTimes;
	}
	public long getOsaTimes() {
		return osaTimes;
	}
	public long getGrindingTimes() {
		return grindingTimes;
	}
	public long getTimesBadSleepForMinus() {
		return timesBadSleepForMinus;
	}
	public long getTimesBadSleep() {
		return timesBadSleep;
	}
	public long getTimeDiffFromAppro() {
		return timeDiffFromAppro;
	}
	public int getSleepScore() {
		return sleepScore;
	}
	
}
