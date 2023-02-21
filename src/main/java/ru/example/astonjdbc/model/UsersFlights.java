package ru.example.astonjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersFlights {
    private int userFlightsId;
    private int userId;
    private String username;
    private int flightId;
}