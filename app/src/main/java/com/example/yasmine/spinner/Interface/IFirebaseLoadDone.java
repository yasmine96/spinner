package com.example.yasmine.spinner.Interface;

import com.example.yasmine.spinner.Model.School;

import java.util.List;

public interface IFirebaseLoadDone  {
    void onFirebaseLoadSucess(List<School> schoolList);
    void onFirebaseLoadFailed(String Message);
}
