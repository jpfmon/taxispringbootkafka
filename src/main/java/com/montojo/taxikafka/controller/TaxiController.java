package com.montojo.taxikafka.controller;


import com.montojo.taxikafka.producers.inputproducer.Signal;
import com.montojo.taxikafka.producers.inputproducer.SignalInputProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TaxiController {

    @Autowired
    SignalInputProducer signalInputProducer;

    private String htmlText = "<form action=\"/checkin\">\n" +
            "  <label for=\"taxiid\">Taxi Id:</label><br>\n" +
            "  <input type=\"text\" id=\"taxiid\" name=\"taxiid\"><br>\n" +
            "  <label for=\"lat\">Latitude:</label><br>\n" +
            "  <input type=\"text\" id=\"lat\" name=\"lat\"><br><br>\n" +
            "  <label for=\"long\">Longitude:</label><br>\n" +
            "  <input type=\"text\" id=\"long\" name=\"long\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "</form>";

    @RequestMapping("/")
    public String testController(){
        return "Testing taxi controller \n" + htmlText ;
    }

    @RequestMapping("/checkin")
    public String receiveSignal(@RequestParam(name = "taxiid", required = true) String taxiId,
                              @RequestParam(name="lat", required = true) String latitude,
                              @RequestParam(name="long", required = true) String longitude){

        //check validity of coordinates
        boolean checkCoordinates = Signal.VALIDATE_COORDINATES(latitude, longitude);
        boolean checkTaxiId = taxiId.trim().isEmpty();
        String text = String.format("Taxi id %s. Coordinates latitude %s and longitude %s are %s",
                checkTaxiId? "is empty" : taxiId, latitude, longitude, checkCoordinates ? "valid" : "invalid");

        if (checkCoordinates){
            //create signal
            Signal signal = new Signal(taxiId.trim(), Float.valueOf(latitude), Float.valueOf(longitude));
            //send signal to topic
            signalInputProducer.writeSignal(signal);
        }
        return text + "\n" + htmlText;
    }
}
