package com.footystars.service.requester;


import com.footystars.persistence.entity.RequestLimiter;
import com.footystars.persistence.repository.RequestLimiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestsLimiterService {

    private final RequestLimiterRepository requestLimiterRepository;
    private static final Integer LIMIT = 75000;


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

    private void createLimiter(int limit, int remaining) {
        var requestsLimit = RequestLimiter.builder()
                .requestsLimit(limit)
                .remaining(remaining)
                .build();
        requestLimiterRepository.save(requestsLimit);
    }

    public Integer getRemaining() {
        var limiterList = requestLimiterRepository.findAll();
        if (!limiterList.isEmpty()) {
            return limiterList.get(0).getRemaining();
        } else {
            createLimiter(LIMIT, LIMIT);
        }
        return LIMIT;
    }

}
