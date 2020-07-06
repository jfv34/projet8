package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.ui.filter.FilterFragmentViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class FilterFragmentViewModelTest {

    @Before

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void When_String_has_three_words_getFilterListInForm_return_this_three_words() {

        FilterFragmentViewModel filterFragmentViewModel = Mockito.mock(FilterFragmentViewModel.class);
        String string_list = "Paris, Madrid, New Delhi";
        Mockito.when(filterFragmentViewModel.getFilterListInForm(string_list)).thenCallRealMethod();
        ArrayList<String> filter = filterFragmentViewModel.getFilterListInForm(string_list);
        boolean result = (filter.get(0).equals("Paris")
                && filter.get(1).equals("Madrid")
                && filter.get(2).equals("New Delhi"));
        Assertions.assertThat(result).isEqualTo(true);
    }
}