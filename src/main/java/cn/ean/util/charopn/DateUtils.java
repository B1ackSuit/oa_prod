package cn.ean.util.charopn;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类，结合lang3的日期类，后续重构：复写lang3内的日期工具类，减耦
 *
 * @author ean
 * @FileName DateUtils
 * @Date 2022/5/18 01:49
 **/
@Component
public class DateUtils {

    /**
     * imp functional interface org.springframework.core.convert.converter.Converter< String, LocalDate >
     *
     * @param s
     */
    public LocalDate convert(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
