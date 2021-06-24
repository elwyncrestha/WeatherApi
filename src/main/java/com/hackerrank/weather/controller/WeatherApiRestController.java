package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class WeatherApiRestController {

    private final WeatherRepository repository;

    public WeatherApiRestController(WeatherRepository repository) {
        this.repository = repository;
    }

    @DeleteMapping("/erase")
    public ResponseEntity<?> eraseAll() {
        repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/erase", params = {"start", "end", "lat", "lon"})
    public ResponseEntity<?> delete(@RequestParam String start, @RequestParam String end, @RequestParam Float lat, @RequestParam Float lon) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Weather> find = repository.findByDateRangeAndLatAndLong(lat, lon, dateFormat.parse(start), dateFormat.parse(end));
        repository.delete(find);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/weather")
    public ResponseEntity<?> save(@RequestBody Weather weather) {
        Weather find = repository.findOne(weather.getId());

        if (find != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        repository.save(weather);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/weather")
    public ResponseEntity<?> getAll() {
        List<Weather> weathers = repository.findAllByOrderByIdAsc();

        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }

    @GetMapping(value = "/weather", params = "date")
    public ResponseEntity<?> getByDate(@RequestParam String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Weather> weathers = repository.findAllByDateOrderById(dateFormat.parse(date));
        if (weathers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }

    @GetMapping(value = "/weather", params = {"lat", "lon"})
    public ResponseEntity<?> getByLocation(@RequestParam Float lat, @RequestParam Float lon) {
        List<Weather> weathers = repository.findByLatAndLong(lat, lon);

        if (weathers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }


}
