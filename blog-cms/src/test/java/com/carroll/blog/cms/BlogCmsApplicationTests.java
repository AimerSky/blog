package com.carroll.blog.cms;

import com.carroll.blog.cms.service.LearnTestService;
import com.carroll.blog.mbg.mapper.LearnTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogCmsApplicationTests {

	@Autowired
	private LearnTestService learnTestService ;


	@Test
	void contextLoads() {
		long startTime = System.currentTimeMillis();    //获取开始时间


		long endTime = System.currentTimeMillis();    //获取结束时间

		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}

}
