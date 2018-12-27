package com.jerryxie.redfin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerryxie.redfin.domain.House;
import com.jerryxie.redfin.repository.HouseRepository;

@Service
public class HouseService {

    @Autowired
    private HouseRepository homeRepo;

    public void addHomes(List<House> homes) {
        this.homeRepo.insert(homes);
    }
}
