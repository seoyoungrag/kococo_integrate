package kr.co.dwebss.kococo.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.entities.Record;
import kr.co.dwebss.kococo.core.entities.RecordOnly;
import kr.co.dwebss.kococo.core.repository.AnalysisRepository;
import kr.co.dwebss.kococo.core.repository.RecordOnlyRepository;
import kr.co.dwebss.kococo.core.repository.RecordRepository;

@RestController
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class RecordController implements ResourceProcessor<RepositoryLinksResource> {

	@Autowired
	RecordRepository recordRepository;
	@Autowired
	RecordOnlyRepository recordOnlyRepository;
	@Autowired
	AnalysisRepository analysisRepository;
	
    private RepositoryEntityLinks entityLinks;
    
    public RecordController(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }
	
	@RequestMapping(value = "/api/record/consulting/{id}", method = RequestMethod.PUT, produces = { "application/hal+json" })
	public Resource<Record> putRecord(@PathVariable("id") Integer id, @RequestBody Record req) {
		Optional<Record> ori = recordRepository.findById(id);
		if (!ori.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "에 해당하는 record 데이터가 없음", null);
		}
		if (!Optional.ofNullable(req.getConsultingTitle()).orElse("").equals("")
				&& !Optional.ofNullable(req.getConsultingContents()).orElse("").equals("")) {
			//분석정보 업데이트(child)
			List<Analysis> ansList = req.getAnalysisList();
			for(Analysis ans : ansList) {
				Optional<Analysis> ansU = analysisRepository.findById(ans.getAnalysisId());
				if (!ansU.isPresent()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "에 해당하는 analysis 데이터가 없음, recordId="+id, null);
				}
				if (!Optional.ofNullable(ans.getAnalysisServerUploadPath()).orElse("").equals("")){
					ansU.get().setAnalysisServerUploadDt(LocalDateTime.now());
					ansU.get().setAnalysisServerUploadPath(ans.getAnalysisServerUploadPath());
					ansU.get().setAnalysisServerUploadYn('Y');
					Analysis ansRes = analysisRepository.save(ansU.get());
					
				} else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"analysisServerUploadPath 값이 없음, analysisId="+ans.getAnalysisId()+" recordId="+id, null);
				}
			}
			//레코드 업데이트(parent)
			ori.get().setConsultingTitle(req.getConsultingTitle());
			ori.get().setConsultingContents(req.getConsultingContents());
			ori.get().setConsultingYn('Y');
			ori.get().setConsultingRegistDt(LocalDateTime.now());
			
			Record res = recordRepository.save(ori.get());
			Link selfLink = linkTo(methodOn(RecordController.class).getRecord(id)).withSelfRel();
			res.add(selfLink);
			Resource<Record> resource = new Resource<Record>(res);

			return resource;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"consultingTitle, consultingContents 중에 값이 없는 게 있음", null);
		}
	}

    @RequestMapping(value="/api/record/search/findByRecordId/{id}", method=RequestMethod.GET, produces = { "application/hal+json" })
    public Resource<Record> getRecord(@PathVariable("id") Integer id) {
    	Optional<Record> ori = recordRepository.findById(id);
    	if(ori.isPresent()) {
	        Resource<Record> resource = new Resource<Record>(ori.get());
			return resource;
    	}else {
            return null;
    	}
    }
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
	    resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RecordController.class).getRecord(null)).withRel("analysis"));
	    return resource;
	    }
}
