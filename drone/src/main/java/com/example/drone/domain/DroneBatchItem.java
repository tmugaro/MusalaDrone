package com.example.drone.domain;

import javax.persistence.*;

@Entity
@Table(name = "drone_batch_item")
public class DroneBatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_number")
    private DroneBatchNumber batchNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public DroneBatchNumber getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(DroneBatchNumber batchNumber) {
        this.batchNumber = batchNumber;
    }
}
