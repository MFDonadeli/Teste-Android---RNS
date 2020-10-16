package com.salcisne.androidteste.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salcisne.androidteste.R;
import com.salcisne.androidteste.databinding.ActivityListaProdutosBindingImpl;
import com.salcisne.androidteste.model.Produto;
import com.salcisne.androidteste.service.IProdutoService;
import com.salcisne.androidteste.viewmodel.ProdutoViewModel;

import com.salcisne.androidteste.service.ProdutoService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutoActivity extends AppCompatActivity {

    private ProdutoViewModel viewModel;
    private ActivityListaProdutosBindingImpl binding;

    private ListView produtoList;
    private List<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_lista_produtos);
        binding.setLifecycleOwner(this);

        produtoList = (ListView) findViewById(R.id.listView);

        new JsonTask().execute("http://192.168.1.36:8080/filialproduto/");;
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getListaProdutos();



        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);

        produtoList.setAdapter(adapter);
    }

    public class JsonTask extends AsyncTask<String, String, List<Produto>>
    {
        @Override
        protected List<Produto> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Produto>>() {}.getType();
                produtos = gson.fromJson(finalJson, listType);
            }
            catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            super.onPostExecute(produtos);

            setupViewModel();
        }
    }
}
