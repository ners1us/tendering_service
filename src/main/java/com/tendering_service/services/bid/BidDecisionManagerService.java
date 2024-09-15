package com.tendering_service.services.bid;

import com.tendering_service.enums.DecisionStatus;

import java.util.UUID;

public interface BidDecisionManagerService {
    void submitDecision(UUID bidId, DecisionStatus decision, String username);
}
