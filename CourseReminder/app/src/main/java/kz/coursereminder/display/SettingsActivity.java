package kz.coursereminder.display;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;

import kz.coursereminder.R;
import kz.coursereminder.adapters.SettingsThemeAdapter;

public class SettingsActivity extends AppCompatActivity {

    private GridView gridView;
    private SettingsThemeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setUpThemeColor();
        addThemeColorListener();
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpThemeColor() {
        gridView = findViewById(R.id.setting_theme_color_gridview);
        adapter = new SettingsThemeAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void addThemeColorListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setHighlight(position);
                adapter.notifyDataSetChanged();
            }
        });
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
}
