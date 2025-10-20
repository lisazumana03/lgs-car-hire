/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
package za.co.carhire.dto;

import java.util.Date;

public record InsuranceDTO(
    int insuranceID,
    Date insuranceStartDate,
    double insuranceCost,
    String insuranceProvider,
    String status,
    String policyNumber,
    String mechanic,
    Integer car) {

}
