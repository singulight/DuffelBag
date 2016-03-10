package ru.singulight.duffelbag.mqttnodes.types;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 11.01.16.
 */
public enum NodeType {
    TEMPERATURE,
    REL_HUMIDITY,
    ATMOSPHERIC_PRESSURE,
    RAINFALL,
    WIND_SPEED,
    POWER,
    POWER_CONSUMPTION,
    VOLTAGE,
    CURRENT,
    WATER_FLOW,
    WATER_CONSUMPTION,
    RESISTANCE,
    GAS_CONCENTRATION,
    PUSH_BUTTON,
    SWT,
    OTHER,
    TEXT,
/* Thing types */
    RGB_LAMP,
    DIMMER,
    SWITCH,
/* Actuator types */
    RGB,
    RELAY,
    AC_VOLTAGE
}
