package com.url.shortener;

import com.url.shortener.controller.UrlController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlApplicationTests {
	@Autowired
	private UrlController urlController;
	@Test
	void contextLoads() {
		Assertions.assertThat(urlController).isNotNull();
	}

}
