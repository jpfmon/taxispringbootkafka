package com.montojo.taxikafka.distanceservice;

import com.montojo.taxikafka.producers.inputproducer.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class DistanceService {

    Map<String, GeoData> geoDataByTaxiId = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(DistanceService.class);

    /**
     * Receives the Signal (object with taxiId and geo coordinates).
     * HashMap geoDataByTaxiId stores taxiId's as keys, with values as GeoData (total distance traveled and stack of coordinates)
     * If HashMap geoDataByTaxiId doesn't contain data of taxi, creates new entry and returns 0 as total distance.
     * If HashMap geoDataByTaxiId contains data of taxi, retrieves GeoData and processNewCoordinates(),
     * to retrieve new total distance traveled, store it in GeoData, and push new coordinates into the stack
     * @param signal
     * @return total distance traveled.
     */
    public float processSignal(Signal signal) {

        float totalDistance = 0f;

        if (!geoDataByTaxiId.containsKey(signal.getTaxiId())) {
            logger.info("\nTaxi id {} doesn't exist, creating new data.", signal.getTaxiId());
            Stack<Coordinates> newCoordStack = new Stack<>();
            newCoordStack.push(new Coordinates(signal.getLatitude(), signal.getLongitude()));
            GeoData geoData = new GeoData(0f, newCoordStack);
            geoDataByTaxiId.put(signal.getTaxiId(), geoData);
            return totalDistance;
        }

        logger.info("\nTaxiId {} already exists, retrieving and processing data.", signal.getTaxiId());
        GeoData geoDataOfTaxi = geoDataByTaxiId.get(signal.getTaxiId());
        totalDistance = geoDataOfTaxi.processNewCoordinates(signal.getLatitude(), signal.getLongitude());
        return totalDistance;

    }
}
