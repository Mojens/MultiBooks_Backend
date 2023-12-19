package com.monero.multibooks.MultiBooks.Controllers.Chart;

import com.monero.multibooks.MultiBooks.Dto.Graph.Accounting.AccountingTotalResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Sales.InvoiceSalesResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Status.InvoiceStatusResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.TotalInvoices.TotalInvoicesResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalProducts.TotalProductsResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalUsers.TotalUsersResponse;
import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Chart.ChartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                                                     HttpServletRequest httpRequest) {
        List<InvoiceSalesResponse> response = chartService.salesForTheYear(cvrNumber, start, end, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "Sales for the year chart data retrieved"));
    }

    @GetMapping("/invoice-status/{cvrNumber}")
    public ResponseEntity<ApiResponse> getInvoiceCircleChart(@PathVariable int cvrNumber,
                                                             @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                             @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                             HttpServletRequest httpRequest) {
        List<InvoiceStatusResponse> response = chartService.getInvoiceCircleChart(cvrNumber, start, end, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "Invoice circle chart data retrieved"));
    }

    @GetMapping("/total-products/{cvrNumber}")
    public ResponseEntity<ApiResponse> getTotalProducts(@PathVariable int cvrNumber,
                                                        HttpServletRequest httpRequest) {
        TotalProductsResponse products = chartService.getTotalProducts(cvrNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(products, "Total products retrieved"));
    }

    @GetMapping("/total-users/{cvrNumber}")
    public ResponseEntity<ApiResponse> getTotalUsers(@PathVariable int cvrNumber,
                                                     HttpServletRequest httpRequest) {
        TotalUsersResponse users = chartService.getTotalUsers(cvrNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(users, "Total users retrieved"));
    }

    @GetMapping("/total-invoices/{cvrNumber}")
    public ResponseEntity<ApiResponse> getTotalInvoices(@PathVariable int cvrNumber,
                                                        HttpServletRequest httpRequest) {
        TotalInvoicesResponse invoices = chartService.getTotalInvoices(cvrNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoices, "Total invoices retrieved"));
    }

    @GetMapping("/total-vat/{cvrNumber}")
    public ResponseEntity<ApiResponse> getVatForQuarter(@PathVariable int cvrNumber,
                                                        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                        HttpServletRequest httpRequest) {
        AccountingTotalResponse response = chartService.totalVatForQuota(cvrNumber, start, end, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "Total invoices retrieved"));
    }

    @GetMapping("/invoice-status/{cvrNumber}/status/{statusCode}")
    public ResponseEntity<ApiResponse> getInvoicesByStatus(@PathVariable int cvrNumber,
                                                           @PathVariable int statusCode,
                                                           HttpServletRequest httpRequest,
                                                           Pageable pageable) {
        Page<InvoiceResponse> invoices = chartService.getInvoicesByStatus(cvrNumber, statusCode, pageable, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoices, "Invoices retrieved"));
    }


}
