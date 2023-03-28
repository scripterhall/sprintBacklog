package com.ms.sprintbacklogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.sprintbacklogservice.entity.SprintBacklog;
import com.ms.sprintbacklogservice.model.Sprint;
import com.ms.sprintbacklogservice.service.SprintBacklogService;
import com.ms.sprintbacklogservice.service.SprintFeignClient;

@RestController
@RequestMapping("/sprint-backlogs")
public class SprintBacklogController {
    
    @Autowired
    private SprintBacklogService sprintBacklogService;

    @Autowired
    private SprintFeignClient sprintFeignClient;


    @GetMapping("/{id}")
    public ResponseEntity<SprintBacklog> getSprintBacklog(@PathVariable("id") Long id) {
       
        SprintBacklog spb = sprintBacklogService.getSprintBacklog(id);

        if (spb == null) {
            return ResponseEntity.notFound().build();
        }
     
        Sprint sprint = this.sprintFeignClient.getSprintById(spb.getSprintId());
        spb.setSprint(sprint);
        
        return  new ResponseEntity<>(spb, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<SprintBacklog> ajouterSprintBacklog(@RequestBody SprintBacklog sBacklog) {
        System.out.print(sBacklog.getSprintId());
        Sprint sp  = this.sprintFeignClient.getSprintById(sBacklog.getSprintId());
        SprintBacklog sprintBacklog = sprintBacklogService.ajouterSprintBacklog(sBacklog);
       
        sprintBacklog.setSprint(sp);
        sprintBacklog.setSprintId(sBacklog.getSprintId());

        return new ResponseEntity<>(sprintBacklog, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerSprintBacklog(@PathVariable("id") Long id) {
        
        boolean isDeleted = sprintBacklogService.supprimerSprintBacklog(id);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
    
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sprint/{id-sprint}")
    @ResponseBody
    public ResponseEntity<SprintBacklog> getSprintBacklogBySprint(@PathVariable("id-sprint") Long idSprint) {
        SprintBacklog spb = sprintBacklogService.getSprintBacklogBySprint(idSprint);

        if (spb == null) {
            return ResponseEntity.notFound().build();
        }
    
        Sprint sprint = this.sprintFeignClient.getSprintById(spb.getSprintId());
        spb.setSprint(sprint);
    
        return new ResponseEntity<>(spb, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SprintBacklog> modifierSprintBacklog(@RequestBody SprintBacklog spb){
        
        SprintBacklog modifiedSprintBacklog = this.sprintBacklogService.modifierSprintBacklog(spb);
        
        if (modifiedSprintBacklog == null) 
            return ResponseEntity.notFound().build();
        modifiedSprintBacklog.setSprint(spb.getSprint());
        modifiedSprintBacklog.setSprintId(spb.getSprint().getId());
        return new ResponseEntity<>(modifiedSprintBacklog, HttpStatus.OK);
    }

    
   

}
