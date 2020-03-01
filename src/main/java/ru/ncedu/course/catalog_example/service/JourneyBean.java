package ru.ncedu.course.catalog_example.service;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@SessionScoped
public class JourneyBean implements Serializable {

    private List<String> list = new ArrayList<>();

    public JourneyBean() {
    }

    public void addUrl(String url, String params) {
        String string;
        if (!url.contains("favicon.ico") & !url.contains("style.css")) {
            string = url.substring(21);
            if (params != null) {
                string += "?" + params;
            }
            //Object obj = string;
            this.list.add(string);
        }
    }

    public void clear() {this.list.clear();}

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
