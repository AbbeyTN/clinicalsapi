package com.bharath.clinicals.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.print.event.PrintJobAttributeEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "clinicaldata")
public class ClinicalData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(name = "component_name")
	private String componentName;
	
	@Column(name = "component_value")
	private String componentValue;
	
	@Column(name = "measured_date_time")
	private Timestamp measuredDateTime;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="patient_id",nullable = false)
	@JsonIgnore
	private Patient patient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponentNameString() {
		return componentName;
	}

	public void setComponentName(String componentNameString) {
		this.componentName = componentNameString;
	}

	public String getComponentValue() {
		return componentValue;
	}

	public void setComponentValue(String componentValue) {
		this.componentValue = componentValue;
	}

	public Timestamp getMeasuredDateTime() {
		return measuredDateTime;
	}

	public void setMeasuredDateTime(Timestamp measuredDateTime) {
		this.measuredDateTime = measuredDateTime;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	 @Override
	public Object clone() throws CloneNotSupportedException {
	        ClinicalData clone = new ClinicalData();
	        
	            clone.setComponentValue(componentValue);
	            clone.setComponentName(componentName);
	            clone.setId(id);
	            clone.setMeasuredDateTime(measuredDateTime);
	            clone.setPatient(patient);
	 
	          
	       
	        return clone;
	    }



	
	

}
