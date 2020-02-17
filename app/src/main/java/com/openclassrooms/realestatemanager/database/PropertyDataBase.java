package com.openclassrooms.realestatemanager.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

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
                contentValues.put("city", "Lyon");
                contentValues.put("price","1250259");
                contentValues.put("description","Située dans un charmant passage, splendide maison de caractère où les styles s'harmonisent parfaitement avec les espaces extérieurs: jardin 22 m² et terrasse 16 m² magnifiquement végétalisés. Rez-de-jardin: vaste espace de vie doté d'une cuisine ouverte sur le jardin, salon cathédrale avec cheminée, hauteur sous plafond de plus de 4,5 m, salon télévision en mezzanine. 1er étage: 2 grandes chambres, salle d'eau et bureau. 2ème étage: suite parentale donnant sur terrasse, salle de bains et dressing, toilettes, buanderie. Cave. Double garage intégré. ");

                contentValues.put("id", 2);
                contentValues.put("type", "Duplex");
                contentValues.put("city", "Bordeaux");
                contentValues.put("price","2439531");
                contentValues.put("description","Maison de Ville exceptionnelle de 8 pièces de 353 m² avec toit terrasse de 70 m². Sur 4 niveaux, cette maison rénovée avec passion et goût par ses propriétaires se compose: - D'un sous-sol: cave, cave à vin, buanderie et local technique. - Au rez-de-chaussée: Une entrée, un salon avec cheminée à l'éthanol, une salle à manger, une cuisine semi-ouverte, une mezzanine de réception. - Au 1er étage: un vaste espace parental de 65 m² avec bureau - Au 2ème et 3ème étage: 2 chambres et 2 salles de bains Le tout desservi par un ascenseur donnant accès à un exceptionnel toit terrasse arboré de plus de 70 m². Climatisation, sauna/hammam, décoration très soignée avec matériaux luxueux font de cette maison un bien d'exception. Commerces, très bonnes écoles, 3 lignes de métro à proximité. BOX Possible en option du prix. CONTACT: Arnaud LAVIGNE dont 2.57 % honoraires TTC à la charge de l'acquéreur.");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}
