package com.ngnt;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import info.debatty.java.stringsimilarity.*;

import com.ngnt.models.Output;
import com.ngnt.models.Position;
import com.ngnt.rest.RestClient;

@SpringBootApplication
public class ColliersApplication {

	static void setColumnTitles(Levenshtein l, HashMap<Integer, String> columnMaps, int count, String cellValue) {
		if (l.distance(cellValue.toLowerCase(), "localitate") < 1) {
			columnMaps.put(count, "localitate");
		}
		if (l.distance(cellValue.toLowerCase(), "city") < 2) {
			columnMaps.put(count, "localitate");
		}
		if (l.distance(cellValue.toLowerCase(), "judet") < 2) {
			columnMaps.put(count, "judet");
		}
		if (l.distance(cellValue.toLowerCase(), "judet_gar") < 2) {
			columnMaps.put(count, "judet");
		}
		if (l.distance(cellValue.toLowerCase(), "tip_str") < 2) {
			columnMaps.put(count, "tip strada");
		}
		if (l.distance(cellValue.toLowerCase(), "nume_str") < 2) {
			columnMaps.put(count, "strada");
		}
		if (l.distance(cellValue.toLowerCase(), "numar") < 2) {
			columnMaps.put(count, "numar");
		}
		if (l.distance(cellValue.toLowerCase(), "nr_str") < 2) {
			columnMaps.put(count, "numar");
		}
		if (l.distance(cellValue.toLowerCase(), "numar camere") < 2) {
			columnMaps.put(count, "numar camere");
		}

		if (l.distance(cellValue.toLowerCase(), "nr_camere") < 2) {
			columnMaps.put(count, "numar camere");
		}

		if (l.distance(cellValue.toLowerCase(), "suprafata") < 2) {
			columnMaps.put(count, "suprafata");
		}

		if (l.distance(cellValue.toLowerCase(), "mp imobil") < 2) {
			columnMaps.put(count, "suprafata");
		}
		if (l.distance(cellValue.toLowerCase(), "suprafata utila") < 2) {
			columnMaps.put(count, "suprafata");
		}
		if (l.distance(cellValue.toLowerCase(), "etaj") < 2) {
			columnMaps.put(count, "etaj");
		}
		if (l.distance(cellValue.toLowerCase(), "impartire") < 2) {
			columnMaps.put(count, "impartire");
		}
		if (l.distance(cellValue.toLowerCase(), "confort_det") < 2) {
			columnMaps.put(count, "impartire");
		}
		if (l.distance(cellValue.toLowerCase(), "mod impartire") < 2) {
			columnMaps.put(count, "impartire");
		}
		if (l.distance(cellValue.toLowerCase(), "an construcite") < 2) {
			columnMaps.put(count, "an");
		}
		if (l.distance(cellValue.toLowerCase(), "an constr") < 2) {
			columnMaps.put(count, "an");
		}
		if (l.distance(cellValue.toLowerCase(), "finisaje") < 2) {
			columnMaps.put(count, "finisaje");
		}
		if (l.distance(cellValue.toLowerCase(), "nrdgunit") < 2) {
			columnMaps.put(count, "id");
		}

	}

	static void constructOutput(HashMap<Integer, String> columnsMap, int count, Output output, Cell cell) {
		if (columnsMap.get(count) == "localitate") {
			output.setLocalitate(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "judet") {
			output.setJudet(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "tip strada") {
			output.setTipStrada(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "strada") {
			output.setStrada(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "numar") {
			output.setNumar(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "numar camere") {
			output.setNumarCamere(cell.getNumericCellValue());
		}

		if (columnsMap.get(count) == "suprafata") {
			output.setSuprafata(cell.getNumericCellValue());
		}

		if (columnsMap.get(count) == "etaj") {
			output.setEtaj(cell.getStringCellValue());
		}

		if (columnsMap.get(count) == "impartire") {
			output.setImpartire(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "an") {
			output.setAnConstructie(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "finisaje") {
			output.setFinisaje(cell.getStringCellValue());
		}
		if (columnsMap.get(count) == "id") {
			output.setIDunic((int) cell.getNumericCellValue());
		}

	}

	public static void main(String[] args) throws Exception, InterruptedException, ExecutionException{

		FileInputStream file = new FileInputStream(
				new File("C:\\Users\\Andrei.Dobre\\Desktop\\Colliers\\1.Data\\Imobiliare.xlsx"));

		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator();

		HashMap<Integer, String> columnsMap = new HashMap<Integer, String>();
		boolean firstRow = true;
		Levenshtein l = new Levenshtein();
		List<Output> outputs = new ArrayList<Output>();

		// iterating over excel file
		while (itr.hasNext()) {
			int count = 0;
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
			Output output = new Output();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				if (firstRow) {
					String cellValue = cell.getStringCellValue();
					setColumnTitles(l, columnsMap, count, cellValue);
				} else {
					constructOutput(columnsMap, count, output, cell);
				}
				count++;
			}
			firstRow = false;
			count = 0;

			String address = output.localitate + "+" + output.judet + "+" + output.tipStrada + "+" + output.strada + "+"
					+ output.numar;
			String newAddress = address.replace(" ", "+");

			/*
			 * RestClient obj = new RestClient(newAddress); Future<Position> futurePosition
			 * = obj.sendGet();
			 * 
			 * while (!futurePosition.isDone()) {
			 * 
			 * }
			 * 
			 * try { Position position = futurePosition.get(); System.out.println(position);
			 * output.setLat(position.lat); output.setLng(position.lng); } catch (Exception
			 * e) { System.err.print(e); }
			 */

			outputs.add(output);
			//System.out.println(output);

		}
		
		for (Output output : outputs) {
			System.out.println(output);
		}

		wb.close();

		// SpringApplication.run(ColliersApplication.class, args);
	}

}
