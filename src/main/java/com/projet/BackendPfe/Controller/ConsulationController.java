package com.projet.BackendPfe.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.util.StreamUtils;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.projet.BackendPfe.model.AutoDetection;
import com.projet.BackendPfe.model.AvisExpert;
import com.projet.BackendPfe.model.Consultation;
import com.projet.BackendPfe.model.Expert;
import com.projet.BackendPfe.model.Generaliste;
import com.projet.BackendPfe.model.Patient;
import com.projet.BackendPfe.repository.AutoDetectionRepository;
import com.projet.BackendPfe.repository.AvisExpertRepository;
import com.projet.BackendPfe.repository.ConsultationRepository;
import com.projet.BackendPfe.repository.ExpertRepository;
import com.projet.BackendPfe.repository.GeneralisteRepository;
import com.projet.BackendPfe.repository.PatientRepository;
import com.projet.BackendPfe.services.ConsultationService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/consultation")



public class ConsulationController {
	public static String uploadDirectory=System.getProperty("user.dir")+"/upload";

	@Autowired ConsultationRepository repository ;
	@Autowired GeneralisteRepository medecinRepository;
	@Autowired PatientRepository patientRepository;
	@Autowired ExpertRepository expertRepository;
	@Autowired ConsultationService service ; 
	@Autowired AutoDetectionRepository pr ; 
    @Autowired AvisExpertRepository avis; 

	
    @GetMapping("/test")
	public List<Consultation> getttt (){
		List<Consultation> liste_Droite = repository.findByDemandeAvisD(1) ; 
		List<Consultation> liste1 = new ArrayList<Consultation>();// Empty
		for(Consultation consult :liste_Droite) {
			if((consult.getAutoDetection().getAvisExpert()==null)) {
				
				liste1.add(consult)  ; 
			}
			else {
				// dejaa avis mtaa gauche existe 
				if((consult.getAutoDetection().getAvisExpert().getMaladieDroite()== null)
						&&(consult.getAutoDetection().getAvisExpert().getGraviteDroite()==0 )) {
					
					 liste1.add(consult)  ; 
				}
			}
			}
		List<Consultation> liste_Gauche = repository.findByDemandeAvisG(1) ; 
		List<Consultation> liste2 = new ArrayList<Consultation>();
		for(Consultation consu :liste_Gauche) {
			if((consu.getAutoDetection().getAvisExpert()==null)) {
				
				liste2.add(consu)  ; 
			}
			else {
				if((consu.getAutoDetection().getAvisExpert().getMaladieGauche()==null)
						&&(consu.getAutoDetection().getAvisExpert().getGraviteGauche()==0 )) {
					
					 liste2.add(consu)  ; 
				}
			}
			}
		liste1.addAll(liste2) ;
		List<Consultation> resultats =new ArrayList<Consultation>() ; 
		resultats=liste1; 
		return resultats ; 
	}

    
    
    /*@GetMapping("test")
    public List<Consultation> getAllDemandesss (){
    	List<Consultation> liste = repository.findAll();
    	List<Consultation> resultat= new ArrayList<>() ; 
    	for(Consultation consult :liste) {
    		if((consult.getAutoDetection().getAvisExpert()==null)) {
    			if((consult.getDemandeAvisD()==1 && consult.getDemandeAvisG()==0)) {
    			     resultat.add(consult);}
    			if((consult.getDemandeAvisD()==0 && consult.getDemandeAvisG()==1)) {
    			     resultat.add(consult);}
    		  
    			if((consult.getDemandeAvisD()==1 && consult.getDemandeAvisG()==1)) {
    				if(! resultat.contains(consult)) {
    					 resultat.add(consult);
    			    }
    				}
    		
    	}
    	
    	}
    	return resultat ; 
    }/*/
    @GetMapping("/demandes")
    public List<Consultation> getAllDemandes(){
    	    	List<Consultation> liste = repository.findAll();
    	    	List<Consultation> resultat= new ArrayList<>() ; 
    	    	for(Consultation consult :liste) {
    	    		if((consult.getDemandeAvisD()==1 || consult.getDemandeAvisG()==1)){
    	    			if((consult.getAutoDetection().getAvisExpert()==null)) {
    	    				resultat.add(consult);
    	    				
    	    			
    	    			}
    	    		}
    	    	}
    	    	return resultat ; 
    }
    @GetMapping("/Consultations") // hdhy st79itha f expert avis => besh yjiw kol ama f html mtaa angular besh naamlu condiition ala id AvisExpert f autotDetection eli huwa meloul besh ykoun null khater ma7tinch avis 
 	public List<Consultation> getAllConsultation(){
         //pr.findById(id);
    	
	    return  repository.findAll();

	} 
    
	@PostMapping("/Consultations/{idGeneraliste}/{idPatient}")
	public Consultation addConsultation(@PathVariable("idGeneraliste") long idGeneraliste , 
			                                                                 @PathVariable("idPatient") long idPatient   ){
		Generaliste  generaliste = medecinRepository.findById(idGeneraliste).get(); 
		Patient patient = patientRepository.findById(idPatient).get() ; 
		byte[] image1 = null ; 
		byte[] image2 = null ; 
	
		
		

		Consultation consultation = new Consultation(LocalDate.now(),generaliste, patient,image1,image2);
		repository.save(consultation) ;
		return consultation ; 

	}
	
	

	
	
	
	
	//deleteAll Pictures 
	@PutMapping("consultation/picturesD/{id}/{idConsultation}")
	public void deleteConsult(@PathVariable("id") long id, @PathVariable("idConsultation") long idConsultation){
		 Consultation consult =repository.findById(idConsultation).get();
		 consult.setImage1_Droite(null);
		
		 repository.save(consult);

	}
	@PutMapping("consultation/picturesG/{id}/{idConsultation}")
	public void deleteConsultG(@PathVariable("id") long id, @PathVariable("idConsultation") long idConsultation){
		 Consultation consult =repository.findById(idConsultation).get();
		 consult.setImage1_Gauche(null);
		
		 repository.save(consult);

	}

	// put for expert baed f avis demander 
	
	/*@PutMapping("SendConsultation/{idConsultation}/{idExpert}")
	public Consultation EnvoyerConultationAunExpert(@PathVariable("idConsultation") long idConsultation , 
			                                                                                               @PathVariable("idExpert") long idExpert) {
		Consultation consultation = repository.findById(idConsultation).get() ;
		Expert expert = expertRepository.findById(idExpert).get() ;
	   consultation.setExpert(expert);
	   repository.save(consultation) ;
	   return consultation ; 
	}/*
	/***********Oeil Droite *************/
	
	
	
	
	@PutMapping("/addimage1D/{idConsultation}")
	public String updateImage1D(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image1") MultipartFile image1) throws IOException {
		service.updateImage1Droite(idConsultation , image1);
		return "Done pour image1 Droite !!!!" ; 
		
	}
	

/*	@PutMapping("/addimage2D/{idConsultation}")
	public String updateImage2D(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image2") MultipartFile image2 ) throws IOException {
			updateImage2Droite(idConsultation,  image2);
		return "Done pour image2  Droite!!!!" ; 
	}
	public void updateImage2Droite(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage2_Droite(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	@PutMapping("/addimage3D/{idConsultation}")
	public String updateImage3D(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image3") MultipartFile image3) throws IOException {
	updateImage3Droite(idConsultation , image3);
		return "Done pour image3 Droite !!!!" ; 
	}
	public void updateImage3Droite(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage3_Droite(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	@PutMapping("/addimage4D/{idConsultation}")
	public String updateImage4D(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image4") MultipartFile image4 ) throws IOException {
		updateImage4Droite(idConsultation , image4);
		return "Done pour image4  Droite!!!!" ; 
	}
	public void updateImage4Droite(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage4_Droite(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	@PutMapping("/addimage5D/{idConsultation}")
	public String updateImage5D(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image5") MultipartFile image5        ) throws IOException {

		updateImage5Droite(idConsultation, image5);
		return "Done pour image5 Droite  !!!!" ; 
	}
	
	public void updateImage5Droite(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage5_Droite(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	/***********Oeil Gauche *************/
	
	@PutMapping("/addimage1G/{idConsultation}")
	public String updateImage1G(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image1") MultipartFile image1  ) throws IOException {
	service.updateImage1Gauche(idConsultation , image1);
		return "Done pour image1 Gauche !!!!" ; 
	}
	
	
	
	/*@PutMapping("/addimage2G/{idConsultation}")
	public String updateImage2G(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image2") MultipartFile image2 ) throws IOException {
		updateImage2Gauche(idConsultation, image2);
		return "Done pour image2  Gauche!!!!" ; 
	}
	
	public void updateImage2Gauche(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage2_Gauche(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	
	@PutMapping("/addimage3G/{idConsultation}")
	public String updateImage3G(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image3") MultipartFile image3 ) throws IOException {
		updateImage3Gauche(idConsultation, image3);
		return "Done pour image3 Gauche !!!!" ; 
	}
	
	public void updateImage3Gauche(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage3_Gauche(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	/*
	
	@PutMapping("/addimage4G/{idConsultation}")
	public String updateImage4G(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image4") MultipartFile image4 ) throws IOException {
	updateImage4Gauche(idConsultation,  image4);
		return "Done pour image4  Gauche!!!!" ; 
	}
	
	public void updateImage4Gauche(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage4_Gauche(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	
	
	@PutMapping("/addimage5G/{idConsultation}")
	public String updateImage5G(@PathVariable("idConsultation") long idConsultation  , @RequestParam("image5") MultipartFile image5        ) throws IOException {
		updateImage5Gauche(idConsultation , image5);
		return "Done pour image5 Gauche  !!!!" ; 
	}
	public void updateImage5Gauche(long id , MultipartFile file) throws IOException {
		
		 Consultation  consultation = repository.findById(id).get();
		 StringBuilder fileNames=new StringBuilder();
		 Path fileNameAndPath=Paths.get(uploadDirectory,file.getOriginalFilename()+"");
		 fileNames.append(file);
		 System.out.println("bagraa"+fileNameAndPath);
		 Files.write(fileNameAndPath, file.getBytes());
		 consultation.setImage5_Gauche(fileNameAndPath.toString());
		 
		repository.save(consultation);
	}
	
	*/
	@DeleteMapping("/deleteConsult/{id}/{idConsultation}")
	public void deleteProduct(@PathVariable("idConsultation") long idConsultation){
		
	
		repository.deleteById(idConsultation);
	} 
	@GetMapping("/Consultations/{id}")
	public List<Consultation> getAllProducts(@PathVariable("id") @ModelAttribute("id") long id){
         //pr.findById(id);
	    return  repository.findByGeneraliste_id(id);

	} 

	@GetMapping("/Consultation/{id}/{idPatient}") // hehdy pour lien de consultation pour chaque patient
	public List<Consultation> getConsultationsByPatient (@PathVariable("id") long id ,@PathVariable ("idPatient") long idPatient){
	
    return repository.findByPatient_idAndGeneraliste_id(idPatient,id);		 
		
	}
	/*@GetMapping("/Consultations/{id}/{idConsultation}/{idPatient}")
	public ResponseEntity<byte[]> getImage() throws IOException {
		Consultation conster = repository.findById((long) 1).get();

		ClassPathResource imageFile = new ClassPathResource(conster.getImage1_Droite());

		byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }*/
	
	@GetMapping("/Consultation/{id}/{idConsultation}/{idPatient}")
	public Consultation getAllProductsbyid(@PathVariable("id") long id,@PathVariable("idConsultation") long idConsultation,@PathVariable("idPatient") long idPatient){
		Consultation conster = repository.findById(idConsultation).get();
		 if( conster.getImage1_Droite()== null) {
			  return conster;
		  }
		  else {
			    conster.setImage1_Droite(service.decompressZLib(conster.getImage1_Droite()));}	
		 if( conster.getImage1_Gauche()== null) {
			  return conster;
		  }
		  else {
			    conster.setImage1_Gauche(service.decompressZLib(conster.getImage1_Gauche()));
			    return conster;
				  
				
			}}
			    
	// input id de Auto detection dans consultation
	
	
	@PutMapping("/editAuto/{idGeneraliste}/{idConsult}/{idAutoDetection}")//(2) scénarion besh ysir directement maa ajout autoDetectio(cad k nenzlu al AI model 2 fonction bsh ysiru whda huni whda post mtaa autodetection)  
	public Consultation updateID(@PathVariable("idGeneraliste") long idGeneraliste,@PathVariable("idConsult") long idConsult,  @PathVariable("idAutoDetection") long idAutoDetection) {
	Consultation consultation = repository.findById(idConsult).get();
		AutoDetection autp =pr.findById(idAutoDetection).get();
           consultation.setAutoDetection(autp);
	return	repository.save(consultation);
		 
	}
	
	/*@PutMapping("/demanderAvis/{idGeneraliste}/{idConsult}")
	public String udemanderAvisID(@PathVariable("idGeneraliste") long idGeneraliste,@PathVariable("idConsult") long idConsult) {
	Consultation consultation = repository.findById(idConsult).get();
		repository.save(consultation);
		return "Done pour changement ID  !!!!" ; 
	}*/
	// demande avis virtuelle 
	@PutMapping("/demanderAvisG/{idGeneraliste}/{idConsult}")
	public Consultation demanderAvisID(@PathVariable("idGeneraliste") long idGeneraliste,@PathVariable("idConsult") long idConsult) {
	Consultation consultation = repository.findById(idConsult).get();
           consultation.setDemandeAvisG(1);
return		repository.save(consultation);
	}
	@PutMapping("/demanderAvisD/{idGeneraliste}/{idConsult}")
	public Consultation demanderAvisId(@PathVariable("idGeneraliste") long idGeneraliste,@PathVariable("idConsult") long idConsult) {
	Consultation consultation = repository.findById(idConsult).get();
           consultation.setDemandeAvisD(1);
		return repository.save(consultation);
}
	

}
	
