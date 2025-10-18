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
