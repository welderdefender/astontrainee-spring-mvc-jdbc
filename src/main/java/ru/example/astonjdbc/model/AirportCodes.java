package ru.example.astonjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportCodes {
    private int airportCodeId;
    private String airportCodeName;
}