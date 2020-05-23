package planets;

import com.google.gson.JsonSyntaxException;
import planets.models.Planet;
import planets.models.Result;
import planets.services.Calculate;
import planets.services.IOUtils;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            List<Planet> planetList = IOUtils.getPlanetsFromFile(".\\input.json");
            LocalDateTime dateTime = LocalDateTime.of(2005, 6, 21, 0, 0);
            List<Result> resultList = Calculate.calculateMain(planetList, dateTime);
            resultList.forEach(System.out::println);

        } catch (JsonSyntaxException jsonSyntaxEx) {
            System.out.println(jsonSyntaxEx);
        }
    }

}
