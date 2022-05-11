package dev.vfcardoso.poc.helper.arch.utility;

import dev.vfcardoso.poc.helper.arch.exception.UtilityException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:02
 */
public class ConvertUtils {

    //<editor-fold desc="[toString]">
    public static String listToString(List<Long> lista, String sep) {
        String ret = new String();
        for (int i = 0; i < lista.size(); i++) {
            ret += String.valueOf(lista.get(i));
            if (lista.size() != i) {
                ret += sep;
            }
        }
        return ret;
    }

    public static String escapeToHtml(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for( char c : s.toCharArray() ) {
            if( c == ' ' ) {
                if( previousWasASpace ) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch(c) {
                case '<': builder.append("&lt;"); break;
                case '>': builder.append("&gt;"); break;
                case '&': builder.append("&amp;"); break;
                case '"': builder.append("&quot;"); break;
                case '\n': builder.append("<br>"); break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;
                default:
                    if( c < 128 ) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int)c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    public static String convertStringToSafeFilename(String s) {
        String ret = s.toLowerCase();
        ret = ret.replaceAll("[\\\\\\/\\:\\*\\?\\\"\\<\\>\\|\\s\\,]", "-");
        return ret;
    }

    public static String throwableToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static String getFormattedNumber(Double number, int decimals, Locale locale) {
        return ConvertUtils.getFormattedNumber(number, decimals, locale, RoundingMode.HALF_EVEN);
    }

    public static String getFormattedNumber(Double number, int decimals, Locale locale, RoundingMode mode) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMaximumFractionDigits(decimals);
        nf.setMinimumFractionDigits(decimals);
        nf.setRoundingMode(mode);
        return nf.format(number);
    }
    //</editor-fold>

    //<editor-fold desc="[toLocalDateTime]">
    public static LocalDateTime parseDate(String maybeDate, String format) {
        LocalDateTime dt;
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
            DateTime dtTime = fmt.parseDateTime(maybeDate);
            dt = dtTime.toLocalDateTime();
        } catch (IllegalArgumentException e) {
            throw new UtilityException("Data inválida! (data: " + maybeDate + "; formato: " + format + ")");
        }
        return dt;
    }


    //</editor-fold>

    //<editor-fold desc="[toLocalDate]">
    public static LocalDate parseDateSavingTimeProof(String maybeDate, String format) {
        try {
            String dateStripped = maybeDate.split(" ")[0] + " 01:00:00";
            String formatStripped = format.split(" ")[0] + " HH:mm:ss";
            DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStripped);
            DateTime dtTime = fmt.parseDateTime(dateStripped);
            return dtTime.toLocalDate();
        } catch (IllegalArgumentException e) {
            throw new UtilityException("Data inválida! (data: " + maybeDate + "; formato: " + format + ")");
        }

    }
    //</editor-fold>

    //<editor-fold desc="[toDouble]">
    public static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
    //</editor-fold>

    //<editor-fold desc="[toInteger]">
    public static int toInteger(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return bd.intValue();
    }
    //</editor-fold>

    @SuppressWarnings("unchecked")
    public static <T> T convertTo(Object object, Class<T> type) {
        try {
            if(object == null) {
                return null;
            } else if (type.equals(String.class)) {
                return (T) String.valueOf(object);
            } else if (type.equals(Double.class)) {
                return (T) Double.valueOf(String.valueOf(object));
            } else if (type.equals(Integer.class)) {
                return (T) Integer.valueOf(String.valueOf(object));
            } else if (type.equals(LocalDateTime.class)) {
                return (T) ConvertUtils.parseDate(String.valueOf(object), "yyyy-MM-dd HH:mm:ss.S");
            } else if (type.equals(LocalDate.class)) {
                return (T) ConvertUtils.parseDateSavingTimeProof(String.valueOf(object), "yyyy-MM-dd HH:mm:ss.S");
            } else if (type.equals(Long.class)) {
                return (T) Long.valueOf(String.valueOf(object));
            } else if (type.equals(Boolean.class)) {
                return (T) Boolean.valueOf(String.valueOf(object).trim());
            } else {
                throw new UtilityException("Tipo não mapeado no método de conversão: [" + type.getSimpleName() + "]");
            }
        } catch (NumberFormatException | UtilityException e) {
            throw new UtilityException("Não foi possível converter", e);
        }
    }
}