package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

@Database(entities = {Property.class}, version = 1, exportSchema = false)
public abstract class PropertyDataBase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile PropertyDataBase INSTANCE;

    // --- DAO ---
    public abstract PropertyDao propertyDao();

    // --- INSTANCE ---
    public static PropertyDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PropertyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PropertyDataBase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("type", "Flat");
                contentValues.put("city", "Houston");
                contentValues.put("price", "17250259");
                contentValues.put("description", "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.");
                contentValues.put("id", 2);
                contentValues.put("type", "Duplex");
                contentValues.put("city", "San Francisco");
                contentValues.put("price","2439531");
                contentValues.put("description", "Exceptional 8-room townhouse of 353 m² with roof terrace of 70 m². On 4 levels, this house renovated with passion and taste by its owners consists of: - A basement: cellar, wine cellar, laundry room and technical room. - On the ground floor: Entrance, living room with ethanol fireplace, dining room, semi-open kitchen, reception mezzanine. - On the 1st floor: a large parental space of 65 m² with office - On the 2nd and 3rd floor: 2 bedrooms and 2 bathrooms All served by an elevator giving access to an exceptional roof terrace with trees of over 70 m². Air conditioning, sauna / hammam, very neat decoration with luxurious materials make this house an exceptional property. Shops, very good schools, 3 metro lines nearby. BOX Possible optional price. CONTACT: Arnaud LAVIGNE of which 2.57% fees including VAT borne by the buyer.");

            }
        };
    }

}
