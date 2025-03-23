package util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils
{
    public static String generateNumber(int totalDigits)
    {
        long randomNumber;
        String returnValue = null;

        if(totalDigits == 10)
        {
            randomNumber = (long) (Math.random() * 1_0000_0000_00L);
            returnValue = String.format("%010d", randomNumber);
        }
        else if(totalDigits == 16)
        {
            randomNumber = (long) (Math.random() * 1_0000_0000_0000_0000L);
            returnValue = String.format("%016d", randomNumber);
        }

        return returnValue;
    }

    public static Date getSqlDate(String date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return Date.valueOf(localDate);
    }
}
