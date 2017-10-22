package spring;

import org.springframework.context.annotation.ComponentScan;

// @Configuration
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("security")
public class SecurityXmlConfig {

    public SecurityXmlConfig() {
        super();
    }

}
