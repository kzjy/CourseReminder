package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;


import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

abstract class CourseControllers {

    Context context;
    FileManager fileManager;
    CourseManager courseManager;

    CourseControllers (Context context) {
        this.context = context;
        this.fileManager = new FileManager(context);
        this.courseManager = fileManager.getCourseManager();
    }

    void makeToastInvalidName() {
        Toast.makeText(context, "Course name cannot be empty", Toast.LENGTH_SHORT).show();
    }

    void makeToastNameInUse() {
        Toast.makeText(context, "Course name already in use", Toast.LENGTH_SHORT).show();
    }


}
