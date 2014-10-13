package com.innocreate.json2pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

/***********************************************************************************************************************
*
* JSON2Any - A JSON to class converter
* ####################################
*
* Copyright (C) 2014 by InnoCreate (http://www.innocreate.se)
*
***********************************************************************************************************************
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
* an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
* specific language governing permissions and limitations under the License.
*
**********************************************************************************************************************/

/**
 * 
 * This class reads all the JSON-files in the assets folder and converts them to
 * a POJO. 
 * 
 * @author Simon Evertsson
 * 
 */
public class Json2POJOConverter {
	
	private static final String DEFAULT_ASSETS_PATH = "assets/";
	
	private File mAssetsDir;
	
	private ArrayList<JSONObject> mJSONFiles = new ArrayList<JSONObject>();
	
	/**
	 * Default converter which uses the path to the assets folder lying in the project root as the 
	 * source to read the JSON-files from
	 */
	public Json2POJOConverter() {
		mAssetsDir = new File(DEFAULT_ASSETS_PATH);
	}
	
	/**
	 * Default converter which uses the path to the assets folder lying in the project root as the 
	 * source to read the JSON-files from
	 */
	public Json2POJOConverter(String assetsDirPath) throws IllegalArgumentException {
		if(assetsDirPath != null) {
			mAssetsDir = new File(assetsDirPath);
		} else {
			throw new IllegalArgumentException("Assets directory must not be null.");
		}
	}
	
	private JSONObject readJSONFile(String filename) {
		JSONObject json = null;
		FileInputStream fileIS = null;
		try {
			fileIS = new FileInputStream(new File(mAssetsDir, filename));
			StringBuilder builder = new StringBuilder();
			int ch;
			while((ch = fileIS.read()) != -1){
			    builder.append((char)ch);
			}
			json = new JSONObject(builder.toString()); 
			fileIS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return json;
		
	}
	
	public void readJSONFiles() {
		String jsonFilenames[] = mAssetsDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {				
				return (!name.equalsIgnoreCase("json_converter_test.json") && name.endsWith(".json"));
			}
		});
		
		for(String filename : jsonFilenames) {
			mJSONFiles.add(readJSONFile(filename));
		}
		
	}
	
	public File getAssetsDir() {
		return mAssetsDir;
	}
	
	public ArrayList<JSONObject> getJSONFiles() {
		return mJSONFiles;
	}
	
}
