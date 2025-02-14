package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  /** 
   * Compares the daylight hours between two cities.
   * @param cityName1 Name of the first city
   * @param cityName2 Name of the second city
   * @return A string to inform which city has longer daylight hours
   */

  public String daylightHoursComparison(String cityName1, String cityName2) {
    try {
      CityInfo cityInfo1 = forecastByCity(cityName1);
      CityInfo cityInfo2 = forecastByCity(cityName2);

      long city1DaylightHours = calculateDaylightHours(cityInfo1);
      long city2DaylightHours = calculateDaylightHours(cityInfo2);

      if (city1DaylightHours > city2DaylightHours) {
        return cityName1 + " has the longest day with " + city1DaylightHours + " hours of daylight.";
      } else if (city2DaylightHours > city1DaylightHours) {
        return cityName2 + " has the longest day with " + city2DaylightHours + " hours of daylight.";
      } else {
        return "Both cities have the same daylight hours.";
      }
    } catch (Exception e) {
      return "Error when comparing daylight hours for: " + cityName1 + " and " + cityName2;
    }
  }


  /**
   * Checks which city it is currently raining in.
   * @param cityName1 Name of the first city
   * @param cityName2 Name of the second city
   * @return A string stating whether it is raining in either or both cities
   */

  public String rainCheck(String cityName1, String cityName2) {
    try {
      CityInfo cityInfo1 = forecastByCity(cityName1);
      CityInfo cityInfo2 = forecastByCity(cityName2);

      String rainCheckCity1 = cityInfo1.getCurrentConditions().getConditions();
      String rainCheckCity2 = cityInfo2.getCurrentConditions().getConditions();

      if (rainCheckCity1.toLowerCase().contains("rain")) {
        return " It is currently raining in " + cityName1 + ".";
      } else if (rainCheckCity2.toLowerCase().contains("rain")) {
        return " It is currently raining in " + cityName2 + ".";
      } else {
        return "It is not raining in either city.";
      }
    } catch (Exception e) {
      return "Error checking rain status for: " + cityName1 + " and " + cityName2;
    }
  }


  /**
   * Calculates daylight hours using sunrise and sunset times.
   * Using type long to avoid overflow.
   * @param cityInfo CityInfo object containing sunrise and sunset times
   * @return Number of daylight hours
   */

  private long calculateDaylightHours(CityInfo cityInfo) {
    try {
      String sunriseTime = cityInfo.getCurrentConditions().getSunrise();
      String sunsetTime = cityInfo.getCurrentConditions().getSunset();

      long sunriseInMillisec = convertTimeToMillisec(sunriseTime);
      long sunsetInMillisec = convertTimeToMillisec(sunsetTime);

      return (sunsetInMillisec - sunriseInMillisec) / (1000 * 60 * 60);  // Converting milliseconds to hours
    } catch (Exception e) {
      return 0;
    }
  }


  /**
   * Converts a time string (HH:mm) to milliseconds.
   * Using type long to avoid overflow.
   * @param time Time string in HH:mm format
   * @return Time in milliseconds
   */

  private long convertTimeToMillisec(String time) {
    try {
      String[] splitTimeParts = time.split(":");
      int hours = Integer.parseInt(splitTimeParts[0]);
      int minutes = Integer.parseInt(splitTimeParts[1]);

      return (hours * 60 + minutes) * 60 * 1000; // Converting to milliseconds
    } catch (Exception e) {
      return 0;
    }
  }
}

