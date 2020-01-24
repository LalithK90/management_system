package lk.imms.management_system.asset.report.service;


import lk.imms.management_system.asset.OffednerGuardian.dao.GuardianDao;
import lk.imms.management_system.asset.commonAsset.model.NameCount;
import lk.imms.management_system.asset.commonAsset.model.ParameterCount;
import lk.imms.management_system.asset.commonAsset.service.CommonService;
import lk.imms.management_system.asset.contravene.dao.ContraveneDao;
import lk.imms.management_system.asset.contravene.entity.Contravene;
import lk.imms.management_system.asset.court.dao.CourtDao;
import lk.imms.management_system.asset.crime.dao.CrimeDao;
import lk.imms.management_system.asset.detectionTeam.dao.DetectionTeamDao;
import lk.imms.management_system.asset.detectionTeam.dao.DetectionTeamMemberDao;
import lk.imms.management_system.asset.employee.dao.EmployeeDao;
import lk.imms.management_system.asset.minutePetition.dao.MinutePetitionDao;
import lk.imms.management_system.asset.minutePetition.dao.MinutePetitionFilesDao;
import lk.imms.management_system.asset.offender.dao.OffenderDao;
import lk.imms.management_system.asset.petition.dao.PetitionDao;
import lk.imms.management_system.asset.petition.dao.PetitionStatuDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petitionAddOffender.dao.PetitionOffenderDao;
import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lk.imms.management_system.asset.petitioner.dao.PetitionerDao;
import lk.imms.management_system.asset.userManagement.dao.RoleDao;
import lk.imms.management_system.asset.userManagement.dao.UserDao;
import lk.imms.management_system.asset.workingPlace.dao.WorkingPlaceDao;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import lk.imms.management_system.util.service.DateTimeAgeService;
import lk.imms.management_system.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {
    private final ContraveneDao contraveneDao;
    private final PetitionOffenderDao petitionOffenderDao;
    private final PetitionDao petitionDao;
    private final DetectionTeamMemberDao detectionTeamMemberDao;
    private final GuardianDao guardianDao;
    private final DateTimeAgeService dateTimeAgeService;
    private final CourtDao courtDao;
    private final CrimeDao crimeDao;
    private final DetectionTeamDao detectionTeamDao;
    private final EmployeeDao employeeDao;
    private final MinutePetitionDao minutePetitionDao;
    private final OffenderDao offenderDao;
    private final PetitionerDao petitionerDao;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final MinutePetitionFilesDao minutePetitionFilesDao;
    private final PetitionStatuDao petitionStatuDao;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final CommonService commonService;
    private final WorkingPlaceDao workingPlaceDao;

    @Autowired
    public ReportService(ContraveneDao contraveneDao, PetitionOffenderDao petitionOffenderDao,
                         PetitionDao petitionDao, DetectionTeamMemberDao detectionTeamMemberDao,
                         GuardianDao guardianDao, DateTimeAgeService dateTimeAgeService, CourtDao courtDao,
                         CrimeDao crimeDao, DetectionTeamDao detectionTeamDao, EmployeeDao employeeDao,
                         MinutePetitionDao minutePetitionDao, OffenderDao offenderDao, PetitionerDao petitionerDao,
                         UserDao userDao, RoleDao roleDao, MinutePetitionFilesDao minutePetitionFilesDao,
                         PetitionStatuDao petitionStatuDao,
                         MakeAutoGenerateNumberService makeAutoGenerateNumberService, CommonService commonService,
                         WorkingPlaceDao workingPlaceDao) {
        this.contraveneDao = contraveneDao;
        this.petitionOffenderDao = petitionOffenderDao;
        this.petitionDao = petitionDao;
        this.detectionTeamMemberDao = detectionTeamMemberDao;
        this.guardianDao = guardianDao;
        this.dateTimeAgeService = dateTimeAgeService;
        this.courtDao = courtDao;
        this.crimeDao = crimeDao;
        this.detectionTeamDao = detectionTeamDao;
        this.employeeDao = employeeDao;
        this.minutePetitionDao = minutePetitionDao;
        this.offenderDao = offenderDao;
        this.petitionerDao = petitionerDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.minutePetitionFilesDao = minutePetitionFilesDao;
        this.petitionStatuDao = petitionStatuDao;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.commonService = commonService;
        this.workingPlaceDao = workingPlaceDao;
    }

    //working place, contravene, count - start
    public List< NameCount > listContraveneWorkingPlace(LocalDate from, LocalDate to) {
        List< NameCount > listContraveneWorkingPlaceAndCount = new ArrayList<>();


        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            List< ParameterCount > parameterCounts = new ArrayList<>();
            NameCount nameCount;
            for ( Contravene contravene : contraveneDao.findAll().stream().distinct().collect(Collectors.toList()) ) {
                List< PetitionOffender > petitionOffenders = new ArrayList<>();
                ParameterCount parameterCount;
                for ( Petition petition : petitionDao.findByWorkingPlace(workingPlace) ) {
                    //petitionOffender according to petition and contravene
                    petitionOffenders =
                            petitionOffenderDao.findByPetitionAndContravenesAndCreatedAtBetween(petition,
                                                                                                contravene,
                                                                                                dateTimeAgeService.dateTimeToLocalDateStartInDay(from)
                                    , dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
                }
                parameterCount = new ParameterCount(contravene.getDetail(),
                                                                   (long) petitionOffenders.size());
                parameterCounts.add(parameterCount);
            }
            nameCount = new NameCount(workingPlace.getCode(), workingPlace.getName(), Long.valueOf("0"),
                                      parameterCounts);
            listContraveneWorkingPlaceAndCount.add(nameCount);
        }
        return listContraveneWorkingPlaceAndCount;
    }

/*
    //working place, contravene, count - end
//----------------------------------------------------------------------------------------------
    //working place, crime count - start
    //crime states complete no
    public List< NameCount > listCrimeWorkingPlaceCrimeStatusNo(LocalDate from, LocalDate to) {
// crime count, working place
        List< NameCount > crimeCountCrimeStatusNo = new ArrayList<>();
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            List< Crime > crimes = new ArrayList<>();
            for ( User user : userDao.findByWorkingPlaces(workingPlace) ) {
                crimes.addAll(crimeDao.findByCreatedByAndCreatedAtBetween(user,
                                                                          dateTimeAgeService
                                                                          .dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)));
            }
            NameCount nameCount = new NameCount(CrimeStatus.NO.toString(), workingPlace.getName(),
                                                crimes
                                                        .stream()
                                                        .filter(crime ->
                                                                        crime.getCrimeStatus().equals(CrimeStatus.NO))
                                                        .count());
            crimeCountCrimeStatusNo.add(nameCount);
        }

        return crimeCountCrimeStatusNo;
    }*/
/*
    //crime states complete partially
    public List< NameCount > listCrimeWorkingPlaceCrimeStatusPartially(LocalDate from, LocalDate to) {
// crime count, working place
        List< NameCount > crimeCountCrimeStatusPartially = new ArrayList<>();
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            List< Crime > crimes = new ArrayList<>();
            for ( User user : userDao.findByWorkingPlaces(workingPlace) ) {
                crimes.addAll(crimeDao.findByCreatedByAndCreatedAtBetween(user,
                                                                          dateTimeAgeService
                                                                          .dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)));
            }
            NameCount nameCount = new NameCount(CrimeStatus.PARTIAL.toString(), workingPlace.getName(),
                                                (long) crimes
                                                        .stream()
                                                        .filter(crime ->
                                                                        crime.getCrimeStatus().equals(CrimeStatus
                                                                        .PARTIAL))
                                                        .count());
            crimeCountCrimeStatusPartially.add(nameCount);
        }

        return crimeCountCrimeStatusPartially;
    }

    //crime states complete
    public List< NameCount > listCrimeWorkingPlaceCrimeStatusComplete(LocalDate from, LocalDate to) {
// crime count, working place
        List< NameCount > crimeCountStatusComplete = new ArrayList<>();
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            List< Crime > crimes = new ArrayList<>();
            for ( User user : userDao.findByWorkingPlaces(workingPlace) ) {
                crimes.addAll(crimeDao.findByCreatedByAndCreatedAtBetween(user,
                                                                          dateTimeAgeService
                                                                          .dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)));
            }
            NameCount nameCount = new NameCount(CrimeStatus.COMPLETED.toString(), workingPlace.getName(),
                                                (long) crimes
                                                        .stream()
                                                        .filter(crime ->
                                                                        crime.getCrimeStatus().equals(CrimeStatus
                                                                        .COMPLETED))
                                                        .count());
            crimeCountStatusComplete.add(nameCount);
        }

        return crimeCountStatusComplete;
    }

    //working place, crime count - end
//----------------------------------------------------------------------------------------------
    //detection team - start
    public List< NameCount > detectionTeamStatusNotSuccess(LocalDate from, LocalDate to) {
        List< NameCount > detectionNotSuccess = new ArrayList<>();
//get all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
//get all petition related working place
            List< DetectionTeam > detectionTeams = new ArrayList<>();
            for ( Petition petition : petitionDao.findByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                        dateTimeAgeService
                                                                                        .dateTimeToLocalDateStartInDay(from)
                    , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
                detectionTeams = petition.getDetectionTeams()
                        .stream()
                        .filter(detectionTeam -> detectionTeam.getDetectionTeamStatus().equals(DetectionTeamStatus
                        .NOTSUCCESS))
                        .collect(Collectors.toList());
            }
            NameCount nameCount = new NameCount(DetectionTeamStatus.NOTSUCCESS.toString(), workingPlace.getName(),
                                                (long) detectionTeams.size());
            detectionNotSuccess.add(nameCount);
        }
        return detectionNotSuccess;
    }

    public List< NameCount > detectionTeamStatusSuccess(LocalDate from, LocalDate to) {
        List< NameCount > detectionSuccess = new ArrayList<>();
//get all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
//get all petition related working place
            List< DetectionTeam > detectionTeams = new ArrayList<>();
            for ( Petition petition : petitionDao.findByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                        dateTimeAgeService
                                                                                        .dateTimeToLocalDateStartInDay(from)
                    , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
                detectionTeams = petition.getDetectionTeams()
                        .stream()
                        .filter(detectionTeam -> detectionTeam.getDetectionTeamStatus().equals(DetectionTeamStatus
                        .SUCCESS))
                        .collect(Collectors.toList());
            }
            NameCount nameCount = new NameCount(DetectionTeamStatus.SUCCESS.toString(), workingPlace.getName(),
                                                (long) detectionTeams.size());
            detectionSuccess.add(nameCount);
        }
        return detectionSuccess;
    }

    //detection team - end
//----------------------------------------------------------------------------------------------
//guardian offender count - start
    public List< NameCount > guardianOffender(LocalDate from, LocalDate to) {
        List< NameCount > guardianOffenderCount = new ArrayList<>();
        for ( Offender offender : offenderDao.findByCreatedAtBetween(
                dateTimeAgeService.dateTimeToLocalDateStartInDay(from)
                , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
            NameCount nameCount = new NameCount(offender.getId().toString(), offender.getNameEnglish(),
                                                (long) offender.getGuardians().size());
            guardianOffenderCount.add(nameCount);
        }
        return guardianOffenderCount;
    }

    //guardian offender count - end
//----------------------------------------------------------------------------------------------
    //petition - start
    //working place count
    public List< NameCount > workingPlacePetitionCount(LocalDate from, LocalDate to) {
        List< NameCount > workingPlacePetitionCount = new ArrayList<>();
        //all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            List< Petition > petitions = petitionDao.findByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                           dateTimeAgeService
                                                                                           .dateTimeToLocalDateStartInDay(from)
                    , dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
            NameCount nameCount = new NameCount(workingPlace.getCode(), workingPlace.getName(),
                                                (long) petitions.size());
            workingPlacePetitionCount.add(nameCount);
        }
        return workingPlacePetitionCount;
    }

    //working place petition priority
    public List< NameCount > workingPlacePetitionPriority(LocalDate from, LocalDate to) {
        List< NameCount > workingPlacePetitionPriority = new ArrayList<>();
        //all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            for ( PetitionPriority petitionPriority : PetitionPriority.values() ) {
                List< Petition > petitions = petitionDao.findByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                               dateTimeAgeService
                                                                                               .dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to))
                        .stream()
                        .filter(petition -> petition.getPetitionPriority().equals(petitionPriority))
                        .collect(Collectors.toList());
                NameCount nameCount = new NameCount(petitionPriority.getPetitionPriority(), workingPlace.getName(),
                                                    (long) petitions.size());
                workingPlacePetitionPriority.add(nameCount);
            }
        }
        return workingPlacePetitionPriority;
    }

    //working place petition type
    public List< NameCount > workingPlacePetitionType(LocalDate from, LocalDate to) {
        List< NameCount > workingPlacePetitionType = new ArrayList<>();
        //all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            for ( PetitionType petitionType : PetitionType.values() ) {
                List< Petition > petitions = petitionDao.findByWorkingPlaceAndCreatedAtBetween(workingPlace,
                                                                                               dateTimeAgeService
                                                                                               .dateTimeToLocalDateStartInDay(from)
                        , dateTimeAgeService.dateTimeToLocalDateEndInDay(to))
                        .stream()
                        .filter(petition -> petition.getPetitionType().equals(petitionType))
                        .collect(Collectors.toList());
                NameCount nameCount = new NameCount(petitionType.getPetitionType(), workingPlace.getName(),
                                                    (long) petitions.size());
                workingPlacePetitionType.add(nameCount);
            }
        }
        return workingPlacePetitionType;
    }

    //working place petition state type
    public List< NameCount > workingPlacePetitionStateType(LocalDate from, LocalDate to) {
        List< NameCount > workingPlacePetitionStateType = new ArrayList<>();
        //all working place
        for ( WorkingPlace workingPlace : workingPlaceDao.findAll() ) {
            for ( PetitionStateType petitionStateType : PetitionStateType.values() ) {
                List< PetitionState > petitionStates =
                        petitionStatuDao.findByPetitionStateTypeAndCreatedAtBetween(petitionStateType,
                                                                                    dateTimeAgeService
                                                                                    .dateTimeToLocalDateStartInDay(from)
                                , dateTimeAgeService.dateTimeToLocalDateEndInDay(to))
                                .stream()
                                .filter(petition -> petition.getPetition().getWorkingPlace().equals(workingPlace))
                                .collect(Collectors.toList());
                NameCount nameCount = new NameCount(petitionStateType.getPetitionStateType(), workingPlace.getName(),
                                                    (long) petitionStates.size());
                workingPlacePetitionStateType.add(nameCount);
            }
        }
        return workingPlacePetitionStateType;
    }

    //petition - end
//----------------------------------------------------------------------------------------------
    //contravene - start
    public List< NameCount > contraveneCountAll(LocalDate from, LocalDate to) {
        List< NameCount > contraveneCountAll = new ArrayList<>();
        for ( Contravene contravene : contraveneDao.findAll() ) {
            List< PetitionOffender > petitionOffenders = petitionOffenderDao.findByContravenesAndCreatedAtBetween(
                    contravene, dateTimeAgeService.dateTimeToLocalDateStartInDay(from)
                    , dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
            NameCount count = new NameCount(contravene.getCode(), contravene.getDetail(),
                                            (long) petitionOffenders.size());
            contraveneCountAll.add(count);
        }
        return contraveneCountAll;
    }

    //contravene - end
//----------------------------------------------------------------------------------------------
    //employee - start
    public List< NameCount > employeeDetectionTeam(LocalDate from, LocalDate to) {
        List< NameCount > employeeDetectionTeam = new ArrayList<>();
        for ( Employee employee : employeeDao.findAll() ) {
            List< DetectionTeam > detectionTeams = new ArrayList<>();
            for ( DetectionTeamMember detectionTeamMember :
                    detectionTeamMemberDao.findByEmployeeAndCreatedAtBetween(employee,
                                                                             dateTimeAgeService
                                                                             .dateTimeToLocalDateStartInDay(from)
                            , dateTimeAgeService.dateTimeToLocalDateEndInDay(to)) ) {
                detectionTeams.add(detectionTeamMember.getDetectionTeam());
            }
            NameCount nameCount = new NameCount("", employee.getName(), (long) detectionTeams.size());
            employeeDetectionTeam.add(nameCount);
        }
        return employeeDetectionTeam;
    }
    //employee - end
//----------------------------------------------------------------------------------------------
*/

}
