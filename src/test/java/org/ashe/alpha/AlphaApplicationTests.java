package org.ashe.alpha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class AlphaApplicationTests {

	@Resource
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		String encodePassword = passwordEncoder.encode("s1");
		log.info(encodePassword);
	}

}
