package com.careerdevs.stockapiv1.controllers;


import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRepository;
import com.careerdevs.stockapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepostory;

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
                ApiError.throwErr(500, "Did not receive response from AV");
            } else if (alphaVantageResponse.equals("{}")) {
                return ApiError.customApiError("No Data Retrieved from AV: ",
                        404);
            }

            Overview savedOverview = overviewRepostory.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (DataIntegrityViolationException e) {
            return ApiError.customApiError(
                    "can not upload duplicate Stock Data",
                    404
            );
        } catch (IllegalArgumentException e) {
            return ApiError.customApiError(
                    "Error In testOverview: Check URL used for AV Request",
                    500
            );
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }


    //http://localhost:4000/api/overview/test(symbol)
    @GetMapping("/{symbol}")
    private ResponseEntity<?> getOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol) {
        try {


            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

            System.out.println(url);

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {
                return ApiError.customApiError("Did not receive response from AV",
                        500);
            } else if (alphaVantageResponse.getSymbol() == null){
                return ApiError.customApiError("invalid stock symbol: " + symbol,
                        400);
            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ApiError.genericApiError(e);
        }
    }


    @PostMapping("/{symbol}")
    private ResponseEntity<?> uploadOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol) {
        try {

            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

            System.out.println(url);

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {
                return ApiError.customApiError("Did not receive response from AV",
                        500);
            } else if (alphaVantageResponse.getSymbol() == null){
                return ApiError.customApiError("invalid stock symbol: " + symbol,
                        404);
            }

            Overview savedOverview = overviewRepostory.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch (DataIntegrityViolationException e) {
            return ApiError.customApiError(
                    "can not upload duplicate Stock Data",
                    500
            );
        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ApiError.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    private ResponseEntity<?> uploadTestOverviewBySymbol (RestTemplate restTemplate) {
        try {

            String[] testSymbols = {"AAPL", "IBM", "GOOG", "TM","GS"};
            ArrayList<Overview> overviews = new ArrayList<>();
            for (int i = 0; i < testSymbols.length; i++) {

                String symbol = testSymbols[i];

                String apiKey = env.getProperty("AV_API_KEY");
                String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

                Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

                if (alphaVantageResponse == null) {
                    return ApiError.customApiError("Did not receive response from AV",
                            500);
                } else if (alphaVantageResponse.getSymbol() == null) {
                    return ApiError.customApiError("invalid stock symbol: " + symbol,
                            404);
                }
                overviews.add(alphaVantageResponse);
            }
                Iterable<Overview> savedOverview = overviewRepostory.saveAll(overviews);

                return ResponseEntity.ok(savedOverview);


        } catch (DataIntegrityViolationException e) {
            return ApiError.customApiError(
                    "can not upload duplicate Stock Data",
                    500
            );
        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            System.out.println(e.getClass());
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/all")
    private  ResponseEntity<?> getAllOverviews () {
        try {

            Iterable<Overview> allOverviews = overviewRepostory.findAll();

            return ResponseEntity.ok(allOverviews);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }


    //GET All Overviews from SQL database
    //return [] of all overviews

    @GetMapping("/id/{id}")
    private ResponseEntity<?> getOverviewById (@PathVariable String id, String userId) {
        try {
            List<Overview> foundOverview = overviewRepostory.findById(Long.parseLong(id));

            if(foundOverview.isEmpty()) {
                return ApiError.customApiError(id + "did not match any overview", 404);
            }

            return ResponseEntity.ok(foundOverview);
        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch(NumberFormatException e){
            return ApiError.customApiError("Invalid Id: Must be a number" + id, 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    //Delete All Overviews from SQL database
    //return # of delete overviews

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews () {
        try {

            long allOverviewsCount = overviewRepostory.count();
            if(allOverviewsCount == 0) return ResponseEntity.ok("No Overviews to Delete");

            overviewRepostory.deleteAll();

            return ResponseEntity.ok("Deleted Overviews: " +allOverviewsCount);

        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    //DELETE one Overview by ID from SQL databases
    //return deleted overview OR 404 if not found
    @DeleteMapping("/id/{id}")
    private ResponseEntity<?> deleteById (@PathVariable String id) {
        try {


            long overviewId = Long.parseLong(id);

            List<Overview> foundOverview = overviewRepostory.findById(overviewId);

            if(foundOverview.isEmpty()) {
                return ApiError.customApiError(id + "did not match any overview", 404);
            }

            overviewRepostory.deleteById(overviewId);

            return ResponseEntity.ok(foundOverview);
        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch(NumberFormatException e){
            return ApiError.customApiError("Invalid Id: Must be a number" + id, 400);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/symbol/{symbol}")
    private ResponseEntity<?> deleteBySymbol (@PathVariable String symbol) {
        try {

            List<Overview> foundOverview = overviewRepostory.findBySymbol(symbol);

            if(foundOverview.isEmpty()) {
                return ApiError.customApiError(symbol + "did not match any overview", 404);
            }

            overviewRepostory.deleteBySymbol(symbol);

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/{field}/{value}")
    private ResponseEntity<?> getOverviewByField (@PathVariable String field, @PathVariable String value) {

        try {
            List<Overview> foundOverview = null;

            //Getter logic
            switch (field) {
                case "id":
                    foundOverview = overviewRepostory.findById(Long.parseLong(value));
                    break;
                case "symbol":
                    foundOverview = overviewRepostory.findBySymbol(value);
                    break;
                case "sector":
                    foundOverview = overviewRepostory.findBySector(value);
                    break;
                case "name":
                    foundOverview = overviewRepostory.findByName(value);
                    break;
                case "currency":
                    foundOverview = overviewRepostory.findByCurrency(value);
                    break;
                case "country":
                     foundOverview = overviewRepostory.findByCountry(value);
                     break;
            }

            if (foundOverview != null || foundOverview.isEmpty()) {
                return ResponseEntity.ok(foundOverview);
            }
            return ApiError.customApiError(field + "did not match any overview", 404);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch(NumberFormatException e){
            return ApiError.customApiError("Invalid Id: Must be a number" + field, 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/{field}/{value}")
    private ResponseEntity<?> deleteOverviewByField (@PathVariable String field, @PathVariable String value) {

        try {
            List<Overview> foundOverview = null;

            //Getter logic
            switch (field) {
                case "id":
                    foundOverview = overviewRepostory.deleteById(Long.parseLong(value));
                    break;
                case "symbol":
                    foundOverview = overviewRepostory.findBySymbol(value);
                    break;
            }

            if (foundOverview != null || foundOverview.isEmpty()) {
                return ResponseEntity.ok(foundOverview);
            }
            return ApiError.customApiError(field + "did not match any overview", 404);

        } catch (HttpClientErrorException e ) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch(NumberFormatException e){
            return ApiError.customApiError("Invalid Id: Must be a number" + field, 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

}
