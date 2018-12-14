package cgodin.qc.ca.ecolekarate;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class FragmentLogin extends Fragment implements AdapterView.OnItemSelectedListener{
    private List<String> lstCourrielsComptes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Spinner spinnerCompte;
    private Button btnConnexion;
    public boolean connecte;
    private View myFragment;
    private String cheminServeur = "http://10.0.2.2:8098";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_login, container, false);
        btnConnexion = (Button) myFragment.findViewById(R.id.btnConnexion);
        //btnConnexion = (Button) getView().findViewById((R.id.btnConnexion));
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexion();
            }
        });
        String url = cheminServeur + "/compte/all/username";
        Log.d("Wack !!!!", "onCreateView: Salut ma belle julienne de carrotte");
        new GetAllUsername().execute(url);
        return myFragment;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void connexion(){
        spinnerCompte = (Spinner) myFragment.findViewById(R.id.spinner);
        Log.d("Connexion", "onClick: Je tente de me connecter");
        if(spinnerCompte.isEnabled()){

            connecte = true;
            btnConnexion.setText("Fermer la connexion");
            spinnerCompte.setEnabled(false);
            String url = cheminServeur + "/compte/infos/base/" + spinnerCompte.getSelectedItem().toString();
            new GetCompteConnexion().execute(url);
            //startWebSocket();
            //stomp.brancherStomp();
            //stompPrive.brancherStompPrive();
        }
        else{

            connecte = false;
            btnConnexion.setText("Établir une connexion");
            spinnerCompte.setEnabled(true);
            viderLesInformations();
            //stopWebSocket();
                   /* stomp.terminerStomp();
                    stompPrive.terminerStompPrive();
                    viderInformations();
                    new Logout().execute("");*/
        }
    }

    /**
     * Vider les informations de connexion quand on se déconnecte.
     */
    public void viderLesInformations(){
        TextView tvCeinture = (TextView) getView().findViewById((R.id.tbInfoCeinture));
        TextView tvGrade = (TextView) getView().findViewById((R.id.tbInfoGrade));
        TextView tvPoints = (TextView) getView().findViewById((R.id.tbInfoPoints));
        TextView tvCredits = (TextView) getView().findViewById((R.id.tbInfoCredits));
        TextView tvAlias = (TextView) getView().findViewById((R.id.tbInfoAlias));
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageLogin);

        tvCeinture.setText("");
        tvGrade.setText("");
        tvPoints.setText("");
        tvCredits.setText("");
        tvAlias.setText("");

        imageView.setImageResource(R.drawable.nouser_avatar);
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

                return result.toString().replace("\"", "");
            }
            catch (ProtocolException e) {
                e.printStackTrace();
                //return null;
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

    public class GetCompteConnexion extends AsyncTask<String, Integer, String> {
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
                    JSONArray lstComptes = new JSONArray(s.toString());
                    //tv.setText(lstComptes.getString(6));

                    //Aller chercher les composantes de l'interface qui servent à l'affichage d'information sur le compte.
                    //TextView tvCourriel = (TextView) findViewById(R.id.tbC);
                    TextView tvCeinture = (TextView) getView().findViewById((R.id.tbInfoCeinture));
                    TextView tvGrade = (TextView) getView().findViewById((R.id.tbInfoGrade));
                    TextView tvPoints = (TextView) getView().findViewById((R.id.tbInfoPoints));
                    TextView tvCredits = (TextView) getView().findViewById((R.id.tbInfoCredits));
                    TextView tvAlias = (TextView) getView().findViewById((R.id.tbInfoAlias));
                    ImageView imageView = (ImageView) getView().findViewById(R.id.imageLogin);

                    //Remplir les informations de la personne sélectionnée
                    //tvCourriel.setText(lstComptes.getString(0));
                    //pwd = lstComptes.getString(1);
                    tvAlias.setText(lstComptes.getString(2));
                    String base = lstComptes.getString(3).trim();
                    tvCeinture.setText(lstComptes.getString(4));
                    tvGrade.setText(lstComptes.getString(5));
                    tvPoints.setText(lstComptes.getString(6));
                    tvCredits.setText(lstComptes.getString(7));

                    //Log.d("TEST", pwd);

                    String pureBase64Encoded = base.substring(base.indexOf(",")  + 1);

                    byte[] imageAsBytes = Base64.decode(pureBase64Encoded.getBytes(), Base64.DEFAULT);
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                    //À implémenter plus tard dans le développement
                    //new Login().execute("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
