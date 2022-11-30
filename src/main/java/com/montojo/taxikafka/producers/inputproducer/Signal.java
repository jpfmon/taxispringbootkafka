package com.montojo.taxikafka.producers.inputproducer;


import java.util.Objects;

public class Signal {

    private String taxiId;

    private Float latitude;

    private Float longitude;

    public Signal() {
    }

    public Signal(String taxiId, Float latitude, Float longitude) {
        this.taxiId = taxiId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static boolean VALIDATE_COORDINATES(String latitudeTxt, String longitudeTxt){
        float latitude;
        float longitude;

//        System.out.printf("Validating latitude %s and longitude %s%n",latitudeTxt, longitudeTxt);

        try {
            latitude = Float.parseFloat(latitudeTxt);
            if( latitude < -90 || latitude > 90){
                System.out.println("Not valid range for latitude");
                return false;
            }
        } catch (Exception e){
            System.out.println("Not valid number for latitude");
            return false;
        }
        try {
            longitude = Float.parseFloat(longitudeTxt);
            if( longitude < -180 || longitude > 180){
                System.out.println("Not valid range for longitude");
                return false;
            }
        } catch (Exception e){
            System.out.println("Not valid number for longitude");
            return false;
        }
        return true;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "taxiId='" + taxiId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signal signal = (Signal) o;
        return taxiId.equals(signal.taxiId) && latitude.equals(signal.latitude) && longitude.equals(signal.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxiId, latitude, longitude);
    }
}
