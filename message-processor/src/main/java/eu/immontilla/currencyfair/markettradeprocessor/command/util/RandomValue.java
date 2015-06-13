package eu.immontilla.currencyfair.markettradeprocessor.command.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class RandomValue {
    // Current currency available list taken from https://www.currencyfair.com/
    private static String[] currencies = { "AED", "AUD", "CAD", "CHF", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "NOK", "NZD", "PLN", "SEK", "USD", "ZAR" };

    // Country codes list' taken from http://countrycode.org filtered in accordance with https://www.currencyfair.com/support/centre/ (What countries can I transfer to?)
    private static String[] countries = { "AD", "AE", "AG", "AI", "AM", "AS", "AT", "AU", "AW", "BB", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN",
            "BQ", "BR", "BS", "BT", "BW", "CA", "CG", "CH", "CK", "CL", "CM", "CN", "CO", "CR", "CV", "CW", "CY", "CZ", "DE", "DJ", "DK", "EE", "ES", "FI",
            "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GP", "GQ", "GR", "GU", "HK", "HR", "HU", "IE", "IL", "IM", "IN",
            "IS", "IT", "JE", "JM", "JO", "JP", "KI", "KN", "KR", "KY", "KZ", "LC", "LI", "LK", "LS", "LT", "LU", "LV", "MC", "MF", "MG", "MH", "ML", "MO",
            "MQ", "MR", "MS", "MT", "MU", "MV", "MX", "MY", "MZ", "NC", "NE", "NL", "NO", "NR", "NU", "NZ", "OM", "PE", "PF", "PH", "PL", "PM", "PN", "PR",
            "PT", "PW", "QA", "RE", "RO", "RW", "SA", "SB", "SC", "SE", "SG", "SH", "SI", "SJ", "SK", "SM", "SN", "SO", "SR", "SV", "SX", "SZ", "TC", "TD",
            "TG", "TH", "TK", "TL", "TM", "TO", "TT", "TV", "TW", "US", "UY", "UZ", "VA", "VC", "VG", "VI", "VN", "VU", "WF", "WS", "YT", "ZA", "ZM" };

    public static int get(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static float get(float min, float max) {
        Random rand = new Random();
        float randomNum = rand.nextFloat() * (max - min) + min;
        return randomNum;
    }

    public static BigDecimal get(double min, double max) {
        Random rand = new Random();
        double randomNum = rand.nextDouble() * (max - min) + min;
        return (new BigDecimal(randomNum).setScale(2, BigDecimal.ROUND_UP));
    }

    public static float getPercentage(double min, double max) {
        return get(min, max).floatValue();
    }

    public static String getCurrency(String differentThan) {
        String value = "";
        if (differentThan != null) {
            do {
                value = currencies[get(0, (currencies.length - 1))];
            }
            while (value.equalsIgnoreCase(differentThan));
        }
        else {
            value = currencies[get(0, (currencies.length - 1))];
        }
        return value;
    }

    public static String getCountry() {
        return countries[get(0, (countries.length - 1))];
    }

    public static Date getDate(String startDate, String endDate) {
        long offset = Timestamp.valueOf(startDate).getTime();
        long end = Timestamp.valueOf(endDate).getTime();
        long diff = end - offset + 1;
        long rand = offset + (long) (Math.random() * diff);
        return new Date(rand);
    }

    public static Date getDate(int days) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.add(Calendar.DATE, -1);
        Date endDate = new Date(now.getTimeInMillis());
        now.add(Calendar.DATE, (days * -1));
        Date startDate = new Date(now.getTimeInMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return getDate(sdf.format(startDate), sdf.format(endDate));
    }

}
