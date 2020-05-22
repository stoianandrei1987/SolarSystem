import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {
    static String mercury = "Mercury 0.38709893 0.20563069 7.00487 48.33167 77.45645 252.25084 88.0";
    static String venus = "Venus 0.72333199 0.00677323 3.39471 76.68069 131.53298 181.97973 224.7";
    static String earth = "Earth 1.00000011 0.01671022 0.00005 -11.26064 102.94719 100.46435 365.25";
    static String mars = "Mars 1.52366231 0.09341233 1.85061 49.57854 336.04084 355.45332 687.0";

    /*bad value
    static String mars = "Mars 1.52366231 0.09341233 1.85061 49.57854 336.04084 262.42784 687.0";


     */
    static String jupiter = "Jupiter 5.20336301 0.04839266 1.30530 100.55615 14.75385 34.40438 4331.0";
    static String saturn = "Saturn 9.53707032 0.05415060 2.48446 113.71504 92.43194 49.94432 10747.0";
    static String uranus = "Uranus 19.19126393 0.04716771 0.76986 74.22988 170.96424 313.23218 30589.0";
    static String neptune = "Neptune 30.06896348 0.00858587 1.76917 131.72169 44.97135 304.88003 59800.0";
    static String pluto = "Pluto 39.48168677 0.24880766 17.14175 110.30347 224.06676 238.92881 90560.0";
    static LocalDateTime epochJ2000 = LocalDateTime.of(2000, 1, 1, 12, 0, 0);

    /*
    static LocalDateTime epochJ2000 = LocalDateTime.of(1997, 8, 20, 0, 0);

     */

    public static void main(String[] args) {
        String[] strings = {mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto};
        List<Planet> planetList =  new ArrayList<>();

        for(String str : strings) {
            String[] words = str.split(" ");
            String name = words[0];
            Double semimajor = Double.valueOf(words[1]),
                    ecc = Double.valueOf(words[2]),
                    inclination = Double.valueOf(words[3]),
                    longitudeOfAscending = Double.valueOf(words[4]),
                    longitudeOfPeriapsis = Double.valueOf(words[5]),
                    meanLongitude = Double.valueOf(words[6]),
                    orbitalPeriod = Double.valueOf(words[7]);

            Planet p = new Planet(name, epochJ2000, inclination, semimajor, ecc, orbitalPeriod,longitudeOfAscending,
                    longitudeOfPeriapsis, meanLongitude, PlanetType.PLANET);
            planetList.add(p);
        }

        for(Planet pl : planetList) {
            System.out.println(pl);
        }

        Type listType = new TypeToken<List<Planet>>() {}.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File f = new File(".\\input.json");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = gson.toJson(planetList, listType);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
