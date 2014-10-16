package com.innocreate.json2pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
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
	
	
	private static final String CLASS_NAME = "class_name";
	
	private static final String INTEGER = "int";
	
	private static final String LONG = "long";
	
	private static final String DOUBLE = "double";
	
	private static final String BOOLEAN = "boolean";
	
	private static final String STRING = "String";
	
	
	
	
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
	
	private String readFile(String filename) {
		FileInputStream fileIS = null;
		String result = null;
		try {
			fileIS = new FileInputStream(new File(mAssetsDir, filename));
			StringBuilder builder = new StringBuilder();
			int ch;
			while((ch = fileIS.read()) != -1){
			    builder.append((char)ch);
			}
			result = builder.toString();
			fileIS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	private void writeFile(String filename, String data) {
		FileOutputStream fileOS = null;
		try {
			fileOS = new FileOutputStream(new File(mAssetsDir, filename));
			fileOS.write(data.getBytes("UTF-8"));
			fileOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void readJSONFiles() {
		String jsonFilenames[] = mAssetsDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {				
				return (name.endsWith(".json"));
			}
		});
		
		for(String filename : jsonFilenames) {
			mJSONFiles.add(new JSONObject(readFile(filename)));
		}
		
	}
	
	private String unindent(String currentIndentation, int indentSize) {
		if(currentIndentation.length() == 0) {
			return "";
		} else {
			StringBuilder indentBuilder = new StringBuilder();
			for(int i = 0; i < currentIndentation.length()-indentSize; i++) {
				indentBuilder.append(' ');
			}
			return indentBuilder.toString();
		}
	}
	
	private String indent(String currentIndentation, int indentSize) {
		StringBuilder indentBuilder = new StringBuilder();
		for(int i = 0; i < currentIndentation.length()+indentSize; i++) {
			indentBuilder.append(' ');
		}
		currentIndentation = indentBuilder.toString();
		return currentIndentation;
	}
	
	private String generateFields(HashMap<String, String> fields, String currentIndentation, int indentSize) {
		StringBuilder builder = new StringBuilder();
		currentIndentation = indent(currentIndentation, indentSize);
		for(String fieldName : fields.keySet()) {
			String type = fields.get(fieldName);
			builder.append(currentIndentation + "public " + type + " " + fieldName + ";\n\n");
		}
		return builder.toString();
	}
	
	private String generateAccessorsNMutators(HashMap<String, String> fields, String currentIndentation, int indentSize) {
		StringBuilder builder = new StringBuilder();
		currentIndentation = indent(currentIndentation, indentSize);
		for(String fieldName : fields.keySet()) {
			String type = fields.get(fieldName);
			Character first = Character.toUpperCase(fieldName.charAt(0));
			String rest = fieldName.substring(1);

			/* Getter */
			builder.append(currentIndentation + "public " + type + " get" + first + rest + "() {\n");
			currentIndentation = indent(currentIndentation, indentSize);
			builder.append(currentIndentation + "return " + fieldName + ";\n");
			currentIndentation = unindent(currentIndentation, indentSize);
			builder.append(currentIndentation + "}\n\n");
			
			/* Setter */
			builder.append(currentIndentation + "public void set" + first + rest + "(" + type + " " + fieldName + ") {\n");
			currentIndentation = indent(currentIndentation, indentSize);
			builder.append(currentIndentation + "this." + fieldName + " = " + fieldName + ";\n");
			currentIndentation = unindent(currentIndentation, indentSize);
			builder.append(currentIndentation + "}\n\n");
		}
		return builder.toString();
	}
	
	private String generatePOJO(String className, HashMap<String, String> fields) {
		StringBuilder builder = new StringBuilder();
		int indentSize = 4;
		String currentIndentation = "";
		
		builder.append("import java.util.ArrayList;\n\n");
		builder.append("public class " + className + " {\n\n");
		
		
		String fieldString = generateFields(fields, currentIndentation, indentSize);
		builder.append(fieldString);
		
		String getterSetterString = generateAccessorsNMutators(fields, currentIndentation, indentSize);
		builder.append(getterSetterString);
		
		builder.append("}\n");
		
		return builder.toString();
	}
	
	public void convertJSONFiles() {
		for(JSONObject object : mJSONFiles) {
			String names[] = JSONObject.getNames(object);
			String className = null;
			HashMap<String, String> fields = new HashMap<String, String>();
			for(String name : names) {
				switch(name) {
				case CLASS_NAME:
					className = object.optString(name);
					className = Character.toUpperCase(className.charAt(0)) + className.substring(1);
					break;
				default:
					fields.put(name, object.optString(name));
					break;
				}
				
			}
			if(className != null) {
				String pojoString = generatePOJO(className, fields);
				writeFile(className + ".java", pojoString);
			} else {
				new JSONException("The object must contain a '" + CLASS_NAME + "' field");
			}
		}
	}
	
	public File getAssetsDir() {
		return mAssetsDir;
	}
	
	public ArrayList<JSONObject> getJSONFiles() {
		return mJSONFiles;
	}
	
}
