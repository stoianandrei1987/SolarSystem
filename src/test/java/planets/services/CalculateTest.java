package planets.services;

import org.junit.Assert;
import org.junit.Test;
import planets.models.Planet;
import planets.models.PlanetType;
import planets.models.XYZCoords;

import java.time.LocalDateTime;



public class CalculateTest {

    private static final LocalDateTime TEST_TIME_1 =
            LocalDateTime.of(1997, 8, 20, 0, 0);
    private static final LocalDateTime TEST_TIME_2 =
            LocalDateTime.of(1996, 6, 21, 0, 0);
    private static final Planet EARTH_T1 =
            new Planet("Earth", TEST_TIME_1, 0.00041,1d,
                    0.0167,365.2,349.2,
                    102.8517, 328.40353, PlanetType.PLANET);


    @Test
    public void calculateMeanAnomalyFromLongitudes() {
        Assert.assertEquals( Math.toRadians(166.4170574),
                Calculate.calculateMeanAnomalyFromLongitudes(EARTH_T1, TEST_TIME_2), 0.005);
    }

    @Test
    public void xyzCoords() {
        XYZCoords xyz = Calculate.xyzCoords(1.57261067,
                Math.toRadians(244.921657),Math.toRadians(49.56654) ,Math.toRadians(336.0882), Math.toRadians(1.84992));
        Assert.assertEquals( -1.18659376, xyz.getX(), 0.001);
        Assert.assertEquals( -1.03200869, xyz.getY(), 0.001);
        Assert.assertEquals( 0.00755328, xyz.getZ(), 0.001);


    }

    @Test
    public void calculateEccAnomalyFromMean() {
        Assert.assertEquals(Math.toRadians(0), Calculate.calculateEccAnomalyFromMean(Math.toRadians(0), 0.093431),
                0.001);
    }

    @Test
    public void calculateTrueAnomalyFromMean() {
        Assert.assertEquals(Math.toRadians(244.921657),
                 Calculate.calculateTrueAnomalyFromMean( Math.toRadians(254.89562), 0.0934231),0.001);
    }

    @Test
    public void calculateEccAnomalyFromTrue() {
        Assert.assertEquals(Math.toRadians(249.85589), Calculate.
                        calculateEccAnomalyFromTrue(Math.toRadians(244.921657), 0.093431),
                0.001);
    }

    @Test
    public void calculateTrueAnomalyFromEcc() {
        Assert.assertEquals(Math.toRadians(244.921657),
                Calculate.calculateTrueAnomalyFromEcc(Math.toRadians(249.85589), 0.093431), 0.001);
    }

    @Test
    public void findRadiusVector() {
        Assert.assertEquals(1.5726, Calculate.findRadiusVector(1.5236365, 0.093431,
                Math.toRadians(244.921657)), 0.001);
    }

    @Test
    public void roundDownDecs() {
        Assert.assertEquals(0.239, Calculate.roundDownDecs( 0.2391), 0);
        Assert.assertEquals(-0.266, Calculate.roundDownDecs( -0.2667), 0);
    }

    @Test
    public void fixRes() {
        Assert.assertEquals(Calculate.roundDownDecs(Math.PI), Calculate.fixRes(-37*Math.PI), 0);
    }

    @Test
    public void calculateJuilanDateNum() {
        Assert.assertEquals(2450680.5 , Calculate.calculateJuilanDateNum(TEST_TIME_1), 0);
    }
}