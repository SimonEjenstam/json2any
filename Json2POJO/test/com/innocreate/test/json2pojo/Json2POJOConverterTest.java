package com.innocreate.test.json2pojo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;

import com.innocreate.json2pojo.Json2POJOConverter;

public class Json2POJOConverterTest {

	@Test
	public void initialisationTest() {
		/* Default constructor */
		Json2POJOConverter converter = new Json2POJOConverter();
		File assetsDir = converter.getAssetsDir();
		assertNotNull(assetsDir);
		assertTrue(assetsDir.isDirectory());
		assertTrue(assetsDir.canRead());
		
		
		/* Supplied illegal path */
		try {
			converter = new Json2POJOConverter(null);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		/* Supplied legal path */
		try {
			converter = new Json2POJOConverter("assets");
		} catch (IllegalArgumentException e) {
			assertTrue(false);
		}
		assetsDir = converter.getAssetsDir();
		assertNotNull(converter);
		assertNotNull(assetsDir);
		assertTrue(assetsDir.isDirectory());
		assertTrue(assetsDir.canRead());
		
		
		
	}
	
	@Test
	public void readTest() {
		Json2POJOConverter converter = new Json2POJOConverter();
		File assetsDir = converter.getAssetsDir();
		String test[] = assetsDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.equalsIgnoreCase("json_converter_test.json");
			}
		});
		if(test.length == 1) {
			converter.readJSONFiles();
			assertTrue(converter.getJSONFiles().size() > 0);
		} else {
			fail("The default assets folder must contain one file named 'json_converter_test.json'.");
		}
	}

}
