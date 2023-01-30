package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstanceHandlerConfiguration {
    @Bean
    public InstanceHandler instanceHandler() {
        return new InstanceHandler();
    }
}
