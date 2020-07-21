package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.ui.Utils.Utils;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void convertDollarToEuro_return_81_euros_when_convert_100_dollars() {
        Assert.assertEquals(81, Utils.convertDollarToEuro(100));
    }

    @Test
    public void convertEuroToDollars_return_100_euros_when_convert_81_dollars() {
        Assert.assertEquals(100, Utils.convertEuroToDollar(81));
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
