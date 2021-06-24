package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("select w from Weather  w where w.location.latitude=?1 and w.location.longitude=?2 and (w.date >= ?3 and w.date <= ?4)")
    List<Weather> findByDateRangeAndLatAndLong(Float lat, Float lon, Date start, Date end);

    List<Weather> findAllByOrderByIdAsc();

    List<Weather> findAllByDateOrderById(Date date);

    @Query("select w from Weather  w where w.location.latitude=?1 and w.location.longitude=?2 order by w.id asc")
    List<Weather> findByLatAndLong(Float lat, Float lon);
}
