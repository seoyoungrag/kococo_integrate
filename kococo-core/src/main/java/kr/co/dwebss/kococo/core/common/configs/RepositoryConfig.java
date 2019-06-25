package kr.co.dwebss.kococo.core.common.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import kr.co.dwebss.kococo.core.entities.Analysis;
import kr.co.dwebss.kococo.core.entities.AnalysisDetails;
import kr.co.dwebss.kococo.core.entities.Code;
import kr.co.dwebss.kococo.core.entities.Record;
import kr.co.dwebss.kococo.core.entities.RecordOnly;
import kr.co.dwebss.kococo.core.entities.User;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Code.class);
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Record.class);
        config.exposeIdsFor(RecordOnly.class);
        config.exposeIdsFor(Analysis.class);
        config.exposeIdsFor(AnalysisDetails.class);
    }
    
}
