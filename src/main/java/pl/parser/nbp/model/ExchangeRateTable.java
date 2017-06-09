package pl.parser.nbp.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class ExchangeRateTable {

    private String tableNo;
    private LocalDate noteDate;
    private LocalDate publishDate;
    private List<DayCurrency> dayCurrencyList;

    public ExchangeRateTable(String tableNo, LocalDate noteDate, LocalDate publishDate, List<DayCurrency> dayCurrencyList) {
        this.tableNo = tableNo;
        this.noteDate = noteDate;
        this.publishDate = publishDate;
        this.dayCurrencyList = dayCurrencyList;
    }

    public String getTableNo() {
        return tableNo;
    }

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public List<DayCurrency> getDayCurrencyList() {
        return dayCurrencyList;
    }

    @Override
    public String toString() {
        return "ExchangeRateTable{" +
                "tableNo='" + tableNo + '\'' +
                ", noteDate=" + noteDate +
                ", publishDate=" + publishDate +
                ", dayCurrencyList=" + dayCurrencyList +
                '}';
    }
}
