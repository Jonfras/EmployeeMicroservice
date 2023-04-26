package com.krejo.serviceBackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public EmployeeDataService createEmployeeDataService() {
        EmployeeDataService eds = new EmployeeDataService();
        eds.createInitialListEntries();
        return eds;
    }

    @Bean
    public ServiceDataService createServiceDataService() {
        ServiceDataService sds = new ServiceDataService();
        sds.createInitialListEntries();
        return sds;
    }
}