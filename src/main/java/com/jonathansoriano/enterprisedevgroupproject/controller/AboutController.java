package com.jonathansoriano.enterprisedevgroupproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for informational pages: About and any future static content
 * pages.
 */
@Controller
public class AboutController {

    /**
     * Renders the About page with project background, developer team, and
     * repository info.
     *
     * @return the about Thymeleaf template name
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
