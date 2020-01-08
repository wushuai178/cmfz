package com.baizhi.ws.service;

import com.baizhi.ws.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    Map queryAllByPage(int page,int rows);

    List<Guru> queryAll();

    Map insertGuru(Guru guru);

    void deleteGuru(List<String> id);

    Map updateGuru(Guru guru);
}
