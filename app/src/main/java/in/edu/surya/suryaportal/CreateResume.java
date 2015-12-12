package in.edu.surya.suryaportal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

public class CreateResume extends AppCompatActivity {

    static String data = "";
    static String dataFileName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeResume();
            }
        });
    }
    void makeResume()
    {
        data = "";
        String name;
        data += "Name : "+(name=((TextView)findViewById(R.id.et_name)).getText().toString())+"\n";
        data += "\nDetails\n "+((TextView)findViewById(R.id.et_details))+"\n";

        data = Base64.encodeToString(data.getBytes(),Base64.DEFAULT);
        dataFileName = "Resume_"+name+".txt";
        finish();
    }

}
