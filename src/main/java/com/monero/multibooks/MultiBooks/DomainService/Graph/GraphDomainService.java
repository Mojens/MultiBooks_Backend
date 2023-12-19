package com.monero.multibooks.MultiBooks.DomainService.Graph;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
public class GraphDomainService {
    public String getQuarter(Instant startDate, Instant endDate) {
        LocalDate startLocalDate = startDate.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.atZone(ZoneId.systemDefault()).toLocalDate();

        YearMonth startYearMonth = YearMonth.of(startLocalDate.getYear(), startLocalDate.getMonth());
        YearMonth endYearMonth = YearMonth.of(endLocalDate.getYear(), endLocalDate.getMonth());

        if (startYearMonth.equals(endYearMonth)) {
            int month = startLocalDate.getMonthValue();
            if (month <= 3) {
                return "Quarter 1";
            } else if (month <= 6) {
                return "Quarter 2";
            } else if (month <= 9) {
                return "Quarter 3";
            } else {
                return "Quarter 4";
            }
        } else {
            return "Multiple Quarters";
        }
    }
}
