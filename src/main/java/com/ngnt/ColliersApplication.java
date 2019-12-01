package com.ngnt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ngnt.models.Output;
import com.ngnt.models.Position;
import com.ngnt.rest.RestClient;

import info.debatty.java.stringsimilarity.Levenshtein;

@SpringBootApplication
public class ColliersApplication {

	static void setColumnTitles(Levenshtein l, HashMap<Integer, String> columnMaps, int count, String cellValue) {
		if (l.distance(cellValue.toLowerCase(), "localitate") < 2) {
			columnMaps.put(count, "localitate");
		}
		if (l.distance(cellValue.toLowerCase(), "city") < 2) {
			columnMaps.put(count, "localitate");
		}
		if (l.distance(cellValue.toLowerCase(), "judet/sector") < 2) {
			columnMaps.put(count, "judet");
		}
		if (l.distance(cellValue.toLowerCase(), "judet_gar") < 2) {
			columnMaps.put(count, "judet");
		}
		if (l.distance(cellValue.toLowerCase(), "tip_str") < 2) {
			columnMaps.put(count, "tip strada");
		}
		if (l.distance(cellValue.toLowerCase(), "tip artera") < 2) {
			columnMaps.put(count, "tip strada");
		}
		if (l.distance(cellValue.toLowerCase(), "nume_str") < 2) {
			columnMaps.put(count, "strada");
		}
		if (l.distance(cellValue.toLowerCase(), "strada") < 2) {
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

		if (l.distance(cellValue.toLowerCase(), "nr_camere") < 3) {
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
		if (l.distance(cellValue.toLowerCase(), "an constr") > 100) {
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

		try {
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
		} catch (Exception e) {
			System.err.print(e);
		}

	}

	private static void writeToCSV(List<Output> outputs, String numeFisier)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(numeFisier, "UTF-8");

		for (Output output : outputs) {
			writer.print(output.IDunic);
			writer.print(",");
			writer.print(output.localitate);
			writer.print(",");
			writer.print(output.judet);
			writer.print(",");
			writer.print(output.tipStrada);
			writer.print(",");
			writer.print(output.strada);
			writer.print(",");
			writer.print(output.numar);
			writer.print(",");
			writer.print(output.numarCamere);
			writer.print(",");
			writer.print(output.suprafata);
			writer.print(",");
			writer.print(output.etaj);
			writer.print(",");
			writer.print(output.impartire);
			writer.print(",");
			writer.print(output.anConstructie);
			writer.print(",");
			writer.print(output.finisaje);
			writer.print(",");
			writer.print(output.lat);
			writer.print(",");
			writer.print(output.lng);
			writer.print("\n");
		}
		writer.close();
	}

	public static void main(String[] args) throws Exception, InterruptedException, ExecutionException {

		for (int i = 0; i < 2; i++) {
			FileInputStream file = new FileInputStream(
					new File("C:\\Users\\Andrei.Dobre\\Desktop\\Colliers\\1.Data\\Imobiliare.xlsx"));

			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(i);
			Iterator<Row> itr = sheet.iterator();

			HashMap<Integer, String> columnsMap = new HashMap<Integer, String>();
			boolean firstRow = true;
			Levenshtein l = new Levenshtein();
			List<Output> outputs = new ArrayList<Output>();
			int max = 0;

			
			while (itr.hasNext() && max < 100) {
				int count = 0;
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator(); 
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

				if (output.localitate != null && output.judet != null && output.tipStrada != null
						&& output.strada != null && output.numar != null) {
					String address = output.localitate + "+" + output.judet + "+" + output.tipStrada + "+" + output.strada
							+ "+" + output.numar;
					String newAddress = address.replace(" ", "+");

					RestClient obj = new RestClient(newAddress);
					Future<Position> futurePosition = obj.sendGet();

					while (!futurePosition.isDone()) {

					}

					try {
						Position position = futurePosition.get();
						System.out.println(position);
						output.setLat(position.lat);
						output.setLng(position.lng);
					} catch (Exception e) {
						System.err.print(e);
					}
				}

				

				if (output.suprafata != 0 && output.strada != null) {
					outputs.add(output);
				}
				max ++;

			}

			for (Output output : outputs) {
				System.out.println(output);
			}

			writeToCSV(outputs, "outputs" + i + ".txt");

			wb.close();

		}

	}

}
