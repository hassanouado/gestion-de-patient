package ma.enset.spring_patient;

import ma.enset.spring_patient.entities.Patient;
import ma.enset.spring_patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPatientApplication.class, args);
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
             patientRepository.save(new Patient(null,"hassan",new Date(),false,123));
            patientRepository.save(new Patient(null,"mohammed",new Date(),true,234));
            patientRepository.save(new Patient(null,"adil",new Date(),true,100));
            patientRepository.save(new Patient(null,"moha",new Date(),false,111));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });

        };
    }

}
