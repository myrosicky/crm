package service;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import comm.commTest;
import context.WebMvcContext;

public class ExcelFileServiceTest extends commTest {

	@Autowired
	private ExcelFileService excelFileService;
	
	
	@Test
	public void testParse() {
		excelFileService.parse(new File(""));
	}
	
	public static void main(String[] args){
		String a = new String("a");
		String b = "a";
		
		System.err.println((b+b)==(b+b));
		System.err.println((b+b));
	}
}
