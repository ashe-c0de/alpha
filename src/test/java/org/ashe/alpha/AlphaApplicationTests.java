package org.ashe.alpha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Slf4j
class AlphaApplicationTests {

	@Resource
	private PasswordEncoder passwordEncoder;

	private static Set<String> composeSet(String str) {
		return new HashSet<>(Arrays.asList(str.split(",")));
	}

	@Test
	void contextLoads() {
		String encodePassword = passwordEncoder.encode("s1");
		log.info(encodePassword);
	}

	@Test
	void toSet() {
		String str = "1,1,1,4";
		Set<String> set = composeSet(str);
		log.info(set.toString());
	}

}
