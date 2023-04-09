package ma.enset.spring_patient.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.spring_patient.entities.Patient;
import ma.enset.spring_patient.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller @AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("index")
    public String patients(Model model, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size,@RequestParam(name = "keyword" ,defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("pageCourrent",page);
        model.addAttribute("keyword",keyword);
       return "patients";
    }
    @GetMapping("/admin/delete")
    public  String delete(Long id,String keyword,int page){
        System.out.println("id is :"+id);
        patientRepository.deleteById(id);
        System.out.println("fintest");
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/user/patients")
    @ResponseBody
   public List<Patient> Listpatients(){
        return patientRepository.findAll();
   }
   @GetMapping("/admin/formPatients")
   public String FormPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
   }
   @PostMapping(path = "/admin/save")
   public String save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "") String keyword){
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
   }
   @GetMapping("/admin/editPatient")
   public String editPatient(Model model,Long id,String keyword, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Patient introunable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editPatients";
   }

}
