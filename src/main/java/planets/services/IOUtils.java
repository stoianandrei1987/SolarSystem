package planets.services;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import planets.models.Planet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

    public static List<Planet> getPlanetsFromFile(String path) throws JsonSyntaxException, JsonIOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Type listType = new TypeToken<List<Planet>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(br, listType);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

}
