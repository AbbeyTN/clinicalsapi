package com.bharath.clinicals.controllers;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.clinicals.controllers.util.BMICalculator;
import com.bharath.clinicals.dto.ClinicalDataRequest;
import com.bharath.clinicals.model.ClinicalData;
import com.bharath.clinicals.model.Patient;
import com.bharath.clinicals.repos.ClinicalDataRepository;
import com.bharath.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
public class ClinicalDataController {
	int a= 0;
	int b=1;
	int c= 7;
	int d =3;
	int e =10;
	
	@Autowired
	private ClinicalDataRepository clinicalDataRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@RequestMapping(value="/clinicals", method=RequestMethod.POST)
	public ClinicalData save(@RequestBody ClinicalDataRequest  request ) {
		Patient patient = patientRepository.findById(request.getPatientID()).orElse(null);
		
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		
		return clinicalDataRepository.save(clinicalData);
	}

	@RequestMapping(value="/clinicals/{patientId}/{componentName}", method=RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable ("patientId") int patientId, 
			@PathVariable ("componentName") String componentName ) throws CloneNotSupportedException{
		
		if(componentName.equals("bmi")){
			componentName = "hw";
		}
	
		 List<ClinicalData> clinicalData;
		
			clinicalData = clinicalDataRepository.
												findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId,componentName);
		
		
		 
		 Iterator<ClinicalData> iterator = clinicalData.iterator();
		 List<ClinicalData> duplicateClinicalData = new ArrayList<>();
		 
		 while(iterator.hasNext())
		 {
		   //Add the object clones
		     duplicateClinicalData.add((ClinicalData) iterator.next().clone());  
		 }
				  
		
			for(ClinicalData eachEntry:duplicateClinicalData) {
				BMICalculator.calculateBMI(clinicalData, eachEntry);
			}
			
		return clinicalData;

	}
	
	
		
	
	

}
