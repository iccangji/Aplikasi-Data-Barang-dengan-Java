
package pdf;

/**
 *
 * @author Muh. Nur Iksan
 */


import java.io.FileOutputStream;
import java.text.DecimalFormat;

import com.itextpdf.text.Element;
 import com.itextpdf.text.Document;
 import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;

import javax.swing.JTable;

public class Report {
    
    public void createPDF (String pdfFilename, JTable data){

    Document doc = new Document();
    PdfWriter docWriter = null;

    DecimalFormat df = new DecimalFormat("0.00");

    try {

        //special font sizes
        Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
        Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);

        //file path
        String path =  pdfFilename;
        docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));

        //open document
        doc.open();

        //create a paragraph
        Paragraph paragraph = new Paragraph("Report Data Mahasiswa.");


        //specify column widths
        float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(90f);

        //insert column headings
        insertCell(table, "Nim", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Nama", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Alamat", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Jenis Kelamin", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Hobi", Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        //just some random data to fill
        for(int x=0; x<data.getRowCount(); x++){
            insertCell(table, ""+data.getValueAt(x,0), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, ""+data.getValueAt(x,1), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, ""+data.getValueAt(x,2), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, ""+data.getValueAt(x,3), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, ""+data.getValueAt(x,4), Element.ALIGN_LEFT, 1, bf12);
        }


        //add the PDF table to the paragraph
        paragraph.add(table);
        // add the paragraph to the document
        doc.add(paragraph);

    }
    catch (DocumentException dex)
    {
        dex.printStackTrace();
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    finally
    {
        if (doc != null){
            //close the document
            doc.close();
        }
        if (docWriter != null){
            //close the writer
            docWriter.close();
        }
    }
  }
public void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

    //create a new cell with the specified Text and Font
    PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
    //set the cell alignment
    cell.setHorizontalAlignment(align);
    //set the cell column span in case you want to merge two or more cells
    cell.setColspan(colspan);
    //in case there is no text and you wan to create an empty row
    if(text.trim().equalsIgnoreCase("")){
        cell.setMinimumHeight(10f);
    }
    //add the call to the table
    table.addCell(cell);
}}
