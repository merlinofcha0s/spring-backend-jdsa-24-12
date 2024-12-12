package com.plb.vinylmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class VinylmgtApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(VinylmgtApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setDefaultProfiles("dev");
        application.setEnvironment(environment);
        application.run();
    }

//    @Bean
//    public CommandLineRunner createData(UserRepository userRepository,
//                                        VinylRepository vinylRepository,
//                                        AuthorRepository authorRepository) {
//        return args -> {
//            User newUser = new User("toto@toto.com", "azerty", "toto", "titi");
//            userRepository.save(newUser);
//
//            Author linkinPark = authorRepository.save(new Author("Linkin Park",
//                    LocalDate.of(1996, 1, 1)));
//
//
//            Vinyl inTheEnd = new Vinyl("In the end",
//                    LocalDate.of(2000, 10, 24),
//                    linkinPark, newUser, "https://upload.wikimedia.org/wikipedia/en/3/3f/LinkinParkIntheEnd.jpg");
//
//            Vinyl papercut = new Vinyl("Papercut", LocalDate.of(2000, 10, 24),
//                    linkinPark, newUser, "https://upload.wikimedia.org/wikipedia/en/e/e8/Linkin_Park_-_Papercut_CD_cover.jpg?20101129221758");
//
//            Vinyl oneStepCloser = new Vinyl("One step closer", LocalDate.of(2000, 10, 24),
//                    linkinPark, newUser, "https://upload.wikimedia.org/wikipedia/en/1/1a/Linkin_Park_-_One_Step_Closer_CD_cover.jpg?20190415030049");
//
//            Vinyl pointsOfAuthority = new Vinyl("Points of Authority", LocalDate.of(2000, 10, 24),
//                    linkinPark, newUser, "https://t2.genius.com/unsafe/340x340/https%3A%2F%2Fimages.genius.com%2Fdfcc33efa0db1c2c6f3f3e761d5a9e8b.1000x1000x1.jpg");
//
//
//            vinylRepository.save(inTheEnd);
//            vinylRepository.save(papercut);
//            vinylRepository.save(oneStepCloser);
//            vinylRepository.save(pointsOfAuthority);
//
//            vinylRepository.findAll().forEach(System.out::println);
//        };
//    }

}
