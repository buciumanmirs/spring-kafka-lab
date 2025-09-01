package dev.mirotech.kafka;

import dev.mirotech.kafka.producer.Image2Producer;
import dev.mirotech.kafka.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    @Autowired
    private ImageService imageService;

    @Autowired
    private Image2Producer image2Producer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // Send six images to Kafka

        // First image: JPG to partition 0
        var image1 = imageService.generateImage("jpg");
        image2Producer.sendImageToPartition(image1, 0);

        // Second image: SVG to partition 0
        var image2 = imageService.generateImage("svg");
        image2Producer.sendImageToPartition(image2, 0);

        // Third image: PNG to partition 0
        var image3 = imageService.generateImage("png");
        image2Producer.sendImageToPartition(image3, 0);

        // Fourth image: GIF to partition 1
        var image4 = imageService.generateImage("gif");
        image2Producer.sendImageToPartition(image4, 1);

        // Fifth image: BMP to partition 1
        var image5 = imageService.generateImage("bmp");
        image2Producer.sendImageToPartition(image5, 1);

        // Sixth image: TIFF to partition 1
        var image6 = imageService.generateImage("tiff");
        image2Producer.sendImageToPartition(image6, 1);
    }


}
