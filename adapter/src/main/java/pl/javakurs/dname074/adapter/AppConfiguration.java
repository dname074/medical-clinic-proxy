package pl.javakurs.dname074.adapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.javakurs.dname074.domain.*;

import java.time.Clock;

@Configuration
public class AppConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DoctorServiceProvider doctorService(MedicalClinicProvider medicalClinicClient) {
        return new DoctorServiceImpl(medicalClinicClient);
    }

    @Bean
    public VisitServiceProvider visitService(MedicalClinicProvider medicalClinicClient,
                                             Clock clock) {
        return new VisitServiceImpl(medicalClinicClient, clock);
    }

    @Bean
    public PageMapper pageMapper() {
        return new PageMapperImpl();
    }
}
