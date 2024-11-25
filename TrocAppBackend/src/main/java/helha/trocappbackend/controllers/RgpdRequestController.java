package helha.trocappbackend.controllers;

import helha.trocappbackend.models.RgpdRequest;
import helha.trocappbackend.services.RgpdRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rgpd-requests")
public class RgpdRequestController {
    @Autowired
    private RgpdRequestService rgpdRequestService;

    @PostMapping
    public RgpdRequest createRequest(@RequestBody RgpdRequest request) {
        return rgpdRequestService.createRequest(request);
    }

    @GetMapping
    public List<RgpdRequest> getAllRequests() {

        return rgpdRequestService.getAllRequests();
    }

    @PutMapping("/{id}")
    public RgpdRequest updateRequestStatus(@PathVariable Long id, @RequestParam String status) {
       return rgpdRequestService.updateRequestStatus(id, status);
    }
}