package lk.imms.management_system.asset.petitionAddOffender.service;

        import lk.imms.management_system.asset.contravene.entity.Contravene;
        import lk.imms.management_system.asset.offender.entity.Offender;
        import lk.imms.management_system.asset.petition.entity.Petition;
        import lk.imms.management_system.asset.petitionAddOffender.dao.PetitionOffenderDao;
        import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
        import lk.imms.management_system.util.interfaces.AbstractService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.cache.annotation.*;
        import org.springframework.data.domain.Example;
        import org.springframework.data.domain.ExampleMatcher;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.time.LocalDateTime;
        import java.util.List;

@Service
@CacheConfig( cacheNames = {"petitionOffender"} )
public class PetitionOffenderService implements AbstractService< PetitionOffender, Long > {
    private final PetitionOffenderDao petitionOffenderDao;

    @Autowired
    public PetitionOffenderService(PetitionOffenderDao petitionOffenderDao) {
        this.petitionOffenderDao = petitionOffenderDao;
    }

    @Override
    @Cacheable
    public List< PetitionOffender > findAll() {
        return petitionOffenderDao.findAll();
    }

    @Override
    @Cacheable
    public PetitionOffender findById(Long id) {
        return petitionOffenderDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "petitionOffender", allEntries = true )},
            put = {@CachePut( value = "petitionOffender", key = "#petitionOffender.id" )} )
    public PetitionOffender persist(PetitionOffender petitionOffender) {
        return petitionOffenderDao.save(petitionOffender);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        //this is not applicable
        return false;
    }

    @Override
    @Cacheable
    public List< PetitionOffender > search(PetitionOffender petitionOffender) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PetitionOffender > petitionOffenderExample = Example.of(petitionOffender, matcher);
        return petitionOffenderDao.findAll(petitionOffenderExample);
    }

    @Caching( evict = {@CacheEvict( value = "petitionOffender", allEntries = true )},
            put = {@CachePut( value = "petitionOffender", key = "#petitionOffender.id" )} )
    public List< PetitionOffender > persistAll(List< PetitionOffender > petitionOffenders) {
        return petitionOffenderDao.saveAll(petitionOffenders);
    }

    @Cacheable
    public List< PetitionOffender > findByPetition(Petition petition) {
        return petitionOffenderDao.findByPetition(petition);
    }

    @CacheEvict( allEntries = true )
    public void deleteAll(List< PetitionOffender > petitionOffenders) {
        petitionOffenderDao.deleteAll(petitionOffenders);
    }

    @Cacheable
    public PetitionOffender getLast() {
        return petitionOffenderDao.findFirstByOrderByIdDesc();
    }

    @Cacheable
    public PetitionOffender findByPetitionAndOffender(Petition petition, Offender offender) {
        return petitionOffenderDao.findByPetitionAndOffender(petition, offender);
    }

    @Cacheable
    public List< PetitionOffender > countByOffenderAndCreatedAtBetween(Offender offender, LocalDateTime from,
                                                                       LocalDateTime to) {
        return petitionOffenderDao.countByOffenderAndCreatedAtBetween(offender, from, to);
    }

    @Cacheable
    @Transactional
    public List< PetitionOffender > findByContravenes(Contravene contravene) {
        /*List<PetitionOffender> contravenes1 = new ArrayList<>();
        return contravenes1;*/
        return petitionOffenderDao.findByContravenes(contravene);
    }

    @Cacheable
    public List< PetitionOffender > findByOffenderAndContravene(Offender offender, Contravene contravene) {
        return petitionOffenderDao.findByOffenderAndContravenes(offender, contravene);
    }

    @Cacheable
    public List<PetitionOffender> findByOffender(Offender offender){
       return petitionOffenderDao.findByOffender(offender);
    }
}
