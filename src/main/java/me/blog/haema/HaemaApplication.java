package me.blog.haema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // CreateDate 적용하기 위해 꼭 필요하다.
public class HaemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaemaApplication.class, args);
	}

}
