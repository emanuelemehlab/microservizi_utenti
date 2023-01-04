package com.example.microservizi_prenotazione.Controller;

import com.example.microservizi_prenotazione.Entities.prenotazione;
import com.example.microservizi_prenotazione.Entities.prenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1")
public class controllerricerca {

    @Autowired
    private prenotazioneRepository Prerepository;

     @GetMapping("/getRicPren")
     public List<prenotazione> getRicPren() {
         return Prerepository.findAll();
     }

     @GetMapping("/getTassistiDisp/{giorno}/{orario}/{posti}/{bag}/{seg}")
     public List<Object> getTassistiDisp(@PathVariable("giorno") String giorno, @PathVariable("orario") String orario, @PathVariable("posti") Integer posti, @PathVariable("bag") String bag, @PathVariable("seg") String seg){
         String uri = "http://localhost:8080/api/v1/getDisponibilita/"+giorno+"/"+orario;
         RestTemplate rt = new RestTemplate();
         List<ArrayList> tasDisp = rt.getForObject(uri,List.class);

         List<Object> tasDispSeri = new ArrayList<Object>();

         for(ArrayList td: tasDisp){
             if((Integer) td.get(6) >= posti && (td.get(7).toString().equals(bag) || td.get(7).toString().equals("true")) && (td.get(8).toString().equals(seg) || td.get(8).toString().equals("true"))){
                    tasDispSeri.add(td);
             }
         }

         return tasDispSeri;

     }

     @PutMapping("/addPrenotazione")
    public String addPrenotazione(@RequestBody prenotazione pre){
         try{
             Prerepository.save(pre);
             ControllerEmail ce = new ControllerEmail();
             ce.sendSimpleMessage(pre.getTassista(),"Prenotazione aggiunta","Hai una nuova prenotazione.");
             return "Prenotazione inserita.";
         }catch(Exception e){
             return e.getMessage();
         }
     }

    @DeleteMapping("/deletePrenotazione/{id}")
    public String deletePrenotazione(@PathVariable("id") Long id) {
        try {
            Prerepository.deleteById(id);
            return "Cancellazione disponibilità con id:" + id+" effettuata.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @GetMapping("/getPrenotazioniTassista/{email}")
    public ResponseEntity<List<prenotazione>> getPrenotazioniTassista(@PathVariable("email") String email){
        List<prenotazione> listaPre = new ArrayList<>();
        try{

            Iterator<prenotazione> it =  Prerepository.findAll().iterator();
            while(it.hasNext()){
                prenotazione pren = it.next();
                if(pren.getTassista().equals(email)){
                    listaPre.add(pren);
                }
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(listaPre,HttpStatus.OK);
    }


    @GetMapping("/getPrenotazioniTassista/{email}/{stato}")
    public ResponseEntity<List<prenotazione>> getPrenotazioniTassistaTipo(@PathVariable("email") String email,@PathVariable("stato") String stato){
        List<prenotazione> listaPre = new ArrayList<>();
        try{

            Iterator<prenotazione> it =  Prerepository.findAll().iterator();
            while(it.hasNext()){
                prenotazione pren = it.next();
                if(pren.getTassista().equals(email) && pren.getStato().equals(stato)){
                    listaPre.add(pren);
                }
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(listaPre,HttpStatus.OK);
    }

    @GetMapping("/getPrenotazioniCliente/{email}")
    public ResponseEntity<List<prenotazione>> getPrenotazioniCliente(@PathVariable("email") String email){
        List<prenotazione> listaPre = new ArrayList<>();
        try{

            Iterator<prenotazione> it =  Prerepository.findAll().iterator();
            while(it.hasNext()){
                prenotazione pren = it.next();
                if(pren.getCliente().equals(email)){
                    listaPre.add(pren);
                }
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(listaPre,HttpStatus.OK);
    }


    @GetMapping("/getPrenotazioniCliente/{email}/{stato}")
    public ResponseEntity<List<prenotazione>> getPrenotazioniClienteTipo(@PathVariable("email") String email,@PathVariable("stato") String stato){
        List<prenotazione> listaPre = new ArrayList<>();
        try{

            Iterator<prenotazione> it =  Prerepository.findAll().iterator();
            while(it.hasNext()){
                prenotazione pren = it.next();
                if(pren.getCliente().equals(email) && pren.getStato().equals(stato)){
                    listaPre.add(pren);
                }
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(listaPre,HttpStatus.OK);
    }

    @PostMapping("/sceltaPrenotazione")
    public String sceltaPrenotazione(@RequestBody prenotazione pre){

        try{
            pre.setStato("attesa_conferma");
            Prerepository.save(pre);
            ControllerEmail ce = new ControllerEmail();
            ce.sendSimpleMessage(pre.getCliente(),"Prenotazione in attesa di conferma","Hai una nuova prenotazione da confermare.");
            return "Prenotazione in attesa di conferma.";
        }catch(Exception e){
            return e.getMessage();
        }
    }


    @PostMapping("/rifiutaPrenotazione")
    public String rifiutaPrenotazione(@RequestBody prenotazione pre){

        try{
            pre.setStato("annullata");
            Prerepository.save(pre);
            ControllerEmail ce = new ControllerEmail();
            ce.sendSimpleMessage(pre.getTassista(),"Prenotazione annullata","La prenotazione "+pre.getId()+" del cliente: "+pre.getCliente()+" è stata annullata.");
            ce.sendSimpleMessage(pre.getCliente(),"Prenotazione annullata","La prenotazione "+pre.getId()+" col tassista: "+pre.getTassista()+" è stata annullata.");
            return "Prenotazione annullata con successo.";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/confermaPrenotazione")
    public String confermaPrenotazione(@RequestBody prenotazione pre){

        try{

            byte[] array = new byte[8]; // length is bounded by 7
            new Random().nextBytes(array);
            String codice = new String(array, Charset.forName("UTF-8"));
            pre.setCodice(codice);
            pre.setStato("confermata");
            Prerepository.save(pre);
            ControllerEmail ce = new ControllerEmail();
            ce.sendSimpleMessage(pre.getTassista(),"Prenotazione confermata","La prenotazione "+pre.getId()+" del cliente: "+pre.getCliente()+" è stata confermata.");
            ce.sendSimpleMessage(pre.getCliente(),"Prenotazione confermata","La prenotazione "+pre.getId()+" col tassista: "+pre.getTassista()+" è stata confermata.");
            return "Prenotazione confermata.";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/controlloCodice")
    public String controlloCodice(@RequestBody prenotazione pre){
         try{
             if(Prerepository.findAll().contains(pre)){
                 pre.setStato("conclusa");
                 Prerepository.save(pre);
                 return "Codice confermato.";
             }else{
                 return "Codice errato.";
             }
         }catch(Exception e){
             return e.getMessage();
         }
    }

}
