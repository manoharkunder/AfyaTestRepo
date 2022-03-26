package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadDataFromJason {

	public static void main(String[] args) throws Exception {
		XSSFWorkbook workbook = null;
		FileOutputStream out = null;
		JSONParser jsonP = new JSONParser();

		try

		(FileReader reader = new FileReader(
				"/home/niveus/eclipse-workspace/UCJ_PERSONAL_LOAN/jsonfiles/employee.json")) {
			// Read JSON File
			Object obj = jsonP.parse(reader);
			JSONArray empList = (JSONArray) obj;
			System.out.println(empList);

			for (int i = 0; i < empList.size(); i++) {
				JSONObject address = (JSONObject) empList.get(i);
				String ids = (String) address.get("id");
				String name = (String) address.get("name");
				String desc = (String) address.get("discription");

				String url = (String) address.get("logo_url");

				System.out.println("the id is ============" + ids);
				System.out.println("the name is ============" + name);
				System.out.println("the desc is ============" + desc);
				System.out.println("the url is ============" + url);

				// workbook object
				workbook = new XSSFWorkbook();

				// spreadsheet object
				XSSFSheet spreadsheet = workbook.createSheet("JasonData");

				// creating a row object
				XSSFRow row;

				// This data needs to be written (Object[])
				Map<String, Object[]> studentData = new TreeMap<String, Object[]>();
				int j = i;

				for (; j <= i;) {

					if (j == 0) {
						studentData.put("0", new Object[] { "id", "name", "discription", "createdAt" });
						break;
					} else {
						studentData.put("" + j + "", new Object[] { ids, name, desc, url });
						break;
					}

				}
				/*
				 * studentData.put( "3", new Object[] { "129", "Narayana", "2nd year" });
				 * 
				 * studentData.put("4", new Object[] { "130", "Mohan", "2nd year" });
				 * 
				 * studentData.put("5", new Object[] { "131", "Radha", "2nd year" });
				 * 
				 * studentData.put("6", new Object[] { "132", "Gopal", "2nd year" });
				 */
				Set<String> keyid = studentData.keySet();

				int rowid = 0;

				// writing the data into the sheets...

				for (String key : keyid) {

					row = spreadsheet.createRow(rowid++);
					Object[] objectArr = studentData.get(key);
					int cellid = 0;

					for (Object obj1 : objectArr) {
						Cell cell = row.createCell(cellid++);
						cell.setCellValue((String) obj1);
					}
				}
				out = new FileOutputStream(new File("/home/niveus/Desktop/data.xlsx"));

				workbook.write(out);
				out.close();

			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// .xlsx is the format for Excel Sheets...
		// writing the workbook into the file...
		/*
		 * FileOutputStream out = new FileOutputStream(new
		 * File("/home/niveus/Desktop/data.xlsx"));
		 * 
		 * workbook.write(out); out.close();
		 */

	}

	/*
	 * private static void parseEmpObj(JSONObject emp) { JSONObject empObj =
	 * (JSONObject) emp.get("employee"); // get emp firstname, lastname, website
	 * String fname = (String) empObj.get("id"); String lname = (String)
	 * empObj.get("name"); String website = (String) empObj.get("website");
	 * System.out.println("First Name: " + fname); System.out.println("Last Name: "
	 * + lname); System.out.println("Website: " + website); }
	 */
}
