package pl.parser.nbp.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class XMLFilesDownloader {

    private static final String EXCHANGE_RATE_URL_PART = "http://www.nbp.pl/kursy/xml/";
    private static final String DIR_FILENAME = "dir";
    private static final String DIR_EXTENSION = ".txt";

    public static List<File> downloadXMLsBetweenDates(LocalDate beginDate, LocalDate endDate) throws IOException {
        List<String> fileNames = getFileNames(downloadDirFilesWithFileNames(beginDate, endDate), beginDate, endDate);

        return null;
    }

    private static List<String> getFileNames(List<URL> dirFiles, LocalDate beginDate, LocalDate endDate) throws IOException {
        List<String> fileNames = new ArrayList<>();

        for (URL dirFile : dirFiles) {
            BufferedReader br = new BufferedReader(new InputStreamReader(dirFile.openStream()));

            fileNames.addAll(br.lines().parallel().filter(str -> {
                if (!str.startsWith("c")) {
                    return false;
                }
                LocalDate date = LocalDate.parse(str.substring(5, 11), DateTimeFormatter.ofPattern("yyMMdd"));
                return (date.isAfter(beginDate) || date.isEqual(beginDate)) && (date.isBefore(endDate) || date.isEqual(endDate));
            }).collect(Collectors.toList()));
        }

        System.err.println(fileNames);
        return fileNames;
    }

    private static List<URL> downloadDirFilesWithFileNames(LocalDate beginDate, LocalDate endDate) throws MalformedURLException {
        List<URL> files = new ArrayList<>();

        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Begin date (" + beginDate + ") is after end date (" + endDate + ").");
        }

        if (beginDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Begin date is after current date.");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date is after current date.");
        }

        Year currYear = Year.from(beginDate);

        while (!currYear.isAfter(Year.from(endDate))) {
            files.add(new URL(EXCHANGE_RATE_URL_PART + DIR_FILENAME + (currYear.equals(Year.now()) ? "" : currYear) + DIR_EXTENSION));
            currYear = currYear.plusYears(1);
        }

        return files;
    }
}
