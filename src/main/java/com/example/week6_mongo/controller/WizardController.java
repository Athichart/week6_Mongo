package com.example.week6_mongo.controller;

import com.example.week6_mongo.pojo.Wizard;
import com.example.week6_mongo.pojo.Wizards;
import com.example.week6_mongo.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;
    protected Wizards wizards;

    public WizardController() {
        wizards = new Wizards();

    }


    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizards(){
        List<Wizard> wizards_list = wizardService.retriveWizard();
        return ResponseEntity.ok(wizards_list);
    }
    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> addWizard(@RequestParam("sex") String sex,
                                           @RequestParam("name") String name,
                                           @RequestParam("position") String position,
                                           @RequestParam("money") String money,
                                           @RequestParam("school") String school,
                                           @RequestParam("house") String house){
        Wizard wizard = wizardService.createWizard(new Wizard(null, sex, name, school, house, money, position));
        return ResponseEntity.ok(wizard);
    }

    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestParam("sex") String sex,
                                @RequestParam("name") String name,
                                @RequestParam("position") String position,
                                @RequestParam("money") String money,
                                @RequestParam("school") String school,
                                @RequestParam("house") String house,
                                @RequestParam("old_name") String old_name){

        Wizard wizard = wizardService.retriveWizardByName(old_name);
        if(wizard != null){
            wizardService.updateWizard(new Wizard(wizard.get_id(), sex, name, school, house, money, position));
            return true;
        }
        else{
            return false;
        }
    }
    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestParam("name") String name){
        Wizard wizard = wizardService.retriveWizardByName(name);
        boolean status = wizardService.deleteWizard(wizard);
        return status;
    }
}
