/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * */
package za.co.carhire.factory.reservation;


import za.co.carhire.domain.Insurance;
import java.util.Date;

public class InsuranceFactory {

        public static Insurance createInsurance(int insuranceID, Date insuranceStartDate, double insuranceCost, String insuranceProvider, String status, long policyNumber, String mechanic) {
            if (insuranceID < 0 || insuranceID > 100000) {
                return null;
            }
            if(insuranceStartDate==null||insuranceStartDate==null){
            return null;
            }
            if (insuranceCost < 0) {
            return null;
            }
            if (insuranceProvider==null||description.isEmpty()){
            return null;
            }
            if (status==null||status.isEmpty()){
            return null;
            }
            if (policyNumber < 0) {
                return null;
            }
            if (mechanic==null||mechanic.isEmpty()){
            return null;
            }
            return new Insurance.Builder()
                    .setInsuranceID(insuranceID)
                    .setInsuranceStartDate(insuranceStartDate)
                    .setInsuranceCost(insuranceCost)
                    .setInsuranceProvider(insuranceProvider)
                    .setStatus(status)
                    .setPolicyNumber(policyNumber)
                    .setMechanic(mechanic)
                    .build();
        }
}
