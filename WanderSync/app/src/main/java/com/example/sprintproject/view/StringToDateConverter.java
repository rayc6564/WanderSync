package com.example.sprintproject.view;

import java.text.ParseException;
import java.util.Date;

public interface StringToDateConverter {
    public Date convertStringToDate(String str) throws ParseException;
}
