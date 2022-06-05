package com.sbab.demo.proxy;

import com.sbab.demo.model.JourData;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "sbab-demo-service", url="${sbab.app.baseURL}")

public interface APIProxy {

    @RequestMapping(method= RequestMethod.GET)
    @Headers("Accept: application/json")
    String getTrafikLabData(@RequestParam(value="model") String model,
                                           @RequestParam(value="key") String key);
}
