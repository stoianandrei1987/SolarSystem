package planets.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Planet {

    private String name;
    private LocalDateTime epochTime;
    private Double inclination;
    private Double semiMajorAxis;
    private Double eccentricity;
    private Double orbitalPeriod;
    private Double longitudeOfAscendingNode;
    private Double longitudeOfPeriapsis;
    private Double meanLongitude;

    private PlanetType planetType;
    private List<Planet> satellites;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(LocalDateTime epochTime) {
        this.epochTime = epochTime;
    }

    public Double getInclination() {
        return inclination;
    }

    public void setInclination(Double inclination) {
        this.inclination = inclination;
    }

    public Double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(Double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public Double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(Double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Double getLongitudeOfAscendingNode() {
        return longitudeOfAscendingNode;
    }

    public void setLongitudeOfAscendingNode(Double longitudeOfAscendingNode) {
        this.longitudeOfAscendingNode = longitudeOfAscendingNode;
    }

    public Double getLongitudeOfPeriapsis() {
        return longitudeOfPeriapsis;
    }

    public void setLongitudeOfPeriapsis(Double longitudeOfPeriapsis) {
        this.longitudeOfPeriapsis = longitudeOfPeriapsis;
    }

    public Double getMeanLongitude() {
        return meanLongitude;
    }

    public void setMeanLongitude(Double meanLongitude) {
        this.meanLongitude = meanLongitude;
    }

    public PlanetType getPlanetType() {
        return planetType;
    }

    public void setPlanetType(PlanetType planetType) {
        this.planetType = planetType;
    }

    public Planet(String name, LocalDateTime epochTime,
                  Double inclination, Double semiMajorAxis,
                  Double eccentricity, Double orbitalPeriod,
                  Double longitudeOfAscendingNode,
                  Double longitudeOfPeriapsis, Double meanLongitude,
                  PlanetType planetType) {

        this.name = name;
        this.epochTime = epochTime;
        this.inclination = inclination;
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.orbitalPeriod = orbitalPeriod;
        this.longitudeOfAscendingNode = longitudeOfAscendingNode;
        this.longitudeOfPeriapsis = longitudeOfPeriapsis;
        this.meanLongitude = meanLongitude;
        this.planetType = planetType;
        this.satellites = new ArrayList<>();

    }

    public void setSatellites(List<Planet> satellites) {
        this.satellites = satellites;
    }

    public List<Planet> getSatellites() {
        return satellites;
    }

    @Override
    public String toString() {
        return "planets.models.Planet{" +
                "name='" + name + '\'' +
                ", epochTime=" + epochTime +
                ", inclination=" + inclination +
                ", semiMajorAxis=" + semiMajorAxis +
                ", eccentricity=" + eccentricity +
                ", orbitalPeriod=" + orbitalPeriod +
                ", longitudeOfAscendingNode=" + longitudeOfAscendingNode +
                ", longitudeOfPeriapsis=" + longitudeOfPeriapsis +
                ", meanLongitude=" + meanLongitude +
                ", planetType=" + planetType +
                '}';
    }
}
