package com.tnki.core;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext
class CoreApplicationTest {

	@org.junit.jupiter.api.Test
	public void contextLoads() {
		assertTrue(true);
	}

}
