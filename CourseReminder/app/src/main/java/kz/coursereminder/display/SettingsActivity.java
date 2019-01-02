package kz.coursereminder.display;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kz.coursereminder.R;
import kz.coursereminder.adapters.SettingsThemeAdapter;
import kz.coursereminder.controllers.SettingsController;
import kz.coursereminder.structure.BitmapConverter;
import kz.coursereminder.structure.Reminder;

public class SettingsActivity extends ThemedActivity {

    private GridView gridView;
    private SettingsThemeAdapter adapter;
    private SettingsController controller;
    private ImageView background;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        controller = new SettingsController(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ((EditText) findViewById(R.id.setting_user)).setText(getUserName());
        setUpThemeColor();
        addThemeColorListener();
        selectBackgroundListener();
        setUpImageView();
        selectIconListener();
    }

    /**
     * action bar menu stuff
     *
     * @param menu menu
     * @return ?
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        setResult(AppCompatActivity.RESULT_OK, resultIntent);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_settings:
                controller.getEditor().putString("User", userNameListener());
                controller.save();
                startMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Swap to Main Activity
     */
    private void startMainActivity() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    /**
     * Set up the gridview for theme color selection
     */
    private void setUpThemeColor() {
        gridView = findViewById(R.id.setting_theme_color_gridview);
        adapter = new SettingsThemeAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setHighlight(preferences.getInt("Theme", 0));
    }

    /**
     * Activate gridview click listener
     */
    private void addThemeColorListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setHighlight(position);
                adapter.notifyDataSetChanged();
                controller.setThemeColor(position);
            }
        });
    }

    /**
     * Activate username listener
     * @return string of input username
     */
    private String userNameListener() {
        EditText name = findViewById(R.id.setting_user);
        return name.getText().toString();
    }

    /**
     * Activate icon click listener
     */
    private void selectIconListener() {
        TextView iconText = findViewById(R.id.setting_choose_icon);
        iconText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery("Icon");
            }
        });
    }
    /**
     * Activate background textview prompt listener
     */
    private void selectBackgroundListener() {
        TextView backgroundText = findViewById(R.id.setting_choose_background);
        backgroundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery("Background");
            }
        });
    }

    /**
     * Set up background and icon image view in settings
     */
    private void setUpImageView() {
        background = findViewById(R.id.settings_background);
        Bitmap backgroundBitmap= bitmapConverter.decodeBase64(preferences.getString("Background", ""));
        background.setImageBitmap(backgroundBitmap);
        icon = findViewById(R.id.settings_icon);
        Bitmap iconBitmap = bitmapConverter.decodeBase64(preferences.getString("Icon", ""));
        icon.setImageBitmap(iconBitmap);
    }

    /**
     * Hide the keyboard
     *
     * @param view the view to hide from
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Open gallery for image selection
     */
    private void openGallery(String picker) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            if (picker.equals("Background")) {
                // background intent
                startActivityForResult(photoPickerIntent, 1);
            } else {
                // icon intent
                startActivityForResult(photoPickerIntent, 11);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try { // When an Image is picked
                if (requestCode == 1 && data != null) {
                    // background
                    onBackgroundResult(data);
                } else if (requestCode == 11 && data != null) {
                    // icon
                    onIconResult(data);
                } else {
                    Toast.makeText(this, "You didn't pick an image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Oopsie ! Something went wrong",
                        Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You didn't pick an image",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Set icon if launched with icon intent
     * @param data data from onActivityResult
     */
    private void onIconResult(Intent data) {
        String imgDecodableString = getImageDecodableString(data);
        Bitmap icon = bitmapConverter.convertDecodableStringToBitmap(imgDecodableString, "Icon");
        this.icon.setImageBitmap(icon);
        String encodedImage = bitmapConverter.encodeBase64(icon);
        controller.getEditor().putString("Icon", encodedImage);
        Toast.makeText(this, "Icon Selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Set background if launched with background intent
     * @param data data from onActivityResult
     */
    private void onBackgroundResult(Intent data) {
        String imgDecodableString = getImageDecodableString(data);
        Bitmap background = bitmapConverter.convertDecodableStringToBitmap(imgDecodableString, "Background");
        this.background.setImageBitmap(background);
        String encodedImage = bitmapConverter.encodeBase64(background);
        controller.getEditor().putString("Background", encodedImage);
        Toast.makeText(this, "Background Selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Get the decodable string from data
     * @param data data catched from onActivityResult
     * @return string of file location
     */
    private String getImageDecodableString(Intent data) {
        try {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            return imgDecodableString;
        } catch (NullPointerException e) {
            Toast.makeText(this, "Oopsie ! Something went wrong", Toast.LENGTH_LONG).show();
        }
        return "";
    }

}
