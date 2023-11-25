package com.monero.multibooks.MultiBooks.Controllers.BusinessTeam;

import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamRequest;
import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.BusinessTeam.BusinessTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/business-team/")
public class BusinessTeamController {



    private final BusinessTeamService businessTeamService;

    public BusinessTeamController(BusinessTeamService businessTeamService) {
        this.businessTeamService = businessTeamService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createBusinessTeam(@RequestBody BusinessTeamRequest request){
        System.out.println(request);
        BusinessTeamResponse response = businessTeamService.createBusinessTeam(request);
        return ResponseEntity.ok(new ApiResponse(response,"Business team has been successfully created"));
    }

    @PatchMapping("set/{mail}/{CVRNumber}")
    public ResponseEntity<ApiResponse> addUserToBusinessTeam(@PathVariable String mail, @PathVariable int CVRNumber){
        ApiResponse response = businessTeamService.addUserToBusinessTeam(mail, CVRNumber);
        return ResponseEntity.ok(response);
    }

}
