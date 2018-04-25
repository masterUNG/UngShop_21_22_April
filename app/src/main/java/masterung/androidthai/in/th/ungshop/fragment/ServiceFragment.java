package masterung.androidthai.in.th.ungshop.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import masterung.androidthai.in.th.ungshop.R;
import masterung.androidthai.in.th.ungshop.utility.DeleteFoodOnServer;
import masterung.androidthai.in.th.ungshop.utility.EditPriceOnServer;
import masterung.androidthai.in.th.ungshop.utility.FoodAdapter;
import masterung.androidthai.in.th.ungshop.utility.GetUserFromServer;
import masterung.androidthai.in.th.ungshop.utility.MyConstance;

public class ServiceFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create ListView
        createListView();


    }   // onActivityCreate

    private void createListView() {

        ListView listView = getView().findViewById(R.id.listViewFood);
        MyConstance myConstance = new MyConstance();
        String[] columnStrings = myConstance.getColumnFoodStrings();

        try {

            GetUserFromServer getUserFromServer = new GetUserFromServer(getActivity());
            getUserFromServer.execute(myConstance.getUrlGetFoodString());

            String jsonString = getUserFromServer.get();
            Log.d("22AprilV1", "JSON food ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            String[] imageStrings = new String[jsonArray.length()];
            String[] nameFoodStrings = new String[jsonArray.length()];
            final String[] priceStrings = new String[jsonArray.length()];
            String[] descriptionStrings = new String[jsonArray.length()];
            final String[] idStrings = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                imageStrings[i] = jsonObject.getString(columnStrings[5]);
                nameFoodStrings[i] = jsonObject.getString(columnStrings[2]);
                priceStrings[i] = jsonObject.getString(columnStrings[3]);
                descriptionStrings[i] = jsonObject.getString(columnStrings[4]);
                idStrings[i] = jsonObject.getString(columnStrings[0]);

            }

            FoodAdapter foodAdapter = new FoodAdapter(getActivity(), imageStrings,
                    nameFoodStrings, priceStrings, descriptionStrings);
            listView.setAdapter(foodAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    comfirmDaleteOrEdit(idStrings[position], priceStrings[position]);
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void comfirmDaleteOrEdit(final String idString, final String priceString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setTitle("Delete or Edit");
        builder.setMessage("Please Choose Delete or Edit ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFood(idString);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editFood(idString, priceString);
                dialog.dismiss();
            }
        });
        builder.show();


    }

    private void editFood(final String idString, String priceString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Please Setup New Price");
        builder.setMessage("Old Price ==> " + priceString + " THB.");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.alert_edit_price, null);
        builder.setView(view);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = view.findViewById(R.id.edtPrice);
                String newPriceString = editText.getText().toString();
                if (newPriceString.isEmpty()) {
                    newPriceString = "0";
                }
                updateNewPrice(idString, newPriceString);

                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void updateNewPrice(String idString, String newPriceString) {

        MyConstance myConstance = new MyConstance();
        try {

            EditPriceOnServer editPriceOnServer = new EditPriceOnServer(getActivity());
            editPriceOnServer.execute(idString, newPriceString,
                    myConstance.getUrlEditPriceString());

            if (Boolean.parseBoolean(editPriceOnServer.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(), "Cannot Edit Price",
                        Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void deleteFood(String idString) {

        MyConstance myConstance = new MyConstance();
        try {

            DeleteFoodOnServer deleteFoodOnServer = new DeleteFoodOnServer(getActivity());
            deleteFoodOnServer.execute(idString, myConstance.getUrlDeleteFoodString());

            if (Boolean.parseBoolean(deleteFoodOnServer.get())) {
                createListView();
            } else {
                Toast.makeText(getActivity(), "Cannot Delete", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }
}
