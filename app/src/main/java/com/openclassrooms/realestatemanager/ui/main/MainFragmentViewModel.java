package com.openclassrooms.realestatemanager.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;

public class MainFragmentViewModel extends ViewModel {

    MutableLiveData<ArrayList<Property>> properties = new MutableLiveData<>();

    public void loadProperties() {

        ArrayList<Property> results = new ArrayList<>();

        Property property_test_1 = new Property(
                1,
                "Flat",
                "Houston",
                17250259,
                "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.",
                null
        );

        Property property_test_2 = new Property(
                2,
                "Duplex",
                "San Francisco",
                2439531,
                "Exceptional 8-room townhouse of 353 m² with roof terrace of 70 m². On 4 levels, this house renovated with passion and taste by its owners consists of: - A basement: cellar, wine cellar, laundry room and technical room. - On the ground floor: Entrance, living room with ethanol fireplace, dining room, semi-open kitchen, reception mezzanine. - On the 1st floor: a large parental space of 65 m² with office - On the 2nd and 3rd floor: 2 bedrooms and 2 bathrooms All served by an elevator giving access to an exceptional roof terrace with trees of over 70 m². Air conditioning, sauna / hammam, very neat decoration with luxurious materials make this house an exceptional property. Shops, very good schools, 3 metro lines nearby. BOX Possible optional price. CONTACT: Arnaud LAVIGNE of which 2.57% fees including VAT borne by the buyer.",
                null);

        results.add(property_test_1);
        results.add(property_test_2);
        results.add(property_test_1);
        results.add(property_test_2);
        results.add(property_test_1);
        results.add(property_test_2);
        results.add(property_test_1);
        results.add(property_test_2);

        properties.setValue(results);

    }
    }

