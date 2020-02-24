package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PropertyDaoTest {

    private PropertyDataBase propertyDataBase;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.propertyDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                PropertyDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        propertyDataBase.close();
    }

    // DATA SET FOR TEST
    private static Property NEW_PROPERTY_NEW_YORK = new Property(1001, "House", "Newy York", 13525205, "Magnificent architect-designed house of 353m² on 5 levels with lift and roof terrace, bright, contemporary style and furnished with quality materials. Ground floor: entrance, living room with ethanol fireplace, dining room, semi-open kitchen, mezzanine office. 1st floor: parental suite of 65m² with office. 2nd floor: 2 bedrooms, 2 bathrooms. 3rd floor: 2 bedrooms, 2 bathrooms and sauna. 4th floor: 70 m² tree-shaded terrace. Basement: cellar, wine cellar, laundry room and technical room. General air conditioning. Possibility of acquiring a box in supplement.");
    private static Property NEW_PROPERTY_LOS_ANGELES = new Property(1002, "House", "Los Angeles", 36353949, "In the immediate vicinity of Montsouris Park, superb 195m² house, on 4 levels, recently renovated, having kept all its character and with a garden-terrace and beautiful light with south-west exposure. Ground floor: office open onto the terrace. 1st floor: large living room with fireplace, fully fitted open kitchen. 2nd floor: master bedroom, dressing room, bathroom and shower. On the 3rd floor: 2 bedrooms. On the 4th floor: large bedroom. 3 toilets. Laundry. Reversible climate control.");


    @Test
    public void getPropertyWhenNoPropertyInserted() throws InterruptedException {
        // TEST
        List<Property> items = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties());
        assertTrue(items.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException {
        // BEFORE : Adding demo property

        this.propertyDataBase.propertyDao().insertProperty(NEW_PROPERTY_NEW_YORK);
        this.propertyDataBase.propertyDao().insertProperty(NEW_PROPERTY_LOS_ANGELES);

        // TEST
        List<Property> properties = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties());
        assertTrue(properties.size() == 2);
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo property. Next, update property added & re-save it
        this.propertyDataBase.propertyDao().insertProperty(NEW_PROPERTY_NEW_YORK);
        Property propertyAdded = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties()).get(0);
        propertyAdded.setPrice(123456789);
        this.propertyDataBase.propertyDao().updateProperty(propertyAdded);

        //TEST
        List<Property> properties = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties());
        assertTrue(properties.size() == 1 && properties.get(0).getPrice() == 123456789);

    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo property. Next, get the property added & delete it.
        this.propertyDataBase.propertyDao().insertProperty(NEW_PROPERTY_LOS_ANGELES);
        Property propertyAdded = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties()).get(0);

        this.propertyDataBase.propertyDao().deleteProperty(propertyAdded.getId());

        //TEST
        List<Property> properties = LiveDataTestUtil.getValue(this.propertyDataBase.propertyDao().getProperties());
        assertTrue(properties.isEmpty());
    }


}

