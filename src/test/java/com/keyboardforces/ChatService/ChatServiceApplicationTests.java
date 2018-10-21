package com.keyboardforces.ChatService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceApplicationTests {

	@Test
	public void contextLoads() {
		/*LocalDateTime now = LocalDateTime.now();

		now = now.plusHours(3);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

		String nowString = now.format(formatter);

		System.out.println(nowString);
	*/
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("Test String");

			File f = new File("C:\\Users\\pC2\\Desktop\\test.zip");
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
			ZipEntry e = new ZipEntry("mytext.txt");
			out.putNextEntry(e);

			byte[] data = sb.toString().getBytes();
			out.write(data, 0, data.length);
			out.closeEntry();

			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
