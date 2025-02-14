package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  // Given two city names, compare the length of the daylight hours and return the city with the longest day

  /**
   * Handles GET requests to compare daylight hours between two cities.
   * 
   * @param city1 Name of the first city
   * @param city2 Name of the second city
   * @return ResponseEntity containing the result of the comparison as a string
   */

  @GetMapping("/daylight-comparison/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable String city1, @PathVariable String city2) {
    try {
      String daylightComparison = weatherService.daylightHoursComparison(city1, city2);
      if (daylightComparison.contains("Error")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(daylightComparison); // Invalid input or error in comparison
      }
      return ResponseEntity.ok(daylightComparison);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body("Error occurred while comparing daylight hours."); // Handle unexpected server issues
    }
  }


  // Given two city names, check which city its currently raining in

  /**
   * Handles GET requests to check if it is raining in either of the two cities.
   * 
   * @param city1 Name of the first city
   * @param city2 Name of the second city
   * @return ResponseEntity containing the result of the rain check as a string
   */

  @GetMapping("/rain-check/{city1}/{city2}")
  public ResponseEntity<String> checkRain(@PathVariable String city1, @PathVariable String city2) {
    try {
      String rainCheck = weatherService.rainCheck(city1, city2);
      if (rainCheck.contains("Error")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rainCheck); // Invalid input or error in rain check
      }
      return ResponseEntity.ok(rainCheck);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body("Error occurred while checking rain status."); // Handle unexpected server issues
    }
  }

}
  


