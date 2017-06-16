package ru.singulight.duffelbag.nodes.types;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 11.01.16.
 */
public enum NodeType {
    TEMPERATURE(1),
    REL_HUMIDITY(2),
    ATMOSPHERIC_PRESSURE(3),
    RAINFALL(4),
    WIND_SPEED(5),
    POWER(6),
    POWER_CONSUMPTION(7),
    VOLTAGE(8),
    CURRENT(9),
    WATER_FLOW(10),
    WATER_CONSUMPTION(11),
    RESISTANCE(12),
    GAS_CONCENTRATION(13),
    PUSH_BUTTON(14),
    SWT(15),
    OTHER(16),
    TEXT(17),
/* Thing types */
    RGB_LAMP(10000),
    DIMMER(10001),
    SWITCH(10002),
    OUTLET(10003),
/* Actuator types */
    RGB(20000),
    RELAY(20001),
    AC_VOLTAGE(20003);

    NodeType(int i) {

    }
}
