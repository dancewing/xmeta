package io.xmeta.chassis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = "io.xmeta"
)
public class XMetaApp {

	public static void main(String[] args) {
		SpringApplication.run(XMetaApp.class, args);
	}

}