package com.digital.engineering.course.training;

import com.digital.engineering.course.training.controller.GreetingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingApplicationTests {

	@Autowired
	private GreetingController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
