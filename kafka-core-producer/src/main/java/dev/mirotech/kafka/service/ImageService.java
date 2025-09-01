package dev.mirotech.kafka.service;

import dev.mirotech.kafka.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ImageService {


    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public Image generateImage(String type) {

        var name = "image-" + COUNTER.incrementAndGet();
        var size = ThreadLocalRandom.current().nextLong(100, 10_001);

        return new Image(name, size, type);
    }



}
