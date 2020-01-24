package lk.imms.management_system.asset.report.controller;

import lk.imms.management_system.asset.commonAsset.model.NameCount;
import lk.imms.management_system.asset.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping( "/report" )
public class ReportRestController {
    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/contra")
    public List< NameCount > find() {
        return reportService.listContraveneWorkingPlace(LocalDate.now().minusMonths(1), LocalDate.now());
    }
}
