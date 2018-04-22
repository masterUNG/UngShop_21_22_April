package masterung.androidthai.in.th.ungshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import masterung.androidthai.in.th.ungshop.R;
import masterung.androidthai.in.th.ungshop.ServiceActivity;
import masterung.androidthai.in.th.ungshop.utility.GetUserFromServer;
import masterung.androidthai.in.th.ungshop.utility.MyAlert;
import masterung.androidthai.in.th.ungshop.utility.MyConstance;

public class MainFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();


    }   // onActivityCreate

    private void loginController() {

        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Get Value From EditText
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space",
                            "Plese Fill All Blank");

                } else {

//                    No Space
                    try {

                        GetUserFromServer getUserFromServer = new GetUserFromServer(getActivity());
                        MyConstance myConstance = new MyConstance();

                        getUserFromServer.execute(myConstance.getUrlGetUserString());
                        String jsonString = getUserFromServer.get();
                        Log.d("21AprilV1", "JSON ==> " + jsonString);

                        boolean b = true;
                        String truePassword = null;
                        String trueName = null;
                        MyAlert myAlert = new MyAlert(getActivity());
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i+=1) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (userString.equals(jsonObject.getString("User"))) {

                                b = false;
                                truePassword = jsonObject.getString("Password");
                                trueName = jsonObject.getString("Name");

                            }
                        }

                        if (b) {
//                            User False
                            myAlert.normalDialog("User False",
                                    "No This User in my Database");
                        } else if (passwordString.equals(truePassword)) {
//                            Password True
                            Toast.makeText(getActivity(), "Welcome " + trueName,
                                    Toast.LENGTH_SHORT).show();

//                            Intent to ServiceActivity
                            Intent intent = new Intent(getActivity(), ServiceActivity.class);
                            intent.putExtra("NameLogin", trueName);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
//                            Password False
                            myAlert.normalDialog("Password False",
                                    "Please Try Again Password False");
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }




            }   // onClick
        });

    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}   // Main Class
