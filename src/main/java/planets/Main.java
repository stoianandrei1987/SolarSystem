package planets;

import planets.models.Planet;
import planets.models.Result;
import planets.services.Calculate;
import planets.services.IOUtils;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Planet> planetList = IOUtils.getPlanetsFromFile(".\\input.json");
        LocalDateTime dateTime = LocalDateTime.of(1997, 6, 21, 0, 0);
        List<Result> resultList =  Calculate.calculateMain(planetList, dateTime);
        resultList.forEach(System.out::println);

        }
    }

