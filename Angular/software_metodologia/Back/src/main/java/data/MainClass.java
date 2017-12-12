package data;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class MainClass extends JFrame {
  private JTable table;

  public MainClass() throws Exception {
    getContentPane().setLayout(new BorderLayout());

    Object[][] data = { { "A", "B", "C", new Integer(5), new Boolean(false) },
        { "D", "E", "F", new Integer(3), new Boolean(true) } };

    String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };

    table = new JTable(data, columnNames);

    JPanel tPanel = new JPanel(new BorderLayout());
    tPanel.add(table.getTableHeader(), BorderLayout.NORTH);
    tPanel.add(table, BorderLayout.CENTER);

    getContentPane().add(tPanel, BorderLayout.CENTER);

    Document document = new Document();
    PdfWriter writer;

    writer = PdfWriter.getInstance(document, new FileOutputStream("my_jtable_shapes.pdf"));

    // writer = PdfWriter.getInstance(document, new
    // FileOutputStream("my_jtable_fonts.pdf"));

    document.open();
    PdfContentByte cb = writer.getDirectContent();

    PdfTemplate tp = cb.createTemplate(500, 500);
    Graphics2D g2;

    g2 = tp.createGraphicsShapes(500, 500);

    // g2 = tp.createGraphics(500, 500);
    table.print(g2);
    g2.dispose();
    cb.addTemplate(tp, 30, 300);

    // step 5: we close the document
    document.close();
  }
}