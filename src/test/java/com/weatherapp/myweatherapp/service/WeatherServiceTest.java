package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    //Mocking the dependency used by WeatherService
    @Mock
    private VisualcrossingRepository weatherRepo;

    //injecting the mocked dependencies in the WeatherService object 
    @InjectMocks
    private WeatherService weatherService;

    //creating mock objects for CityInfo
    private CityInfo city1;
    private CityInfo city2;

    //Initialises the mocks and sets up default behaviour
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating mock CityInfo objects
        city1 = mock(CityInfo.class);
        city2 = mock(CityInfo.class);

        // Mocking the repository behavior
        when(weatherRepo.getByCity("City1")).thenReturn(city1);
        when(weatherRepo.getByCity("City2")).thenReturn(city2);
    }

    @Test
    void testDaylightHours_city1HasLongerDaylight() {
        //mocking sunrise and sunset times
        mockConditions(city1, "05:30", "19:30");
        mockConditions(city2, "06:00", "18:00");

        // Calling method and asserting results
        String result = weatherService.daylightHoursComparison("City1", "City2");
        assertEquals("City1 has the longest day with 14 hours of daylight.", result);
    }

    @Test
    void testDaylightHours_city2HasLongerDaylight() {
        //mocking sunrise and sunset times
        mockConditions(city1, "05:00", "16:00");
        mockConditions(city2, "05:00", "18:00");

        // Calling method and asserting results
        String result = weatherService.daylightHoursComparison("City1", "City2");
        assertEquals("City2 has the longest day with 13 hours of daylight.", result);
    }

    @Test
    void testDaylightHours_bothCitiesHaveSameDaylight() {
        //mocking sunrise and sunset times
        mockConditions(city1, "07:00", "17:00");
        mockConditions(city2, "07:00", "17:00");

        // Calling method and asserting results
        String result = weatherService.daylightHoursComparison("City1", "City2");
        assertEquals("Both cities have the same daylight hours.", result);
    }

    @Test
    void testRainCheck_city1IsRaining() {
        //mocking weather 
        mockWeatherConditions(city1, "Rain");
        mockWeatherConditions(city2, "Cloudy");

        // Calling method and asserting results
        String result = weatherService.rainCheck("City1", "City2");
        assertEquals(" It is currently raining in City1.", result);
    }

    @Test
    void testRainCheck_city2IsRaining() {
        //mocking weather
        mockWeatherConditions(city1, "Sunny");
        mockWeatherConditions(city2, "Rain");

        // Calling method and asserting results
        String result = weatherService.rainCheck("City1", "City2");
        assertEquals(" It is currently raining in City2.", result);
    }

    @Test
    void testRainCheck_noRainInBothCities() {
        //mocking weather 
        mockWeatherConditions(city1, "Cloudy");
        mockWeatherConditions(city2, "Cloudy");

        // Calling method and asserting results
        String result = weatherService.rainCheck("City1", "City2");
        assertEquals("It is not raining in either city.", result);
    }

    // Tests for exception handling
    @Test
    void testDaylightHoursComparison_exception() {
        //simulating exception 
        when(weatherRepo.getByCity("City1")).thenThrow(new RuntimeException("Exception"));

        // Calling method and asserting results
        String result = weatherService.daylightHoursComparison("City1", "City2");
        assertTrue(result.contains("Error when comparing daylight hours"));
    }

    @Test
    void testRainCheck_exception() {
        //simulating an exception 
        when(weatherRepo.getByCity("City1")).thenThrow(new RuntimeException("Exception"));

        // Calling method and asserting results
        String result = weatherService.rainCheck("City1", "City2");
        assertTrue(result.contains("Error checking rain status"));
    }

    // Method used to test cases by mocking city conditions for sunrise and sunset times
    private void mockConditions(CityInfo cityInfo, String sunrise, String sunset) {
        CityInfo.CurrentConditions currentConditions = mock(CityInfo.CurrentConditions.class);
        when(cityInfo.getCurrentConditions()).thenReturn(currentConditions);
        when(currentConditions.getSunrise()).thenReturn(sunrise);
        when(currentConditions.getSunset()).thenReturn(sunset);
    }

    // Method used to test cases by mocking weather conditions 
    private void mockWeatherConditions(CityInfo cityInfo, String condition) {
        CityInfo.CurrentConditions currentConditions = mock(CityInfo.CurrentConditions.class);
        when(cityInfo.getCurrentConditions()).thenReturn(currentConditions);
        when(currentConditions.getConditions()).thenReturn(condition);
    }
}
