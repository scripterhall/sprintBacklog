package com.ms.sprintbacklogservice.entity;

import com.ms.sprintbacklogservice.model.Sprint;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SprintBacklog {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ; 
   
    private Long sprintId;

    @Transient
    private Sprint sprint ;

}
