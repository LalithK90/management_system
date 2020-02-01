package lk.imms.management_system.asset.message.service;

import lk.imms.management_system.asset.message.dao.EmailMessageDao;
import lk.imms.management_system.asset.message.entity.EmailMessage;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = "emailMessage" )
public class EmailMessageService implements AbstractService< EmailMessage, Long > {
    private final EmailMessageDao emailMessageDao;

    @Autowired
    public EmailMessageService(EmailMessageDao emailMessageDao) {
        this.emailMessageDao = emailMessageDao;
    }

    @Override
    @Cacheable
    public List< EmailMessage > findAll() {
        return emailMessageDao.findAll();
    }

    @Override
    @Cacheable
    public EmailMessage findById(Long id) {
        return emailMessageDao.getOne(id);
    }

    @Override
    @Caching( evict = {@CacheEvict( value = "emailMessage", allEntries = true )},
            put = {@CachePut( value = "emailMessage", key = "#emailMessage.id" )} )
    public EmailMessage persist(EmailMessage emailMessage) {
        return emailMessageDao.save(emailMessage);
    }

    @Override
    @CacheEvict( allEntries = true )
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< EmailMessage > search(EmailMessage emailMessage) {
        return null;
    }
}
