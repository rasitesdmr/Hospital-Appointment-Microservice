package com.rasitesdmr.appointmentservice.controller;

import com.rasitesdmr.appointmentservice.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;

    }

    @PostMapping("/appointmentMakingProcess")
    public String appointmentMakingProcess(@RequestParam(name = "cityId") long cityId,
                                                                                 @RequestParam(name = "hospitalId") long hospitalId,
                                                                                 @RequestParam(name = "clinicId") long clinicId,
                                                                                 @RequestParam(name = "doctorIdentityNumber") String doctorIdentityNumber,
                                                                                 @RequestParam(name = "appointmentTime") String appointmentTime,
                                                                                 @RequestParam(name = "appointmentDate") String appointmentDate) {
       return appointmentService.appointmentMakingProcess(cityId,hospitalId,clinicId,doctorIdentityNumber,appointmentTime,appointmentDate);
    }

    @DeleteMapping("/appointmentRemove")
    public boolean appointmentRemove(@RequestParam(name = "appointmentId") Long appointmentId){
        return appointmentService.appointmentRemove(appointmentId);
    }
}
