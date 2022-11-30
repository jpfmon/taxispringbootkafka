package com.montojo.taxikafka.distanceservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Stack;

public class GeoData {

    private float totalDistance;

    private Stack<Coordinates> coordinatesStack;

    private static final Logger logger = LoggerFactory.getLogger(GeoData.class);

    public GeoData() {
    }

    public GeoData(Float totalDistance, Stack<Coordinates> coordinatesStack) {
        this.totalDistance = totalDistance;
        this.coordinatesStack = coordinatesStack;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Stack<Coordinates> getCoordinatesStack() {
        return coordinatesStack;
    }

    public void setCoordinatesStack(Stack<Coordinates> coordinatesStack) {
        this.coordinatesStack = coordinatesStack;
    }

    public float getLastLatitude(){
        return this.coordinatesStack.pop().getLatitude();
    }

    public float getLastLongitude(){
        return this.coordinatesStack.pop().getLongitude();
    }

    public void addNewCoordinates(Coordinates newCoordinates){
        this.coordinatesStack.push(newCoordinates);
    }

    public void addNewCoordinates(Float newLatitude, Float newLongitude){
        addNewCoordinates(new Coordinates(newLatitude, newLongitude));
    }

    @Override
    public String toString() {
        return "GeoData{" +
                "totalDistance=" + totalDistance +
                ", coordinatesStack=" + coordinatesStack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoData geoData = (GeoData) o;
        return Float.compare(geoData.totalDistance, totalDistance) == 0 && coordinatesStack.equals(geoData.coordinatesStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalDistance, coordinatesStack);
    }

    /**
     * Calculates new total distance, updates totalDisntace instance field and stores new coordinates in Stack of coordinates
     * @param newLatitude
     * @param newLongitude
     * @return new total distance traveled
     */
    public float processNewCoordinates(Float newLatitude, Float newLongitude){

        logger.info("\nNew coordinates received are, latitude {} , longitude {}", newLatitude, newLongitude);
        //calculate new distance comparing last coordinates to new coordinates

        Coordinates lastCoordinates = coordinatesStack.peek();
        logger.info("\nPrevious last Coordinates are, latitude {} and longitude {}", lastCoordinates.getLatitude(), lastCoordinates.getLongitude());
        Coordinates newCoordinates = new Coordinates(newLatitude,newLongitude);

        float newDistanceTraveled = Coordinates.calculateDistanceBetween2Coord(lastCoordinates, newCoordinates);

        logger.info("\nNew distance according to new Coordinates is: {}", newDistanceTraveled);

        //sum new calculated distance to the existing stored distance
        float totalNewDistance = totalDistance + newDistanceTraveled;

        //add new coordinates to stack
        coordinatesStack.push(newCoordinates);

        //set new value for totalDistance
        totalDistance = totalNewDistance;
        logger.info("\nNew total distance traveled: {}", totalDistance);
        //return new absolute total distance

        return totalDistance;
    }


}
