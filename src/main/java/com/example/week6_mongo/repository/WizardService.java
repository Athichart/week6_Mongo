package com.example.week6_mongo.repository;

import com.example.week6_mongo.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository){
        this.repository = repository;
    }
    public List<Wizard> retriveWizard(){
        return repository.findAll();
    }
    public Wizard createWizard(Wizard wizard){
        return repository.save(wizard);
    }
    public Wizard retriveWizardByName(String name){
        return repository.findByName(name);
    }
    public Wizard retriveWizardById(String _id){
        return repository.findId(_id);
    }
    public Wizard updateWizard(Wizard wizard){
        return repository.save(wizard);
    }
    public boolean deleteWizard(Wizard wizard){
        try{ repository.delete(wizard); return true;}
        catch (Exception e){return false;}
    }

}
