package io.xmeta.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = "io.xmeta"
)
public class XMetaServerApp {

	public static void main(String[] args) {
		SpringApplication.run(XMetaServerApp.class, args);
	}

}
