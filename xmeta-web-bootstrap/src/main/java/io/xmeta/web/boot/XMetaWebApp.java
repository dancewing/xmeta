package io.xmeta.web.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.xmeta")
public class XMetaWebApp {

	public static void main(String[] args) {
		SpringApplication.run(XMetaWebApp.class, args);
	}

}
