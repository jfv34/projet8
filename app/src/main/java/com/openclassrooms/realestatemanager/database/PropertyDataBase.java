package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

@Database(entities = {Property.class}, version = 4, exportSchema = false)

@TypeConverters({Converters.class})
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

                ContentValues contentValues_1 = new ContentValues();
                contentValues_1.put("id", 1);
                contentValues_1.put("type", "Flat");
                contentValues_1.put("price", "1250252");
                contentValues_1.put("address", "603 Bayland Avenue");
                contentValues_1.put("city","Houston");
                contentValues_1.put("state","United States");
                contentValues_1.put("zip","TX 77009");
                contentValues_1.put("area","170");
                contentValues_1.put("pieces","7");
                contentValues_1.put("interestPoint","Woodland Park and Houston Zoo");
                contentValues_1.put("description", "Located in a charming passage, splendid house of character where styles harmonize perfectly with the outdoor spaces: 22 m² garden and 16 m² terrace beautifully planted. Garden level: large living space with a kitchen opening onto the garden, cathedral lounge with fireplace, ceiling height over 4.5m, TV lounge on the mezzanine. 1st floor: 2 large bedrooms, shower room and office. 2nd floor: master bedroom opening onto terrace, bathroom and dressing room, toilet, utility room. Cellar. Double integrated garage.");
                contentValues_1.put("photos", "");
                contentValues_1.put("solded", false);
                contentValues_1.put("entryDate", "12/01/20");
                contentValues_1.put("saleDate","");
                contentValues_1.put("agentName","Jordan SMITH");


                ContentValues contentValues_2 = new ContentValues();
                contentValues_2.put("id", 2);
                contentValues_2.put("type", "Duplex");
                contentValues_2.put("price", "34725029");
                contentValues_2.put("address", "2799 Broadway");
                contentValues_2.put("city","San Francisco");
                contentValues_2.put("state","United States");
                contentValues_2.put("zip","CA 94115");
                contentValues_2.put("area","1077");
                contentValues_2.put("pieces","15");
                contentValues_2.put("interestPoint","Crissy Field and Baker Beach");
                contentValues_2.put("description", "On a corner lot overlooking the city and the bay, a rare opportunity to own a tailor-made residence in a world class location. The centerpiece of the house is a spectacular serpentine staircase with ornate handrails designed by Italian artisans.");
                contentValues_2.put("photos","");
                contentValues_2.put("solded", true);
                contentValues_2.put("entryDate", "");
                contentValues_2.put("saleDate","05/03/20");
                contentValues_2.put("agentName","Michael BROWN");

                db.insert("Property", OnConflictStrategy.IGNORE,contentValues_1);
                db.insert("Property", OnConflictStrategy.IGNORE,contentValues_2);

            }
        };
    }
}