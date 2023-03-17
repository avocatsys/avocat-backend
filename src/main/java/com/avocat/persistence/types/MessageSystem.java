package com.avocat.persistence.types;

import lombok.Getter;

@Getter
public enum MessageSystem {

    BRANCH_OFFICE_NOT_CAN_BE_NULL("branch office can`t be null."),
    CONTRACT_NOT_CAN_BE_NULL("contract can`t be null."),
    CUSTOMER_NOT_CAN_BE_NULL("customer can`t be null."),
    PROCESS_NOT_CAN_BE_NULL("process number can`t be null."),
    PROCESS_MUST_HAVE_OPPOSING_PARTY("process must have opposing party."),
    PROCESS_NEEDS_TO_HAVE_MAIN_PART("process needs to have main part."),
    PROCESS_AREA_NOT_CAN_BE_NULL("area of process can`t be null.");

    private final String message;

    MessageSystem(String message) {
        this.message = message;
    }
}
