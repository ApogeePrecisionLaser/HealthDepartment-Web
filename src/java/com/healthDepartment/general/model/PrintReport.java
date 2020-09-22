/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.general.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintLine;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
//import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;


/**
 *
 * @author jpss
 */
public class PrintReport {
      public void fill() throws JRException
  {
    long start = System.currentTimeMillis();
    JasperPrint jasperPrint = getJasperPrint();
    JRSaver.saveObject(jasperPrint, "build/reports/PrintServiceReport.jrprint");
    System.err.println("Filling time : " + (System.currentTimeMillis() - start));
  }

  private static JasperPrint getJasperPrint() throws JRException
  {
    //JasperPrint
    JasperPrint jasperPrint = new JasperPrint();
    jasperPrint.setName("NoReport");
    jasperPrint.setPageWidth(595);
    jasperPrint.setPageHeight(842);

    //Fonts
    JRDesignStyle normalStyle = new JRDesignStyle();
    normalStyle.setName("Sans_Normal");
    normalStyle.setDefault(true);
    normalStyle.setFontName("DejaVu Sans");
    normalStyle.setFontSize(8);
    normalStyle.setPdfFontName("Helvetica");
    normalStyle.setPdfEncoding("Cp1252");
    normalStyle.setPdfEmbedded(false);
    jasperPrint.addStyle(normalStyle);

    JRDesignStyle boldStyle = new JRDesignStyle();
    boldStyle.setName("Sans_Bold");
    boldStyle.setFontName("DejaVu Sans");
    boldStyle.setFontSize(8);
    boldStyle.setBold(true);
    boldStyle.setPdfFontName("Helvetica-Bold");
    boldStyle.setPdfEncoding("Cp1252");
    boldStyle.setPdfEmbedded(false);
    jasperPrint.addStyle(boldStyle);

    JRDesignStyle italicStyle = new JRDesignStyle();
    italicStyle.setName("Sans_Italic");
    italicStyle.setFontName("DejaVu Sans");
    italicStyle.setFontSize(8);
    italicStyle.setItalic(true);
    italicStyle.setPdfFontName("Helvetica-Oblique");
    italicStyle.setPdfEncoding("Cp1252");
    italicStyle.setPdfEmbedded(false);
    jasperPrint.addStyle(italicStyle);

    JRPrintPage page = new JRBasePrintPage();

    JRPrintLine line = new JRBasePrintLine(jasperPrint.getDefaultStyleProvider());
    line.setX(40);
    line.setY(50);
    line.setWidth(515);
    line.setHeight(0);
    page.addElement(line);

    JRPrintImage image = new JRBasePrintImage(jasperPrint.getDefaultStyleProvider());
    image.setX(45);
    image.setY(55);
    image.setWidth(165);
    image.setHeight(40);
    image.setScaleImage(ScaleImageEnum.CLIP);
    image.setRenderer(
      JRImageRenderer.getInstance(
        JRLoader.loadBytesFromResource("jasperreports.png")
        )
      );
    page.addElement(image);

    JRPrintText text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
    text.setX(210);
    text.setY(55);
    text.setWidth(345);
    text.setHeight(30);
    text.setTextHeight(text.getHeight());
    text.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
    text.setLineSpacingFactor(1.3133681f);
    text.setLeadingOffset(-4.955078f);
    text.setStyle(boldStyle);
    text.setFontSize(18);
    text.setText("JasperReports Project Description");
    page.addElement(text);

    text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
    text.setX(210);
    text.setY(85);
    text.setWidth(325);
    text.setHeight(15);
    text.setTextHeight(text.getHeight());
    text.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
    text.setLineSpacingFactor(1.329241f);
    text.setLeadingOffset(-4.076172f);
    text.setStyle(italicStyle);
    text.setFontSize(12);
    text.setText((new SimpleDateFormat("EEE, MMM d, yyyy")).format(new Date()));
    page.addElement(text);

    text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
    text.setX(40);
    text.setY(150);
    text.setWidth(515);
    text.setHeight(200);
    text.setTextHeight(text.getHeight());
    text.setHorizontalAlignment(HorizontalAlignEnum.JUSTIFIED);
    text.setLineSpacingFactor(1.329241f);
    text.setLeadingOffset(-4.076172f);
    text.setStyle(normalStyle);
    text.setFontSize(14);
    text.setText(
      "JasperReports is a powerful report-generating tool that has the ability to deliver "+
      "rich content onto the screen, to the printer or into PDF, HTML, XLS, CSV or XML files.\n\n" +
      "It is entirely written in Java and can be used in a variety of Java enabled applications, " +
      "including J2EE or Web applications, to generate dynamic content.\n\n" +
      "Its main purpose is to help creating page oriented, ready to print documents in a simple and flexible manner."
      );
    page.addElement(text);

    jasperPrint.addPage(page);

    return jasperPrint;
  }

//Once saved the PrintServiceReport.jrprint file, it will be loaded and sent to the available printer when calling the print() method. A print dialog will popup before sending the print job:

  public void print(JasperPrint jasperPrint) throws JRException
  {
    long start = System.currentTimeMillis();
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    printRequestAttributeSet.add(MediaSizeName.ISO_A4);

    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
    //printServiceAttributeSet.add(new PrinterName("Epson Stylus 820 ESC/P 2", null));
    printServiceAttributeSet.add(new PrinterName("\\\\navitus2-pc\\hp officejet 5600 series", null));
    //printServiceAttributeSet.add(new PrinterName("PDFCreator", null));

    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
   // exporter.setParameter(JRExporterParameter., start)
    
	//exporter.setExporterInput(new SimpleExporterInput("build/reports/PrintServiceReport.jrprint"));
//	SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
//	configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
//	configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
//	configuration.setDisplayPageDialog(false);
//	configuration.setDisplayPrintDialog(true);
//	exporter.setConfiguration(configuration);
	exporter.exportReport();

    System.err.println("Printing time : " + (System.currentTimeMillis() - start));
  }

  /*public void print() throws JRException {
  PrintRequestAttributeSet printRequestAttributeSet=new HashPrintRequestAttributeSet();
  if (config.hasCopies()) {
    printRequestAttributeSet.add(new Copies(config.getCopies().intValue()));
  }
  PrintServiceAttributeSet printServiceAttributeSet=new HashPrintServiceAttributeSet();
  JRPrintServiceExporter exporter=new JRPrintServiceExporter();
  if (config.hasReportName()) {
    jasperPrint.setName(config.getReportName());
  }
  exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
  SimplePrintServiceExporterConfiguration expConfig=new SimplePrintServiceExporterConfiguration();
  if (config.hasPrinterName()) {
    String printerName=config.getPrinterName();
    PrintService service=Printerlookup.getPrintservice(printerName,Boolean.TRUE,Boolean.TRUE);
    expConfig.setPrintService(service);
    if (config.isVerbose()) {
      System.out.println("printer-name: " + ((service == null) ? "No printer found with name \"" + printerName + "\"! Using default." : "found: " + service.getName()));
    }
  }
  expConfig.setPrintRequestAttributeSet(printRequestAttributeSet);
  expConfig.setPrintServiceAttributeSet(printServiceAttributeSet);
  expConfig.setDisplayPageDialog(Boolean.FALSE);
  if (config.isWithPrintDialog()) {
    setLookAndFeel();
    expConfig.setDisplayPrintDialog(Boolean.TRUE);
  }
 else {
    expConfig.setDisplayPrintDialog(Boolean.FALSE);
  }
  exporter.setConfiguration(expConfig);
  exporter.exportReport();
}*/

}
