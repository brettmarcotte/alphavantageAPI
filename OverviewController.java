package com.careerdevs.stockapiv1.controllers;


import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRepostory;
import com.careerdevs.stockapiv1.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepostory overviewRepostory;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

        //http://localhost:4000/api/overview/test
    @GetMapping("/test")
    private ResponseEntity<?> TestOverview (RestTemplate restTemplate) {
        try {
            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            String alphaVantageResponse = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //test upload to database
    @PostMapping("/test")
    private ResponseEntity<?> TestUploadOverview (RestTemplate restTemplate) {
        try {
            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (alphaVantageResponse.equals("{}")){
                return ApiErrorHandling.customApiError("No Data Retrieved from AV: ",
                        HttpStatus.BAD_REQUEST);
            }

            Overview savedOverview = overviewRepostory.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    //http://localhost:4000/api/overview/test(symbol)
    @GetMapping("/{symbol}")
    private ResponseEntity<?> getOverviewBySymbol (RestTemplate restTemplate, @RequestParam String symbol) {
        try {

            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

            System.out.println(url);

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (alphaVantageResponse.equals("{}")){
                return ApiErrorHandling.customApiError("invalid stock symbol: " + symbol,
                        HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ApiErrorHandling.genericApiError(e);
        }
    }


}
