package adapter;

import domain.DoctorServiceImpl;
import domain.DoctorServiceProvider;
import domain.VisitServiceImpl;
import domain.VisitServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DoctorServiceProvider doctorService(MedicalClinicClientImpl medicalClinicClient) {
        return new DoctorServiceImpl(medicalClinicClient);
    }

    @Bean
    public VisitServiceProvider visitService(MedicalClinicClientImpl medicalClinicClient,
                                             Clock clock) {
        return new VisitServiceImpl(medicalClinicClient, clock);
    }

    @Bean
    public PageMapper pageMapper() {
        return new PageMapper();
    }
}
