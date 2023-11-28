package com.monero.multibooks.MultiBooks.Controllers.BusinessTeam;

import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamRequest;
import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.BusinessTeam.BusinessTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/business-team/")
public class BusinessTeamController {

    private final BusinessTeamService businessTeamService;

    public BusinessTeamController(BusinessTeamService businessTeamService) {
        this.businessTeamService = businessTeamService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createBusinessTeam(@RequestBody BusinessTeamRequest request) {
        System.out.println(request);
        BusinessTeamResponse response = businessTeamService.createBusinessTeam(request);
        return ResponseEntity.ok(new ApiResponse(response, "Business team has been successfully created"));
    }

    @PatchMapping("set/{mail}/{CVRNumber}")
    public ResponseEntity<ApiResponse> addUserToBusinessTeam(@PathVariable String mail, @PathVariable int CVRNumber) {
        System.out.println(mail + " " + CVRNumber);
        ApiResponse response = businessTeamService.addUserToBusinessTeam(mail, CVRNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("user/{mail}")
    public ResponseEntity<ApiResponse> userApartOfBusinessTeam(@PathVariable String mail, HttpServletRequest request) {
        List<BusinessTeamResponse> response = businessTeamService.userApartOfBusinessTeam(mail, request);
        return ResponseEntity.ok(new ApiResponse(response, "All teams user is apart of"));
    }

    @GetMapping("get/{CVRNumber}")
    public ResponseEntity<ApiResponse> getBusinessTeam(@PathVariable int CVRNumber, HttpServletRequest request) {
        BusinessTeamResponse response = businessTeamService.getBusinessTeamById(CVRNumber, request);
        return ResponseEntity.ok(new ApiResponse(response, "Business team found"));
    }

    @DeleteMapping("delete/{CVRNumber}")
    public ResponseEntity<ApiResponse> deleteBusinessTeam(@PathVariable int CVRNumber, HttpServletRequest request) {
        BusinessTeamResponse response = businessTeamService.deleteBusinessTeamById(CVRNumber, request);
        return ResponseEntity.ok(new ApiResponse(response, "Business team deleted"));
    }

}
