package planets.services;

import planets.models.Planet;
import planets.models.Result;
import planets.models.XYZCoords;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Calculate {

    public static List<Result> calculateMain (List<Planet>planetList, LocalDateTime dateTime) {

        List<Result> resultList = new ArrayList<>();
        planetList.stream().forEach(planet -> {
            resultList.add(calculatePlanet(planet, null, dateTime));
            planet.getSatellites().forEach(satellite -> {
                resultList.add(calculatePlanet(satellite, planet, dateTime));
            });
        });

        return resultList;
    }

    private static Result calculatePlanet(Planet planet, Planet orbitsAround, LocalDateTime dateTime) {
        double meanA = calculateMeanAnomalyFromLongitudes(planet, dateTime);
        double trueA = calculateTrueAnomalyFromMean(meanA, planet.getEccentricity());
        double eccA = calculateEccAnomalyFromTrue(trueA, planet.getEccentricity());
        double radius = findRadiusVector(planet.getSemiMajorAxis(), planet.getEccentricity(), trueA);
        XYZCoords helioCoords = xyzCoords(radius, trueA, toRadians(planet.getLongitudeOfAscendingNode()),
                toRadians(planet.getLongitudeOfPeriapsis()), toRadians(planet.getInclination()));
        return new Result(planet, orbitsAround, meanA, eccA, trueA, radius, helioCoords, dateTime);


    }


    public static double calculateMeanAnomalyFromLongitudes(Planet planet, LocalDateTime dateTime) {
        double time_diff =  calculateJuilanDateNum(dateTime) -
                calculateJuilanDateNum(planet.getEpochTime());
        double res = (360d / planet.getOrbitalPeriod()) * time_diff +
               planet.getMeanLongitude() - planet.getLongitudeOfPeriapsis();
        res = toRadians(res);
        return fixRes(res);
     //   return res;
    }


    public static XYZCoords xyzCoords(double radius, double trueAnom, double lonAsc,
                                       double lonPer, double incl) {

        double tla = trueAnom + lonPer - lonAsc;
        double x = radius * (cos(lonAsc) * cos(tla) - sin(lonAsc) * sin(tla) * cos(incl));
        double y = radius * (sin(lonAsc) * cos(tla) + cos(lonAsc) * sin(tla) * cos(incl));
        double z = radius * (sin(tla) * sin(incl));
        return new XYZCoords(x, y, z);

    }

    public static double calculateEccAnomalyFromMean(double mean, double ecc) {
        return calculateEccAnomalyFromTrue(calculateTrueAnomalyFromMean(mean, ecc), ecc);
    }

    public static double calculateTrueAnomalyFromMean(double meanAnomaly, double eccentricity) {

        double part1 = (2d * eccentricity - pow(eccentricity, 3) / 4d) * sin(meanAnomaly);
        double part2 = 5d / 4d * pow(eccentricity, 2) * sin(meanAnomaly * 2);
        double part3 = 13d / 12d * pow(eccentricity, 3) * sin(meanAnomaly * 3);
        return fixRes(meanAnomaly + part1 + part2 + part3);

    }

    public static double calculateEccAnomalyFromTrue(double trueAnomaly, double eccentricity) {
        double res = 2 * Math.atan(Math.tan(trueAnomaly / 2) * Math.sqrt((1 - eccentricity) /
                (1 + eccentricity)));
        return fixRes(res);
    }

    public static double calculateTrueAnomalyFromEcc(double eccentricAnomaly, double eccentricity) {
        double res = 2 * Math.atan(Math.sqrt((1 + eccentricity) / (1 - eccentricity)) *
                Math.tan(eccentricAnomaly / 2));
        return fixRes(res);
    }

    public static double findRadiusVector(double semiMajorAxis, double ecc, double trueAnomaly) {
        return roundDownDecs(semiMajorAxis * (1d - pow(ecc, 2)) / (1d + ecc * cos(trueAnomaly)));
    }

    public static double roundDownDecs(double number) {
        if(number<0) return (double) Math.ceil(number * 1000d) / 1000d;
        else return (double) Math.floor(number *1000d) /  1000d;
    }

    public static double fixRes(double res) {
        if (res > 2 * PI) res %= 2 * PI;
        else while (res < 0) res += 2 * PI;
        return roundDownDecs(res);
    }

    public static double calculateJuilanDateNum(LocalDateTime local) {

        int year = local.getYear();
        int month = local.getMonthValue();
        int day = local.getDayOfMonth();
        int hour = local.getHour();
        int minute = local.getMinute();
        int second = local.getSecond();

        int jdn = (1461 * (year + 4800 + (month - 14) / 12)) / 4 + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12 -
                (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day - 32075;

        double jd = (((double) hour - 12.0) / 24) + ((double) minute / 1440) + ((double)
                second / 86400) + jdn;

        return roundDownDecs(jd);
    }
}
