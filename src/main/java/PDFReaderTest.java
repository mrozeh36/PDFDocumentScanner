import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PDFReaderTest {

    WebDriver driver;
    String url = "Enter Page URL";

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @Test
    public void pdfReaderTest() throws IOException {
        URL pdfUrl = new URL(url);
        InputStream ip = pdfUrl.openStream();
        BufferedInputStream bf = new BufferedInputStream(ip);
        PDDocument pdDocument = PDDocument.load(bf);

        //Get page count:
        int pageCount = pdDocument.getNumberOfPages();
        System.out.println("Page count is " + pageCount);
        Assert.assertEquals(pageCount, 5);

        System.out.println("======================Getting Page Content=================");

        //Get Page Content
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(pdDocument);
        System.out.println(pdfText);
        Assert.assertTrue(pdfText.contains("Enter words to search"));

        //Get Content From Specific Page
        pdfTextStripper.setStartPage(4);
        String pdfText1 = pdfTextStripper.getText(pdDocument);
        System.out.println(pdfText1);
        Assert.assertTrue(pdfText1.contains("Enter words to search"));
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
