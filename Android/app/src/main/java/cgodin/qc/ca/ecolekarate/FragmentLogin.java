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
    //Chemin pour le serveur localhost qu'on roule avec intelliJ
    private String cheminServeur = "http://10.0.2.2:8098";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_login, container, false);
        btnConnexion = (Button) myFragment.findViewById(R.id.btnConnexion);
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexion();
            }
        });
        String url = cheminServeur + "/compte/all/username";
        new GetAllUsername().execute(url);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Fonction qui est appelé lors d'un évènement click sur la bouton connexion
     */
    public void connexion(){
        spinnerCompte = (Spinner) myFragment.findViewById(R.id.spinner);
        Log.d("Connexion", "onClick: Je tente de me connecter");
        if(spinnerCompte.isEnabled()){

            connecte = true;
            //btnConnexion.setText("Fermer la connexion");
            VariableSession.btnConnexion = "Fermer la connexion";
            //spinnerCompte.setEnabled(false);
            VariableSession.booConnexion = false;
            String url = cheminServeur + "/compte/infos/base/" + spinnerCompte.getSelectedItem().toString();
            new GetCompteConnexion().execute(url);
            //startWebSocket();
            //stomp.brancherStomp();
            //stompPrive.brancherStompPrive();
        }
        else{

            connecte = false;
            //btnConnexion.setText("Établir une connexion");
            VariableSession.btnConnexion = "Établir une connexion";
            //spinnerCompte.setEnabled(true);
            VariableSession.booConnexion = true;
            viderLesInformations();
            remplirLesInformations();
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
        VariableSession.Alias = "";
        VariableSession.Credits = "";
        VariableSession.Points = "";
        VariableSession.grade = "";
        VariableSession.ceinture = "";
        VariableSession.base64 = "";
    }

    /**
     * Cette fonction permet de remplir les infos de comptes à partir de la classe static.
     */
    public void remplirLesInformations(){
        TextView tvCeinture = (TextView) getView().findViewById((R.id.tbInfoCeinture));
        TextView tvGrade = (TextView) getView().findViewById((R.id.tbInfoGrade));
        TextView tvPoints = (TextView) getView().findViewById((R.id.tbInfoPoints));
        TextView tvCredits = (TextView) getView().findViewById((R.id.tbInfoCredits));
        TextView tvAlias = (TextView) getView().findViewById((R.id.tbInfoAlias));
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageLogin);
        Button btnConnexion2 = (Button) getView().findViewById(R.id.btnConnexion);
        Spinner spinner2 = (Spinner) getView().findViewById(R.id.spinner);


        if(!VariableSession.ceinture.equals("")){
            tvCeinture.setText(VariableSession.ceinture);
        }
        else{
            tvCeinture.setText("N/A");
        }

        if(!VariableSession.grade.equals("")){
            tvGrade.setText(VariableSession.grade);
        }
        else{
            tvGrade.setText("N/A");
        }

        if(!VariableSession.Points.equals("")){
            tvPoints.setText(VariableSession.Points);
        }
        else{
            tvPoints.setText("N/A");
        }

        if(!VariableSession.Credits.equals("")){
            tvCredits.setText(VariableSession.Credits);
        }
        else{
            tvCredits.setText("N/A");
        }

        if(!VariableSession.Alias.equals("")){
            tvAlias.setText(VariableSession.Alias);
        }
        else{
            tvAlias.setText("N/A");
        }

        if(!VariableSession.base64.equals("")){
            String pureBase64Encoded = VariableSession.base64.substring(VariableSession.base64.indexOf(",")  + 1);

            byte[] imageAsBytes = Base64.decode(pureBase64Encoded.getBytes(), Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }
        else{
            imageView.setImageResource(R.drawable.nouser_avatar);
        }
        btnConnexion2.setText(VariableSession.btnConnexion);
        spinner2.setSelection(VariableSession.spinnerID);
        spinner2.setEnabled(VariableSession.booConnexion);

    }

    /**
     * Tâches en background qui peremet de remplir le spinner avec la liste
     * de tous les utilisateurs.
     */
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
            remplirLesInformations();
        }
    }

    /**
     * Cette classe interne nous permet d'aller chercher les informations de compte du compte sélectionner
     * dans le spinner. De plus, elle remplis les zone de textes qui contiennent
     * l'information sur l'utilisateur.
     */
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

                    Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);

                    //Remplir les informations de la personne sélectionnée
                    //tvCourriel.setText(lstComptes.getString(0));
                    //pwd = lstComptes.getString(1);

                    /*
                    * Modifier les informations de connexion graphiquement et dans la classe de variable static.
                    * */
                    //tvAlias.setText(lstComptes.getString(2));
                    VariableSession.Alias = lstComptes.getString(2);

                    String base = lstComptes.getString(3).trim();
                    VariableSession.base64 = base;

                    //tvCeinture.setText(lstComptes.getString(4));
                    VariableSession.ceinture = lstComptes.getString(4);

                    //tvGrade.setText(lstComptes.getString(5));
                    VariableSession.grade = lstComptes.getString(5);

                    //tvPoints.setText(lstComptes.getString(6));
                    VariableSession.Points = lstComptes.getString(6);

                    //tvCredits.setText(lstComptes.getString(7));
                    VariableSession.Credits = lstComptes.getString(7);

                    VariableSession.spinnerID = spinner.getSelectedItemPosition();

                    String pureBase64Encoded = base.substring(base.indexOf(",")  + 1);
                    remplirLesInformations();

                    //À implémenter plus tard dans le développement
                    //new Login().execute("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
