package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.realestatemanager.models.Filter;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.ui.filter.FilterFragmentViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class FilterFragmentViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_String_has_three_words_getFilterListInForm_return_this_three_words() {

        FilterFragmentViewModel filterFragmentViewModel = new FilterFragmentViewModel();
        String string_list = "Paris, Madrid, New Delhi";
        ArrayList<String> filter = filterFragmentViewModel.getFilterListInForm(string_list);

        Assertions.assertThat(filter.get(0)).isEqualTo("Paris");
        Assertions.assertThat(filter.get(1)).isEqualTo("Madrid");
        Assertions.assertThat(filter.get(2)).isEqualTo("New Delhi");
    }

    @Test
    public void When_filtred_by_two_states_return_this_states() {

        FilterFragmentViewModel filterFragmentViewModel = new FilterFragmentViewModel();

        ArrayList<Property> properties = new ArrayList<>();
        Property property1 = new Property(
                1,
                "Loft",
                "1250252",
                "Amphitheatre Pkwy",
                "Mountain View",
                "United States",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property2 = new Property(
                2,
                "Loft",
                "1250252",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Japon",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property3 = new Property(
                3,
                "Loft",
                "1250252",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Canada",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property4 = new Property(
                4,
                "Loft",
                "1250252",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Spain",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        properties.add(property1);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);

        ArrayList<String> states = new ArrayList<>();
        states.add("United States");
        states.add("Canada");

        Filter filter = new Filter(
                null,
                999999999,
                0,
                null,
                states,
                999999,
                0,
                99,
                0,
                null,
                null,
                null,
                null,
                null,
                99,
                0
        );
        filterFragmentViewModel.setFilter(filter);
        filterFragmentViewModel.filterByStates(properties);
        Assert.assertEquals(properties.size(), 2);
        Assert.assertEquals(properties.get(0).getState(), "United States");
        Assert.assertEquals(properties.get(1).getState(), "Canada");
    }

    @Test
    public void When_() {

        FilterFragmentViewModel filterFragmentViewModel = new FilterFragmentViewModel();

        ArrayList<Property> properties = new ArrayList<>();
        Property property1 = new Property(
                1,
                "Loft",
                "1000",
                "Amphitheatre Pkwy",
                "Mountain View",
                "United States",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property2 = new Property(
                2,
                "Loft",
                "4000",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Japon",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property3 = new Property(
                3,
                "Loft",
                "2000",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Canada",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        Property property4 = new Property(
                4,
                "Loft",
                "3000",
                "Amphitheatre Pkwy",
                "Mountain View",
                "Spain",
                "CA94043",
                "170",
                "7",
                "Woodland Park and Houston Zoo",
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null,
                Status.AVAILABLE,
                "12/01/2020",
                "4/04/2020",
                "Jordan SMITH");

        properties.add(property1);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);


        Filter filter = new Filter(
                null,
                3500,
                1500,
                null,
                null,
                999999,
                0,
                99,
                0,
                null,
                null,
                null,
                null,
                null,
                99,
                0
        );
        filterFragmentViewModel.setFilter(filter);
        filterFragmentViewModel.filterByPrice(properties);
        Assert.assertEquals(properties.size(), 2);
        Assert.assertEquals(properties.get(0).getPrice(),2000);
        Assert.assertEquals(properties.get(1).getPrice(),3000);
    }
}