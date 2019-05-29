package kr.co.dwebss.kococo.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.repository.AnalysisRepository;

@RestController
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class AnalysisController implements ResourceProcessor<RepositoryLinksResource> {

	@Autowired
	AnalysisRepository analysisRepository;
	
    private RepositoryEntityLinks entityLinks;
    
    public AnalysisController(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }
	
	@RequestMapping(value = "/api/analysis/{id}", method = RequestMethod.PUT, produces = { "application/hal+json" })
	public Resource<Analysis> putAnalysisClaim(@PathVariable("id") Integer id, @RequestBody Analysis req) {
		Optional<Analysis> ori = analysisRepository.findById(id);
		if (!ori.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "에 해당하는 analysis 데이터가 없음", null);
		}
		if (!Optional.ofNullable(req.getAnalysisServerUploadPath()).orElse("").equals("")
				&& Optional.ofNullable(req.getClaimReasonCd()).orElse(0) != 0
				&& !Optional.ofNullable(req.getClaimContents()).orElse("").equals("")) {
			ori.get().setClaimYn('Y');
			ori.get().setClaimRegistDt(LocalDateTime.now());
			ori.get().setClaimReasonCd(req.getClaimReasonCd());
			ori.get().setClaimContents(req.getClaimContents());
			ori.get().setAnalysisServerUploadDt(LocalDateTime.now());
			ori.get().setAnalysisServerUploadPath(req.getAnalysisServerUploadPath());
			ori.get().setAnalysisServerUploadYn('Y');

			Analysis res = analysisRepository.save(ori.get());

			Link selfLink = linkTo(methodOn(AnalysisController.class).getAnalysis(id)).withSelfRel();
			res.add(selfLink);
			Resource<Analysis> resource = new Resource<Analysis>(res);

			return resource;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"analysisServerUploadPath, claimReasonCd, claimContents 중에 값이 없는 게 있음", null);
		}
	}

    @RequestMapping(value="/api/analysis/{id}", method=RequestMethod.GET, produces = { "application/hal+json" })
    public Resource<Analysis> getAnalysis(@PathVariable("id") Integer id) {
    	Optional<Analysis> ori = analysisRepository.findById(id);
    	if(ori.isPresent()) {
	        Resource<Analysis> resource = new Resource<Analysis>(ori.get());
			return resource;
    	}else {
            return null;
    	}
    }
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
	    resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AnalysisController.class).getAnalysis(null)).withRel("analysis"));
	    return resource;
	    }
}
