package com.ms.sprintbacklogservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.sprintbacklogservice.entity.SprintBacklog;
import com.ms.sprintbacklogservice.repository.SprintBacklogRepo;

@Service
public class SprintBacklogService {
    

    @Autowired
    private SprintBacklogRepo sprintBacklogRepo;

    public SprintBacklog getSprintBacklog(Long id) {
        if(this.sprintBacklogRepo.existsById(id))
            return this.sprintBacklogRepo.findById(id).get();
        return null;
    }

    public SprintBacklog ajouterSprintBacklog(SprintBacklog sBacklog){
        return this.sprintBacklogRepo.save(sBacklog);
    }

    public boolean  supprimerSprintBacklog(Long id){
        Optional<SprintBacklog> sprintBacklog = this.sprintBacklogRepo.findById(id);
        if (sprintBacklog.isPresent()) {
            try {
                this.sprintBacklogRepo.deleteById(id);
                return true;
            } catch (EmptyResultDataAccessException ex) {
                ex.printStackTrace();
                return false;
            }
          }return false;

    }

    public SprintBacklog getSprintBacklogBySprint(Long idSprint){
        return this.sprintBacklogRepo.findBySprintId(idSprint);
    }

    //eviter la confusion des nom 
    public SprintBacklog modifierSprintBacklog(SprintBacklog sprintBacklog){
        return this.sprintBacklogRepo.save(sprintBacklog);
    }


}
