package lk.imms.management_system.asset.report.controller;

import lk.imms.management_system.asset.commonAsset.model.TwoDate;
import lk.imms.management_system.asset.contravene.service.ContraveneService;
import lk.imms.management_system.asset.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/report" )
public class ReportController {
    private final ReportService reportService;
    private final ContraveneService contraveneService;

    @Autowired
    public ReportController(ReportService reportService, ContraveneService contraveneService) {
        this.reportService = reportService;
        this.contraveneService = contraveneService;
    }

    @GetMapping
    public String reportView(Model model) {
        model.addAttribute("dateObject", new TwoDate());
        model.addAttribute("contravenes", contraveneService.findAll().stream().distinct().collect(Collectors.toList()));
        model.addAttribute("contraveneList",reportService.listContraveneWorkingPlace(LocalDate.now().minusMonths(1), LocalDate.now()));
        return "report/contraveneReport";
    }

}
