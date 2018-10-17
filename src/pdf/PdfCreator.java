/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shirley
 */
public class PdfCreator {
//changing page size 
    //static Document document = new Document (PageSize.A7);

//changing font size 
//static Font courierBold = FontFactory.getFont(FontFactory.COURIER_BOLD, 12);
// .setFont() method 
    
//changing cell colour 
 //cell.setBackgroundColor(BaseColor.YELLOW);
   
    
    static ResultSet result = null;
    static ResultSetMetaData column = null;

    //creates a blank document 
    static Document document = new Document();

    //creates a table with 3 columns 
    static PdfPTable mypdftable = new PdfPTable(3);

    //create pdf table based on data from database using itextpdf library 
    public static void PdfTable() {
        try {
            DataBase.CreateConnection();
            DataBase.statement = DataBase.conn.createStatement();
            result = DataBase.statement.executeQuery("SELECT NAME,LICENSE_NO ,CAR_PLATE FROM DRIVER;");

            //retrieves the properties of the resultset object 
            column = result.getMetaData();

            //creates column headers for table 
            for (int i = 1; i <= column.getColumnCount(); i++) {
                String header = column.getColumnName(i);
                PdfPCell cell = new PdfPCell(new Phrase(header));
                mypdftable.addCell(cell);
            }

            //retreives data from rows of driver table & adds it to pdf table 
            while (result.next()) {
                String name = result.getString("NAME");
                PdfPCell cell3 = new PdfPCell(new Phrase(name));
                mypdftable.addCell(cell3);
                
                String license_no = result.getString("LICENSE_NO");
                PdfPCell cell1 = new PdfPCell(new Phrase(license_no));
                mypdftable.addCell(cell1);

                String car_plate = result.getString("CAR_PLATE");
                PdfPCell cell2 = new PdfPCell(new Phrase(car_plate));
                mypdftable.addCell(cell2);
            }
            DataBase.conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("could not process result query");

        }

    }

    //creating pdf file 
    public static void createPdf(String filePath) throws IOException {

        try {
            // create a pdf file at specific file location 
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            PdfTable();
            document.add(mypdftable);
            document.close();
            System.out.println("file created");

        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("could not find page");
        }

    }
}
