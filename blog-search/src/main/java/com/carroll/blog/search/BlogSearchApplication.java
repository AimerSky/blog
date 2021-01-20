package com.carroll.blog.search;

import com.carroll.blog.mbg.BlogMbgApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BlogMbgApplication.class})
public class BlogSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSearchApplication.class, args);
	}

}
