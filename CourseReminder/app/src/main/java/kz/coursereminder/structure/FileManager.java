package kz.coursereminder.structure;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

    private Context context;
    private CourseManager courseManager;

    public FileManager(Context context) {
        this.context = context;
        loadFile(CourseManager.COURSES);
    }

    /**
     * Load file with filename
     * @param fileName name of file to be loaded
     */
    public void loadFile(String fileName) {
        try {
            if (!checkFile(fileName)) {
                createFile();
            }
            InputStream inputStream = context.openFileInput(fileName);
            ObjectInputStream input = new ObjectInputStream(inputStream);
            inputSelect(fileName, input);
            inputStream.close();
        } catch (Exception e) {
            createFile();
        }
    }

    /**
     * Choose the object to get the input to
     * @param fileName filename of object
     * @param input inputstream
     * @throws ClassNotFoundException .
     * @throws IOException .
     */
    private void inputSelect(String fileName, ObjectInputStream input) throws
            ClassNotFoundException, IOException {
        switch (fileName) {
            case CourseManager.COURSES:
                courseManager = (CourseManager) input.readObject();
                break;
        }
    }
    /**
     * Check if file with filename exists
     * @param fileName filename to be checked
     * @return whether it exists
     */
    private boolean checkFile(String fileName) {
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }


    /**
     * Create new coursemanager file
     */
    private void createFile() {
        courseManager = new CourseManager();
        writeFile(CourseManager.COURSES, courseManager);
    }

    /**
     * Write toSave to fileName
     * @param fileName filename of the file
     * @param toSave object to save
     */
    public void writeFile(String fileName, Object toSave) {
        try {
            ObjectOutputStream ouputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, AppCompatActivity.MODE_PRIVATE));
            ouputStream.writeObject(toSave);
            ouputStream.close();
        } catch (IOException e) {
            Toast.makeText(context, "Oops ! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public CourseManager getCourseManager() {
        return courseManager;
    }
}
