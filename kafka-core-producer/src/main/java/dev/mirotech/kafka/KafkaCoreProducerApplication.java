package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.FoodOrder;
import dev.mirotech.kafka.entity.Image;
import dev.mirotech.kafka.entity.SimpleNumber;
import dev.mirotech.kafka.producer.FoodOrderProducer;
import dev.mirotech.kafka.producer.ImageProducer;
import dev.mirotech.kafka.producer.SimpleNumberProducer;
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
    private ImageProducer imageProducer;


    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        // Generate and send the first image (JPG) to partition 0
        var image1 = imageService.generateImage("jpg");
        imageProducer.sendImageToPartition(image1, 0);

        // Generate and send the second image (SVG) to partition 0
        var image2 = imageService.generateImage("svg");
        imageProducer.sendImageToPartition(image2, 0);

        // Generate and send the third image (e.g., PNG) to partition 0
        var image3 = imageService.generateImage("png");
        imageProducer.sendImageToPartition(image3, 0);

        // Generate and send the fourth image (e.g., GIF) to partition 1
        var image4 = imageService.generateImage("gif");
        imageProducer.sendImageToPartition(image4, 1);

        // Generate and send the fifth image (e.g., BMP) to partition 1
        var image5 = imageService.generateImage("bmp");
        imageProducer.sendImageToPartition(image5, 1);

        // Generate and send the sixth image (e.g., TIFF) to partition 1
        var image6 = imageService.generateImage("tiff");
        imageProducer.sendImageToPartition(image6, 1);
    }


}
