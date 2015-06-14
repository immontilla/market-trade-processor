package eu.immontilla.currencyfair.markettradeprocessor.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for trade messages
 * @author immontilla
 *
 */
@Entity
@Table(name = "messages")
public class Message extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotNull
    private Long userId;

    @Column
    @NotNull
    @Size(min = 3, max = 3) // According to http://en.wikipedia.org/wiki/ISO_4217
    private String currencyFrom;

    @Column
    @NotNull
    @Size(min = 3, max = 3) // According to http://en.wikipedia.org/wiki/ISO_4217
    private String currencyTo;

    @Column
    @NotNull
    @DecimalMin("8.00") // According to https://www.currencyfair.com/support/centre/
    private BigDecimal amountSell;

    @Column
    @NotNull
    @DecimalMin("8.00") // According to https://www.currencyfair.com/support/centre/
    private BigDecimal amountBuy;

    @Column
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("1.00")
    private Float rate;

    @Column
    @NotNull
    @Type(type = "timestamp")
    private Date timePlaced;

    @Column
    @NotNull
    @Size(min = 2, max = 2) // According to http://en.wikipedia.org/wiki/ISO_3166-1 (using Alpha-2 notation)
    private String originatingCountry;
    
    @JsonProperty
    public Long getId() {
        return id;
    }
    
    @JsonProperty
    public Long getUserId() {
        return userId;
    }

    @JsonProperty
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    @JsonProperty
    public String getCurrencyTo() {
        return currencyTo;
    }

    @JsonProperty
    public BigDecimal getAmountSell() {
        return amountSell;
    }

    @JsonProperty
    public BigDecimal getAmountBuy() {
        
        return amountBuy;
    }

    @JsonProperty
    public Float getRate() {
        return rate;
    }

    @JsonProperty
    public String getTimePlaced() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        return sdf.format(timePlaced).toUpperCase();
    }

    @JsonProperty
    public String getOriginatingCountry() {
        return originatingCountry;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public void setAmountSell(BigDecimal amountSell) {
        this.amountSell = amountSell;
    }

    public void setAmountBuy(BigDecimal amountBuy) {
        this.amountBuy = amountBuy;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }

    public void setOriginatingCountry(String originatingCountry) {
        this.originatingCountry = originatingCountry;
    }
    
}
