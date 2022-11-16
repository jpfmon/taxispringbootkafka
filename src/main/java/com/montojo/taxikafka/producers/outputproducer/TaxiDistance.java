package com.montojo.taxikafka.producers.outputproducer;

public class TaxiDistance {

    private String id;
    private float distance;

    public TaxiDistance() {
    }

    public TaxiDistance(String id, float distance) {
        this.id = id;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
