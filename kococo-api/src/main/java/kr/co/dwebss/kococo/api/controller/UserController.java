package kr.co.dwebss.kococo.api.controller;
import java.util.Optional;
/*
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
*/
import java.util.UUID;

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

import kr.co.dwebss.kococo.core.entities.User;
import kr.co.dwebss.kococo.core.repository.UserRepository;

@RestController
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class UserController implements ResourceProcessor<RepositoryLinksResource> {

	@Autowired
	UserRepository userRepository;
	
    private RepositoryEntityLinks entityLinks;
    
    public UserController(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }
	
    @RequestMapping(value="/api/userappid", method=RequestMethod.POST, produces = { "application/hal+json" })
    public Resource<User> postUserAppId() {
    	UUID id = UUID.randomUUID();
		int cnt = 0;
		while (cnt < 10) {
			cnt ++;
			if (userRepository.findById(String.valueOf(id)).isPresent()) {
				id = UUID.randomUUID();
			} else {
				User user = userRepository.save(new User(String.valueOf(id)));

				Link selfLink = entityLinks.linkToSingleResource(User.class, String.valueOf(id));
				
	            user.add(selfLink);
		        Resource<User> resource = new Resource<User>(user);

				return resource;
			}
		}
        return null;

    }

    @RequestMapping(value="/api/userappid/{id}", method=RequestMethod.GET, produces = { "application/hal+json" })
    public Resource<User> getUserAppId(@PathVariable("id") String id) {
    	if(Optional.ofNullable(id).orElse("").equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "userId는 필수 값임", null);
    	}
    	Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
		        Resource<User> resource = new Resource<User>(user.get());
				Link selfLink = entityLinks.linkToSingleResource(User.class, id);
		        resource.add(selfLink);
				return resource;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "에 해당하는 user 데이터가 없음, userId="+id, null);
			}
    }

    @RequestMapping(value="/api/userappid/{id}", method=RequestMethod.PUT, produces = { "application/hal+json" })
    public Resource<User> putUserAppId(@PathVariable("id") String id, @RequestBody User req) {
    	if(Optional.ofNullable(id).orElse("").equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "userId는 필수 값임", null);
    	}
    	Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				user.get().setUserGender(Optional.ofNullable(req.getUserGender()).orElse(user.get().getUserGender()));
				user.get().setUserHeight(Optional.ofNullable(req.getUserHeight()).orElse(user.get().getUserHeight()));
				user.get().setUserAge(Optional.ofNullable(req.getUserAge()).orElse(user.get().getUserAge()));
				user.get().setUserWeight(Optional.ofNullable(req.getUserWeight()).orElse(user.get().getUserWeight()));
				User savendUser = userRepository.save(user.get());
		        Resource<User> resource = new Resource<User>(savendUser);
				Link selfLink = entityLinks.linkToSingleResource(User.class, id);
		        resource.add(selfLink);
				return resource;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + "에 해당하는 user 데이터가 없음, userId="+id, null);
			}
    }
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
	    resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).getUserAppId(null)).withRel("userappid"));
	    return resource;
	    }
}
