package com.montojo.taxikafka.distanceservice;

public class Coordinates {

    private float latitude;
    private float longitude;

    public Coordinates() {
    }

    public Coordinates(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static float calculateDistanceBetween2Coord(Coordinates coord1, Coordinates coord2) {

        float distance = 0f;

        if ((coord1.latitude == coord2.latitude) && (coord1.longitude == coord2.longitude)) {
            return distance;
        }

        double theta = coord1.longitude - coord2.longitude;
        double dist = Math.sin(Math.toRadians(coord1.latitude)) * Math.sin(Math.toRadians(coord2.latitude)) + Math.cos(Math.toRadians(coord1.latitude)) * Math.cos(Math.toRadians(coord2.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        distance = (float) dist;
        return distance;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }


}
