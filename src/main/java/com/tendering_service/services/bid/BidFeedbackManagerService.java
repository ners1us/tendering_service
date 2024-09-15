package com.tendering_service.services.bid;

import java.util.UUID;

public interface BidFeedbackManagerService {
    void submitFeedback(UUID bidId, String feedback, String username);
}
