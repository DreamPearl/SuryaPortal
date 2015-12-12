package in.edu.surya.suryaportal;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Fragment newInstance(int sectionNumber) {
        Fragment fragment ;
        if(sectionNumber==0)
            fragment = new PlaceholderFragment();
        else
            fragment = new RegistrationFragment();
        return fragment;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        RadioButton rb_student,rb_admin;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rb_student = (RadioButton) rootView.findViewById(R.id.rb_studentPanel);
            rb_admin = (RadioButton) rootView.findViewById(R.id.rb_adminPanel);

            rb_student.setOnClickListener(this);
            rb_admin.setOnClickListener(this);

            rootView.findViewById(R.id.b_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rb_student.isChecked())
                        openValidation(0);
                    else
                        openValidation(1);
                }
            });
            return rootView;
        }

        void openValidation(int choice)
        {
            Intent intent = new Intent(getContext(),Validation.class);
            intent.putExtra("choice",choice);
            startActivity(intent);
        }

        @Override
        public void onClick(View view) {
            if(view.getTag().toString().equals("student"))
            {
                rb_admin.setChecked(false);
            }
            else
            {
                rb_student.setChecked(false);
            }
        }
    }
    public static class RegistrationFragment extends Fragment implements View.OnClickListener {

        EditText et_username,et_password,et_email,et_mobile,et_location,et_qualification,et_course;
        Button b_submit,b_createResume,b_pickResume;
        String resumeData="",resumeFile="";
        View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.frag_register, container, false);
            et_username = (EditText) rootView.findViewById(R.id.et_username);
            et_password = (EditText) rootView.findViewById(R.id.et_password);
            et_email = (EditText) rootView.findViewById(R.id.et_emailid);
            et_mobile = (EditText) rootView.findViewById(R.id.et_mobile);
            et_location = (EditText) rootView.findViewById(R.id.et_location);
            et_qualification = (EditText) rootView.findViewById(R.id.et_qualification);
            et_course = (EditText) rootView.findViewById(R.id.et_course);

            b_submit = (Button) rootView.findViewById(R.id.b_submit);
            b_createResume = (Button) rootView.findViewById(R.id.b_createResume);
            b_pickResume = (Button) rootView.findViewById(R.id.b_pickResume);

            b_submit.setOnClickListener(this);
            b_createResume.setOnClickListener(this);
            b_pickResume.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id)
            {
                case R.id.b_submit:
                    if(validate())
                    submit();
                    break;
                case R.id.b_createResume:
                    createResume();
                    break;
                case R.id.b_pickResume:
                    pickResume();
                    break;

            }
        }
        void submit()
        {
            Toast.makeText(getContext(),"All Clear",Toast.LENGTH_SHORT).show();
        }
        void pickResume()
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            Intent chooser = Intent.createChooser(intent,"Pick Resume");
            startActivityForResult(chooser, 0);
        }
        boolean validate()
        {
            et_username.setError(null);
            et_password.setError(null);
            et_email.setError(null);
            et_mobile.setError(null);
            et_course.setError(null);
            View focus = null;
            if(et_username.getText().toString().length()<4)
            {
                et_username.setError("Username Too Short");
                focus = et_username;
            }else
            if(et_password.getText().toString().length()<6)
            {
                et_password.setError("Password Too Short");
                focus = et_password;
            } else
            if(!et_email.getText().toString().contains("@"))
            {
                et_email.setError("Invalid Email");
                focus = et_email;
            } else
            if(et_mobile.getText().toString().length()<10)
            {
                et_mobile.setError("Atleast 10 Digit Phone Number");
                focus = et_mobile;
            }
            else
            if(et_course.getText().toString().isEmpty())
            {
                et_course.setError("Required");
                focus = et_course;
            }
            if(focus==null)
            {
                return true;
            }
            else{
                focus.requestFocus();
                return false;
            }




        }
        boolean flag_CreateResume = false;
        void createResume()
        {
            Intent in = new Intent(getActivity(),CreateResume.class);
            startActivity(in);
            flag_CreateResume = true;
        }

        @Override
        public void onResume() {
            super.onResume();
            if(flag_CreateResume)
            {
                resumeData = CreateResume.data;
                resumeFile = CreateResume.dataFileName;
                b_pickResume.setText("Pick : "+resumeFile);
                CreateResume.dataFileName = "";
                CreateResume.data ="";
                flag_CreateResume=false;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 0 )    //Pick Resume
            {
            if(resultCode!=RESULT_OK)
            {
                resumeData ="";
                resumeFile = "";
                b_pickResume.setText("Pick");
                return;
            }

                File file = new File(data.getDataString());
                String b64 = "";
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(getContext().getContentResolver().openInputStream(data.getData())));
                    byte d[] = new byte[5*1024*1024];
                    int x,l=0;
                    for(;l<5*1024*1024 && (x=br.read())>=0;l++)
                        d[l]=(byte)x;
                    b64 = Base64.encodeToString(d, 0, l, Base64.DEFAULT);
                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }

                if(!b64.isEmpty())
                {
                    resumeData = b64;
                    resumeFile = new File(data.getDataString()).getName();
                    b_pickResume.setText("Pick : "+resumeFile);
                }
                else
                {
                    resumeData ="";
                    resumeFile = "";
                    b_pickResume.setText("Pick");
                }
              }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Registration";

            }
            return null;
        }
    }
}
