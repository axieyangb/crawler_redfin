package com.jerryxie.redfin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerryxie.redfin.domain.Home;
import com.jerryxie.redfin.repository.HomeRepository;

@Service
public class HomeService {

    @Autowired
    private HomeRepository homeRepo;

    public void saveHomes(List<Home> homes) {
        this.homeRepo.saveAll(homes);
    }
}
