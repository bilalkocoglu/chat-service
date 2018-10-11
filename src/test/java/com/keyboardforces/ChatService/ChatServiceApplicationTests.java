package com.keyboardforces.ChatService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceApplicationTests {

	@Test
	public void contextLoads() {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

		String nowString = now.format(formatter);

		System.out.println(nowString);
	}

}
