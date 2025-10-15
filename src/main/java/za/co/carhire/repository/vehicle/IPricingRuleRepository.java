package za.co.carhire.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.vehicle.PricingRule;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPricingRuleRepository extends JpaRepository<PricingRule, Integer> {

    List<PricingRule> findByCarTypeCarTypeID(int carTypeID);

    List<PricingRule> findByActive(boolean active);

    List<PricingRule> findByCarTypeCarTypeIDAndActive(int carTypeID, boolean active);

    List<PricingRule> findByValidFromBeforeAndValidToAfter(LocalDate date1, LocalDate date2);
}
