package com.openclassrooms.realestatemanager;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void When_euro_100000_return_123153_dollars() {
      int result = Utils.convertEuroToDollar(100000);
        Assertions.assertThat(result).isEqualTo(123153);
    }

    @Test
    public void Return_date_in_DD_MM_YYYY_format() {
        String initialFormat = Utils.getTodayDate();
        String expectedFormat = Utils.getDateinDayMonthYearFormat();
        boolean result = true;
        if(!initialFormat.substring(0,4).equals(expectedFormat.substring(6,10))){result=false;}
        if(!initialFormat.substring(5,7).equals(expectedFormat.substring(3,5))){result=false;}
        if(!initialFormat.substring(8,10).equals(expectedFormat.substring(0,2))){result=false;}
        if(!expectedFormat.substring(2,3).equals("/")){result=false;}
        if(!expectedFormat.substring(5,6).equals("/")){result=false;}

        Assertions.assertThat(result).isTrue();
    }



}
