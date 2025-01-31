package com.footystars.service.requester;

import com.footystars.model.entity.RequestLimiter;
import com.footystars.persistence.repository.RequestLimiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for managing API request limits and remaining requests.
 */
@Service
@RequiredArgsConstructor
public class RequestsLimiterService {

    private final RequestLimiterRepository requestLimiterRepository;
    private static final Integer LIMIT = 75000;

    /**
     * Updates the API request limit and the remaining number of requests.
     * If no limiter record exists, a new one is created.
     *
     * @param limit     The maximum number of requests allowed.
     * @param remaining The remaining number of requests available.
     */
    public void update(int limit, int remaining) {
        var limiter = requestLimiterRepository.findAll();
        if (limiter.isEmpty()) {
            createLimiter(limit, remaining);
        } else {
            var requestLimiter = limiter.get(0);
            requestLimiter.setRemaining(remaining);
            requestLimiter.setRequestsLimit(limit);
            requestLimiterRepository.save(requestLimiter);
        }
    }

    /**
     * Creates a new request limiter entity with the given limits.
     *
     * @param limit     The maximum number of requests allowed.
     * @param remaining The remaining number of requests available.
     */
    private void createLimiter(int limit, int remaining) {
        var requestsLimit = RequestLimiter.builder()
                .requestsLimit(limit)
                .remaining(remaining)
                .build();
        requestLimiterRepository.save(requestsLimit);
    }

    /**
     * Retrieves the number of remaining API requests.
     * If no limiter exists, it initializes a new limiter with the default limit.
     *
     * @return The remaining number of requests available.
     */
    public Integer getRemaining() {
        List<RequestLimiter> limiterList = requestLimiterRepository.findAll();
        if (!limiterList.isEmpty()) {
            return limiterList.get(0).getRemaining();
        } else {
            createLimiter(LIMIT, LIMIT);
        }
        return LIMIT;
    }
}
