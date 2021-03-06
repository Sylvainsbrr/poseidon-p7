package com.nnk.springboot.controllers;

import javax.validation.Valid;

import com.nnk.springboot.utils.AuthUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import java.security.Principal;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    @RequestMapping("/curvePoint/list")
    public String home(Model model,@AuthenticationPrincipal Principal principal){
        logger.info("methode home curvePOint");
        String oauth2User = AuthUtils.getOAuth2User(principal);
        if(oauth2User != null){
            model.addAttribute("userName",oauth2User);
        }
        model.addAttribute("curvePoint",curvePointRepository.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        logger.info("methode addBidForm curvePoint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        logger.info("methode validate curvePoint");
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoint", curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("methode sHowUpdateForm curvePoint");
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curve = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
        model.addAttribute("curvePoint", curve);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        logger.info("methode updateBid curvePoint");
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curvePointRepository.save(curvePoint);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("methode deleteBid curvePoint");
        CurvePoint curve = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
        curvePointRepository.delete(curve);
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}