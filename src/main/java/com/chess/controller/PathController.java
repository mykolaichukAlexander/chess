package com.chess.controller;

import com.chess.service.dto.Path;
import com.chess.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@Slf4j
public class PathController {

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("msg", "Please input start and end points");
        model.addAttribute("pathRequest", new PathRequest());
        return "index";
    }

    @RequestMapping(value = { "/calculatePath" }, method = RequestMethod.GET)
    public String calculatePath(Model model){
        model.addAttribute("msg", "Please input start and end points");
        model.addAttribute("pathRequest", new PathRequest());
        return "index";
    }

    @RequestMapping(value = "/calculatePath", method = RequestMethod.POST)
    public String calculatePath(Model model, @ModelAttribute("pathRequest") PathRequest pathRequest){
        try {
            String start = pathRequest.getStart();
            String end = pathRequest.getEnd();
            boolean withUniq = pathRequest.isWithUniq();

            List<Path> paths = pathService.findPaths(start, end, withUniq);
            if (CollectionUtils.isEmpty(paths)){
                model.addAttribute("msg", "Can`t find path");
                return "index";
            }
            model.addAttribute("msg", "Please input start and end points");
            model.addAttribute("paths", paths);
            return "index";
        } catch (Exception e){
            model.addAttribute("msg", e.getMessage());
            return "index";
        }

    }


}
