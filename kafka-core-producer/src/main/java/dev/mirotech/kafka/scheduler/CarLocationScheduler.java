package dev.mirotech.kafka.scheduler;

import dev.mirotech.kafka.entity.CarLocation;
import dev.mirotech.kafka.producer.CarProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Service
@RequiredArgsConstructor
public class CarLocationScheduler {


    private final CarProducer carProducer;
    private final List<CarLocation> carLocations = initializeCarLocations();

    @Scheduled(fixedRate = 1000)
    public void generateCarLocationData() {
        for (var carLocation : carLocations) {
            // Update timestamp
            carLocation.setTimestamp(System.currentTimeMillis());

            // Adjust distance based on car ID
            if ("car-one".equals(carLocation.getCarId()) || "car-three".equals(carLocation.getCarId())) {
                carLocation.setDistance(carLocation.getDistance() + 1);
            } else if ("car-two".equals(carLocation.getCarId())) {
                carLocation.setDistance(carLocation.getDistance() - 1);
            }

            // Send the updated car location to Kafka
            carProducer.sendMessage(carLocation);

            // Log the sent message
            log.info("Sent car location: {}", carLocation);
        }
    }

    private List<CarLocation> initializeCarLocations() {
        List<CarLocation> locations = new ArrayList<>();
        locations.add(new CarLocation("car-one", System.currentTimeMillis(), 0));
        locations.add(new CarLocation("car-two", System.currentTimeMillis(), 0));
        locations.add(new CarLocation("car-three", System.currentTimeMillis(), 0));
        return locations;
    }

}
