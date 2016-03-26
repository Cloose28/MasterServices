package com.nenazvan.enums;

public enum MainMenu {
    ADD_ORDER(0), DELETE_ORDER(1), ORDERS_OF_MASTER(2), ACTUAL_ORDERS(3), EXPIRED_ORDERS(4), EXIT(5), EXEPTION(6);

    private int selection;

    MainMenu(int selection) {
        this.selection = selection;
    }

    public static MainMenu getInstance(int selection) {
        switch (selection) {
            case 0:
                return ADD_ORDER;
            case 1:
                return DELETE_ORDER;
            case 2:
                return ORDERS_OF_MASTER;
            case 3:
                return ACTUAL_ORDERS;
            case 4:
                return EXPIRED_ORDERS;
            case 5:
                return EXIT;
            default:
                return EXEPTION;
        }
    }
}