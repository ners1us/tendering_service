package com.tendering_service.utils;

import java.util.UUID;

public class DataValidator {

    public static void checkIfUsernamesEqual(String username1, String username2) {
        if (!username1.equals(username2)) {
            throw new SecurityException("You do not have permission to interact with this item");
        }
    }

    public static void checkIfIdsEqual(UUID id1, UUID id2) {
        if (!id1.equals(id2)) {
            throw new SecurityException("You do not have permission to interact with this item");
        }
    }
}
