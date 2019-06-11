package Duelyst.Database;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class DatabaseCollectioner {
    public static void DatabaseGenerator() {
        try {
            FileInputStream file = new FileInputStream(new File("./src/Duelyst/Database/DuelystCards.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                ArrayList<String> data = new ArrayList<>();
                data.clear();
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                boolean noPut = false;
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:

                            if (!noPut) {
                                System.out.print(cell.getNumericCellValue() + "\t");
                                data.add(String.valueOf((int) Math.floor(cell.getNumericCellValue() + 0.1)));
                            }
                            break;
                        case STRING:

                            if (cell.getStringCellValue().equalsIgnoreCase("Card Name")) {
                                noPut = true;
                            }
                            if (!noPut) {
                                System.out.print(cell.getStringCellValue() + "\t");
                                data.add(cell.getStringCellValue());
                            }
                            break;
                    }
                }
                if (data.size()>0) {
                    new DatabaseCard(data);
                }
                System.out.println("");
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
