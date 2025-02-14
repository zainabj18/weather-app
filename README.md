# MyWeather App Tech Test

Welcome to the MyWeather App Tech Test.

## The Challenge

You are tasked with implementing two new features in the app:

1. **Daylight Hours Comparison:** Given two city names, compare the length of the daylight hours between them and return the city with the longest day. In this context, "daylight hours" means the time between sunrise and sunset.

2. **Rain Check:** Given two city names, check which city it is currently raining in.

In addition to implementing these 2 features, you will also need to write tests verifying that your code works as expected.

If possible, include exception handling in the controller.

Finally, you can write any documentation as you see fit, explaining your choices and how the code works.

## Features Implemented 
1. Daylight Hours Comparison: A new endpoint to compare the daylight hours between two cities, returning the city with the longest daylight.

2. Rain Check: An endpoint that checks whether it is raining in either of the two given cities.

## The Codebase

The codebase is a Java application built with the Spring framework. It includes a `WeatherController` class where you will add your new features.

Files/Classes Involved:
WeatherController.java: The main controller where the new endpoints have been added.
WeatherService.java: Handles the logic for fetching weather data from the Visual Crossing API and processing it for the features.
application.properties: Contains configuration, including the Visual Crossing API key.

### Prerequisites

- [Java sdk 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6.3+](https://maven.apache.org/install.html)
- API key for [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions). 
  - This can be done by creating a free account on the above link. Then you will need to add your key to the `weather.visualcrossing.key` field in src/main/resources/application.properties

## Setup Instructions

1. Clone the repository
git clone https://github.com/zainabj18/weather-app.git 

2. Navigate into the project directory:
cd myweatherapp

3. Add Visual Crossing Weather API key to src/main/resources/application.properties:
weather.visualcrossing.key=9YBTZ3J29Z4TU7KT5BVE6NM83

4. Build and install dependencies:
mvn clean install

5. Run the application:
mvn spring-boot:run


## Endpoints 
1. Daylight Hours Comparison
GET /daylight-comparison/{city1}/{city2}

Compares daylight hours between two cities and returns the city with the longest daylight hours.
Example request: /daylight-comparison/London/Paris

2. Rain Check
GET /rain-check/{city1}/{city2}

Checks if it is currently raining in either of the two cities.
Example request: /rain-check/London/Paris


## Testing
To test the functionality, you can run the unit tests written for WeatherService:
mvn test

Tests include verification for both the Daylight Hours Comparison and Rain Check features.

## Submission

* Push the downloaded version of this repo to your Github
* Make a branch for your changes
* Once you're ready to submit, raise a Pull Request to merge your changes with your main branch and share the repo with us.

Good luck!
