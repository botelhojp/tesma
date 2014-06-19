package lemas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lemas.model.LemasLog;
import lemas.model.Project;
import openjade.core.OpenJadeException;

import com.google.gson.Gson;

public class Data {
	
	private static SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd");

	public static String loadFileToStr(File file) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.ready()) {
				sb.append(br.readLine()).append("\n");
			}
			br.close();
		} catch (IOException e) {
                    Message.error(e.getMessage(), null);
		} 
		return sb.toString();
	}

	public static void projectToFile(Project project, String filePath) {
			Gson gson = new Gson();
			String json = gson.toJson(project);
			json.replace(",", ",\n\r");
			try {
				FileWriter writer = new FileWriter(filePath);
				writer.write(json);
				writer.close();
			} catch (IOException e) {
				Message.error(e.getMessage(), null);
				LemasLog.erro(e);
			}
	}

	public static Project fileToProject(File file) {
		try {
			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new FileReader(file));
			Project project = gson.fromJson(br, Project.class);
			return project;
		} catch (IOException e) {
			Message.error(e.getMessage(), null);
			LemasLog.erro(e);
			return null;
		}
	}
	
	public static Date strToDate(String date) {
		try {
			return dt.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToStr(Date date) {
		return dt.format(date);
	}
	
	
	public static int strToIteration(String date) {
		Date d = strToDate(date);
		return Integer.parseInt(dt2.format(d));
	}

	public static Double strToDouble(String value) {
		try {
			return Double.parseDouble(value.replaceAll(",", "."));
		} catch (NumberFormatException e) {
			return 0.0;
		}

	}

	public static int strToValue(String value) {
		if (value.equals("pos")){
			return 1;
		}
		if (value.equals("neu")){
			return 0;
		}
		if (value.equals("neg")){
			return -1;
		}
		throw new OpenJadeException("Tipo [" + value + "] invalido");
	}
	
	public static String exceptionToStr (Throwable ex){
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

}
