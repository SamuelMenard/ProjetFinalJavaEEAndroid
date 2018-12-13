package cgodin.qc.ca.ecolekarate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentLogin extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private List<String> lstCourrielsComptes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Spinner spinnerCompte;
    private Button btnConnexion;
    public boolean connecte;
    private View myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_login, container, false);
        btnConnexion = (Button) myFragment.findViewById(R.id.btnConnexion);
        return myFragment;
    }

    @Override
    public void onClick(View view) {
        if (view == btnConnexion){
            spinnerCompte = (Spinner) myFragment.findViewById(R.id.spinner);
            if(spinnerCompte.isEnabled()){

                connecte = true;
                btnConnexion.setText("Fermer la connexion");
                spinnerCompte.setEnabled(false);
                   /* String url = cheminServeur + "/compte/infos/base/" + courriel.toString().trim();
                    new GetCompteConnexion().execute(url);
                    //startWebSocket();
                    stomp.brancherStomp();
                    stompPrive.brancherStompPrive();*/
            }
            else{

                connecte = false;
                btnConnexion.setText("Établir une connexion");
                spinnerCompte.setEnabled(true);
                //stopWebSocket();
                   /* stomp.terminerStomp();
                    stompPrive.terminerStompPrive();
                    viderInformations();
                    new Logout().execute("");*/
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class GetAllUsername extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlToRead = params[0];
            StringBuilder result = new StringBuilder();

            try{
                URL url = new URL(urlToRead);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");


                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            }
            catch (ProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if (s == null){

            }
            else{
                try {
                    JSONArray lstCourriels = new JSONArray(s.toString());
                    for (int i = 0; i < lstCourriels.length(); i++){
                        lstCourrielsComptes.add(lstCourriels.getString(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, lstCourrielsComptes);

            final Spinner spinner = (Spinner) myFragment.findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(FragmentLogin.this);
            // Drop down layout style - list view with radio button
            adapter.setDropDownViewResource(R.layout.spinner_item);
            // attaching data adapter to spinner
            spinner.setAdapter(adapter);
        }
    }
}
