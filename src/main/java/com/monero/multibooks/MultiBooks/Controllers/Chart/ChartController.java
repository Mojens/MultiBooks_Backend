package com.monero.multibooks.MultiBooks.Controllers.Chart;

import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Sales.InvoiceSalesResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Chart.ChartService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/chart/")
public class ChartController {

    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }



    @GetMapping("/sales/{cvrNumber}")
    public ResponseEntity<ApiResponse> getSalesChart(@PathVariable int cvrNumber,
                                                     @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                     @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                     HttpServletRequest httpRequest){
        List<InvoiceSalesResponse> response = chartService.salesForTheYear(cvrNumber, start, end, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "Sales for the year"));
    }




}
