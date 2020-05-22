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
        XYZCoords helioCoords = helioCoordPlanet(radius, trueA, toRadians(planet.getLongitudeOfAscendingNode()),
                toRadians(planet.getLongitudeOfPeriapsis()), toRadians(planet.getInclination()));
        return new Result(planet, orbitsAround, meanA, eccA, trueA, radius, helioCoords, dateTime);


    }


    private static double calculateMeanAnomalyFromLongitudes(Planet planet, LocalDateTime dateTime) {
        double time_diff =  calculateJuilanDateNum(dateTime) -
                calculateJuilanDateNum(planet.getEpochTime());
        double res = (360d / planet.getOrbitalPeriod()) * time_diff +
               planet.getMeanLongitude() - planet.getLongitudeOfPeriapsis();
        res = toRadians(res);
        return fixRes(res);
    }

    private static double calculateMeanAnomalyFromLongitudes2(Planet planet, LocalDateTime dateTime) {
        double time_diff =  calculateJuilanDateNum(dateTime) -
                calculateJuilanDateNum(planet.getEpochTime());
        double res = (2*PI / planet.getOrbitalPeriod()) * time_diff +
                toRadians(planet.getMeanLongitude()) - toRadians(planet.getLongitudeOfPeriapsis());

        return fixRes(res);
    }

    private static double degPositive(double degs) {
        while (degs < 0) degs += 360;
        return degs;
    }

    private static double calculateMeanAnomalyFromMeanAtTZero(double mtzero, double orbitalPeriod,
                                                              LocalDateTime tzero, LocalDateTime t) {

        double delta = calculateJuilanDateNum(t) + calculateJuilanDateNum(tzero);
        double rate = 2 * PI / orbitalPeriod;
        double m = mtzero + rate * delta;
        return fixRes(m);

    }

    /*
    private static double calculateEccAnomalyFromMean(double meanAnomaly, double eccentricity) {
        return calculateEccAnomalyFromMean(meanAnomaly, 2 * PI, 0, eccentricity);
    }

    private static double calculateEccAnomalyFromMean(double meanAnomaly,
                                                      double max, double min, double eccentricity) {
        meanAnomaly = roundDownDecs(meanAnomaly);
        max = roundDownDecs(max);
        min = roundDownDecs(min);
        eccentricity = roundDownDecs(eccentricity);

        double half = min + (max - min) / 2;
        double testValue = half - eccentricity * Math.sin(half);
        if (roundDownDecs(testValue) == meanAnomaly) return roundDownDecs(half);
        else if (testValue < meanAnomaly) return calculateEccAnomalyFromMean(meanAnomaly, max, half, eccentricity);
        else return calculateEccAnomalyFromMean(meanAnomaly, half, min, eccentricity);
    }

     */

    private static XYZCoords helioCoordPlanet(double radius, double trueAnom, double lonAsc,
                                              double lonPer, double incl) {

        double tla = trueAnom + lonPer - lonAsc;
        double x = radius * (cos(lonAsc) * cos(tla) - sin(lonAsc) * sin(tla) * cos(incl));
        double y = radius * (sin(lonAsc) * cos(tla) + cos(lonAsc) * sin(tla) * cos(incl));
        double z = radius * (sin(tla) * sin(incl));
        return new XYZCoords(x, y, z);

    }

    private static double calculateEccAnomalyFromMean(double mean, double ecc) {
        return calculateEccAnomalyFromTrue(calculateTrueAnomalyFromMean(mean, ecc), ecc);
    }

    private static double calculateTrueAnomalyFromMean(double meanAnomaly, double eccentricity) {

        double part1 = (2 * eccentricity - pow(eccentricity, 3) / 4) * sin(meanAnomaly);
        double part2 = 5d / 4d * pow(eccentricity, 2) * sin(meanAnomaly * 2);
        double part3 = 13d / 12d * pow(eccentricity, 3) * sin(meanAnomaly * 3);
        return fixRes(meanAnomaly + part1 + part2 + part3);

    }

    private static double calculateEccAnomalyFromTrue(double trueAnomaly, double eccentricity) {
        double res = 2 * Math.atan(Math.tan(trueAnomaly / 2) * Math.sqrt((1 - eccentricity) /
                (1 + eccentricity)));
        return fixRes(res);
    }

    private static double calculateTrueAnomalyFromEcc(double eccentricAnomaly, double eccentricity) {
        double res = 2 * Math.atan(Math.sqrt((1 + eccentricity) / (1 - eccentricity)) *
                Math.tan(eccentricAnomaly / 2));
        return fixRes(res);
    }

    private static double findRadiusVector(double semiMajorAxis, double ecc, double trueAnomaly) {
        return roundDownDecs(semiMajorAxis * (1d - pow(ecc, 2)) / (1d + ecc * cos(trueAnomaly)));
    }

    private static double roundDownDecs(double number) {
        return (double) Math.round(number * 1000d) / 1000d;
    }

    private static double fixRes(double res) {
        if (res > 2 * PI) res %= 2 * PI;
        else while (res < 0) res += 2 * PI;
        return roundDownDecs(res);
    }

    private static double calculateJuilanDateNum(LocalDateTime local) {

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
