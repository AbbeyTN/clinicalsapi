package com.bharath.clinicals.controllers;

import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.weaving.DefaultContextLoadTimeWeaver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.clinicals.controllers.util.BMICalculator;
import com.bharath.clinicals.model.ClinicalData;
import com.bharath.clinicals.model.Patient;
import com.bharath.clinicals.repos.ClinicalDataRepository;
import com.bharath.clinicals.repos.PatientRepository;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	Map<String,String> filters = new HashMap<>();
	
	@Autowired 
	private PatientRepository repository;
	
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	public List<Patient> getPatients(){
		return repository.findAll();
		
	}
	
	
	@RequestMapping(value="/patients", method=RequestMethod.POST)
	public Patient savePatient (@RequestBody Patient patient){
		return repository.save(patient);
		
	}
	
	@RequestMapping(value="/patients/{id}", method=RequestMethod.GET)
	public Patient getPatient (@PathVariable("id") int id){
		return repository.findById(id).get();
		
	}
	
	@RequestMapping(value="/patients/analyze/{id}", method=RequestMethod.GET)
	public Patient analyze (@PathVariable("id") int id) {
		Patient patient = repository.findById(id).get();
		List <ClinicalData> clinicalData = patient.getClinicalData();
		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for(ClinicalData eachEntry:duplicateClinicalData) {
			if(filters.containsKey(eachEntry.getComponentNameString())){
				clinicalData.remove(eachEntry);
				continue;
			}
			else {
				filters.put(eachEntry.getComponentNameString(), null);
			}
			BMICalculator.calculateBMI(clinicalData, eachEntry);
	
	}
		filters.clear();
	
		return patient;
	}


	private void calculateBMI(List<ClinicalData> clinicalData, ClinicalData eachEntry) {
		if(eachEntry.getComponentNameString().equals("hw")) {
			String [] heightAndWeight = eachEntry.getComponentValue().split("/");
			if (heightAndWeight!= null && heightAndWeight.length>1) {
			float heightInMeters = Float.parseFloat(heightAndWeight[0])*0.4356F;
			float bmi = Float.parseFloat(heightAndWeight[1])/(heightInMeters*heightInMeters);
			ClinicalData bmiData =new ClinicalData();
			bmiData.setComponentName("bmi");
			bmiData.setComponentValue(Float.toString(bmi));
			
			clinicalData.add(bmiData);
			
		}
}
	}
	
	

}
