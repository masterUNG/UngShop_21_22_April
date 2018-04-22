package masterung.androidthai.in.th.ungshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import masterung.androidthai.in.th.ungshop.MainActivity;
import masterung.androidthai.in.th.ungshop.R;
import masterung.androidthai.in.th.ungshop.utility.AddUserToServer;
import masterung.androidthai.in.th.ungshop.utility.MyAlert;
import masterung.androidthai.in.th.ungshop.utility.MyConstance;

public class RegisterFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


    }   // onActivityCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {

            uploadValueToServer();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void uploadValueToServer() {

//        Get Value From EditText
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText userEditText = getView().findViewById(R.id.edtUser);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();

//        Check Space
        if (nameString.isEmpty() || userString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Have Space",
                    "Please Fill All Blank");
        } else {
//            No Space
            try {

                MyConstance myConstance = new MyConstance();
                AddUserToServer addUserToServer = new AddUserToServer(getActivity());
                addUserToServer.execute(nameString, userString, passwordString,
                        myConstance.getUrlAddUserString());

                String result = addUserToServer.get();
                Log.d("21AprilV1", "Result ==> " + result);

                if (Boolean.parseBoolean(result)) {

                    getActivity().getSupportFragmentManager().popBackStack();

                } else {

                    Toast.makeText(getActivity(), "Cannot Upload Value",
                            Toast.LENGTH_SHORT).show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_register, menu);
    }

    private void createToolbar() {

        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

//        Setup Title
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Register");
        ((MainActivity) getActivity()).getSupportActionBar()
                .setSubtitle("Please Fill All Every Blank");

//        Setup Navigation
        ((MainActivity) getActivity()).getSupportActionBar()
                .setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
