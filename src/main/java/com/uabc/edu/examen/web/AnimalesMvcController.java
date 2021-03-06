package com.uabc.edu.examen.web;

import java.util.List;
import java.util.Optional;

import com.uabc.edu.examen.exception.RecordNotFoundException;
import com.uabc.edu.examen.model.AnimalesEntity;
import com.uabc.edu.examen.service.AnimalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AnimalesMvcController
{
    @Autowired
    AnimalesService service;

    @RequestMapping("/home")
    public String inicio(Model model){
        model.addAttribute("animal",new AnimalesEntity());
        return "create";
    }

    @RequestMapping("/info")
    public String getAllAnimales(Model model) //Consulta
    {
        List<AnimalesEntity> list = service.getAllAnimales();

        model.addAttribute("ani", list);
        return "consulta";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editAnimalesById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException
    {
        if(id.isPresent())
        {
            AnimalesEntity animales = service.getAnimalesById(id.get());
            model.addAttribute("animales", animales);
        }else{
            model.addAttribute("animales", new AnimalesEntity());
        }
        return "animales";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteAnimalesById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
    {
        service.deleteAnimalesById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createAnimales", method = RequestMethod.POST)
    public String createOrUpdateAnimales(AnimalesEntity animales)
    {
        System.out.println(animales);
        System.out.println(animales.toString());
        service.createOrUpdateAnimales(animales);
        return "redirect:/home";
    }

}
