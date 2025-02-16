package com.gotenberg.poc.service;

import dev.inaka.Jotenberg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CallGotenbergService {

    @Autowired
    private Jotenberg jotenberg;

    public String callGotenberg(File fileToConvert) {

        return null;
    }

}
