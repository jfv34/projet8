package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.ui.filter.FilterFragmentViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;

public class FilterFragmentViewModelTest {

    @Test
    public void When_String_has_three_words_getFilterListInForm_return_this_three_words() {
        FilterFragmentViewModel viewModel = new FilterFragmentViewModel();
        String string_list = "Paris, Madrid, New Delhi";
        ArrayList<String> filter = viewModel.getFilterListInForm(string_list);
        boolean result = (filter.get(0).equals("Paris")
                && filter.get(1).equals("Madrid")
                && filter.get(2).equals("New Delhi"));

        Assertions.assertThat(result).isEqualTo(true);
    }
}
