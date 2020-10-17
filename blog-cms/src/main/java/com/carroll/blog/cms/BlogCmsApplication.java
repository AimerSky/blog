package com.carroll.blog.cms;

import com.carroll.blog.mbg.BlogMbgApplication;
import com.carroll.blog.security.BlogSecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BlogMbgApplication.class, BlogSecurityApplication.class})
public class BlogCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogCmsApplication.class, args);
	}

}
