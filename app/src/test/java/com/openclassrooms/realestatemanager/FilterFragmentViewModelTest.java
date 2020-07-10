package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.ui.filter.FilterFragmentViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class FilterFragmentViewModelTest {

    @Test
    public void When_String_has_three_words_getFilterListInForm_return_this_three_words() {

        FilterFragmentViewModel filterFragmentViewModel = new FilterFragmentViewModel();
        String string_list = "Paris, Madrid, New Delhi";
        ArrayList<String> filter = filterFragmentViewModel.getFilterListInForm(string_list);

        Assertions.assertThat(filter.get(0)).isEqualTo("Paris");
        Assertions.assertThat(filter.get(1)).isEqualTo("Madrid");
        Assertions.assertThat(filter.get(2)).isEqualTo("New Delhi");
    }
}