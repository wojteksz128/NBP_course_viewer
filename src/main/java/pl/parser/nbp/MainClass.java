package pl.parser.nbp;

import pl.parser.nbp.util.XMLFilesDownloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println(XMLFilesDownloader.downloadXMLsBetweenDates(LocalDate.now(), LocalDate.now()));
    }
}
