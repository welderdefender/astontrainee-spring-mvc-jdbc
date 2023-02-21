package ru.example.astonjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private int flightId;
    private int company;
    private String companyName;
    private int airportCode;
    private String airportCodeName;
    private String flightDate;
    private String flightFrom;
    private String flightTo;
}