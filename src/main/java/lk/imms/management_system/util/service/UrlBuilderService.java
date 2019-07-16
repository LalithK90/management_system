package lk.imms.management_system.util.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UrlBuilderService {
    public String doSomething(UriComponentsBuilder uriComponentsBuilder, String path) {
        URI someNewUriBasedOnCurrentRequest = uriComponentsBuilder
                .replacePath(null)
                .replaceQuery(null)
                .pathSegment(path)
                .build()
                .toUri();
        //... output -> http://localhost:8090/some/new/path

/*
        URI someNewUriBasedOnCurrentRequest = uriComponentsBuilder
                .build().toUri();
        //... output -> http://localhost:8090

*/
        System.out.println(someNewUriBasedOnCurrentRequest.toString());
        return someNewUriBasedOnCurrentRequest.toString();
    }
}
