package lk.imms.management_system.asset.message.controller;

import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.employee.service.EmployeeService;
import lk.imms.management_system.asset.message.entity.EmailMessage;
import lk.imms.management_system.asset.message.service.EmailMessageService;
import lk.imms.management_system.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/emailMessage" )
public class EmailMessageController {
    private final EmailMessageService emailMessageService;
    private final CommonService commonService;
    private final EmailService emailService;
    private final EmployeeService employeeService;

    @Autowired
    public EmailMessageController(EmailMessageService emailMessageService, CommonService commonService,
                                  EmailService emailService, EmployeeService employeeService) {
        this.emailMessageService = emailMessageService;
        this.commonService = commonService;
        this.emailService = emailService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String allMessage(Model model) {
        model.addAttribute("emailMessages", emailMessageService.findAll());
        return "emailMessage/emailMessage";
    }

    @GetMapping( "/{id}" )
    public String viewEmailMessage(@PathVariable Long id, Model model) {
        model.addAttribute("emailMessageDetail", emailMessageService.findById(id));
        return "emailMessage/emailMessage-detail";
    }

    @GetMapping( "/add" )
    public String getMessageForm(Model model) {
        model.addAttribute("emailMessage", new EmailMessage());
        //url to find employee
        commonService.commonUrlBuilder(model);
        return "emailMessage/addEmailMessage";
    }

    @PostMapping( "/add" )
    public String sendEmailMessage(@Valid @ModelAttribute EmailMessage emailMessage, BindingResult result,
                                   Model model) {
        if ( result.hasErrors() ) {
            result.getAllErrors().forEach(System.out::println);
            model.addAttribute("emailMessage", emailMessage);
            commonService.commonUrlBuilder(model);
            return "emailMessage/addEmailMessage";
        }
        List< Employee > employees = new ArrayList<>();
        emailMessage.getEmployees().forEach(employee -> employees.add(employeeService.findById(employee.getId())));
        //if there is any duplicate it would remove using following
        emailMessage.setEmployees(employees.stream().distinct().collect(Collectors.toList()));

        // save email message to db and send it relevant employee
        EmailMessage emailMessageDb = emailMessageService.persist(emailMessage);
        //email subject
        String emailMessageDbSubject = emailMessageDb.getSubject();
        //email message
        String emailMessageDbMessage = emailMessageDb.getMessage();
        //send email all selected employee
        emailMessageDb.getEmployees().forEach(
                employee ->
                        emailService.sendEmail(
                                employee.getOfficeEmail(), emailMessageDbSubject, emailMessageDbMessage));

        return "redirect:/emailMessage/add";
    }
}
