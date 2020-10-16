package com.salcisne.androidteste.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.salcisne.androidteste.model.Produto;

import java.util.List;

public interface IProdutoService {
    MutableLiveData<List<Produto>> getListaProdutos();
}
