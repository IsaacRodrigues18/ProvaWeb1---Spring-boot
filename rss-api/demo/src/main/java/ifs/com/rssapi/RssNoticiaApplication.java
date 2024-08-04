package ifs.com.rssapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "ifs.com.rssapi")
public class RssNoticiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssNoticiaApplication.class, args);
	}

}
