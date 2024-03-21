package com.ceiba.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UsuarioRepositorio userRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {

			Usuario userEntity = Usuario.builder()
					.email("ingalexisrojas2@mail.com")
					.username("dannyrojas")
					.numIdentificacion("1090413746")
					.password(passwordEncoder.encode("small1990"))
					.roles(Set.of(Rol.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			Usuario userEntity2 = Usuario.builder()
					.email("anyi@mail.com")
					.username("anyi")
					.numIdentificacion("1092456789")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(Rol.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();

			Usuario userEntity3 = Usuario.builder()
					.email("andrea@mail.com")
					.username("andrea")
					.numIdentificacion("1091254786")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(Rol.builder()
							.name(ERole.valueOf(ERole.INVITED.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}
*/
}
