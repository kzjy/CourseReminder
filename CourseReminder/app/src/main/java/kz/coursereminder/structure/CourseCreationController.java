package kz.coursereminder.structure;

import android.content.Context;
import android.widget.Toast;

public class CourseCreationController {

    private Context context;
    private FileManager fileManager;
    private CourseManager courseManager;

    public CourseCreationController(Context context) {
        this.context = context;
        fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }

    public boolean addCourse(String name, String info, int image) {
        Course newCourse = new Course(name, info, image);
        boolean valid = courseManager.checkValidCourseName(newCourse);
        boolean exists = courseManager.checkExistingCourse(newCourse);
        if (!valid) {
            makeToastInvalidName();
            return false;
        } else if (exists) {
            makeToastNameInUse();
            return false;
        }
        courseManager.addCourse(newCourse);
        fileManager.writeFile(CourseManager.COURSES, courseManager);
        return true;
    }

    private void makeToastInvalidName() {
        Toast.makeText(context, "Course name cannot be empty", Toast.LENGTH_SHORT).show();
    }

    private void makeToastNameInUse() {
        Toast.makeText(context, "Course name already in use", Toast.LENGTH_SHORT).show();
    }

}
