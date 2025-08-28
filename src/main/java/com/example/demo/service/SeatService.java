package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SEAT_KEY_PREFIX = "show:";

    public void cacheSeats(Long showId, Map<String, String> seats) {
        redisTemplate.opsForHash().putAll(SEAT_KEY_PREFIX + showId + ":seats", seats);
    }

    public Map<String, String> getSeats(Long showId) {
        String key = SEAT_KEY_PREFIX + showId + ":seats";

        // 1. Try Redis first
        Map<Object, Object> seats = redisTemplate.opsForHash().entries(key);

        if (seats == null || seats.isEmpty()) {
            // 2. Fallback → DB
            Map<String, String> dbSeats = fetchSeatsFromDb(showId); // your DB query
            if (dbSeats != null && !dbSeats.isEmpty()) {
                // 3. Store in Redis with TTL
                redisTemplate.opsForHash().putAll(key, dbSeats);
                redisTemplate.expire(key, 10, TimeUnit.MINUTES);
                return dbSeats;
            }
        }

        // 4. Return from Redis
        return seats.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> (String) e.getKey(),
                        e -> (String) e.getValue()
                ));
    }

    private Map<String, String> fetchSeatsFromDb(Long showId) {
        // Example: seatId → status ("available"/"booked")
        Map<String, String> dbSeats = new HashMap<>();
        dbSeats.put("A1", "available");
        dbSeats.put("A2", "booked");
        dbSeats.put("A3", "available");
        return dbSeats;
    }

    public boolean lockSeat(Long showId, String seatId, String userId) {
        String key = "lock:" + showId + ":" + seatId;
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, userId, 2, TimeUnit.MINUTES);
        return Boolean.TRUE.equals(success); // seat locked successfully
    }
            

    public void releaseSeat(Long showId, String seatId) {
        String key = "lock:" + showId + ":" + seatId;
        redisTemplate.delete(key);
    }
    public void saveBookingCart(String userId, Long showId, List<String> seats) {
        String key = "cart:" + userId;
        Map<String, Object> cart = new HashMap<>();
        cart.put("showId", showId);
        cart.put("seats", seats);
        redisTemplate.opsForHash().putAll(key, cart);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES); // auto expire
    }


}

