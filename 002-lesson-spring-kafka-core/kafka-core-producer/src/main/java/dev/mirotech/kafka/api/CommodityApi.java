package dev.mirotech.kafka.api;

import dev.mirotech.kafka.entity.Commodity;
import dev.mirotech.kafka.service.CommodityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityApi {

    final private CommodityService commodityService;


    public CommodityApi(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping("/all")
    public List<Commodity> getAllCommodities() {
        return commodityService.generateDummyCommodities();
    }
}
