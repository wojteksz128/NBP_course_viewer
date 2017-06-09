package pl.parser.nbp.model;

import java.math.BigDecimal;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class DayCurrency {

    private String name;
    private BigDecimal converter;
    private String code;
    private BigDecimal purchaseRate;
    private BigDecimal sellingRate;

    public DayCurrency(String name, BigDecimal converter, String code, BigDecimal purchaseRate, BigDecimal sellingRate) {
        this.name = name;
        this.converter = converter;
        this.code = code;
        this.purchaseRate = purchaseRate;
        this.sellingRate = sellingRate;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getConverter() {
        return converter;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPurchaseRate() {
        return purchaseRate;
    }

    public BigDecimal getSellingRate() {
        return sellingRate;
    }

    @Override
    public String toString() {
        return "DayCurrency{" +
                "name='" + name + '\'' +
                ", converter=" + converter +
                ", code='" + code + '\'' +
                ", purchaseRate=" + purchaseRate +
                ", sellingRate=" + sellingRate +
                '}';
    }
}
