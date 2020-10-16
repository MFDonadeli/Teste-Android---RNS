package com.salcisne.androidteste.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.salcisne.androidteste.model.Produto;
import com.salcisne.androidteste.service.api.ApiModule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoService implements IProdutoService {
    @Override
    public MutableLiveData<List<Produto>> getListaProdutos() {
        final MutableLiveData<List<Produto>> listaProdutosLiveData = new MutableLiveData<>();

        ApiModule.getApi().getProdutos().enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                listaProdutosLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });
        return listaProdutosLiveData;
    }
}
