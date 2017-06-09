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
}
