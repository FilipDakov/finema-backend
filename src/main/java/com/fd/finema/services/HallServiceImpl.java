package com.fd.finema.services;

import com.fd.finema.bom.Hall;
import com.fd.finema.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService{
    private HallRepository hallRepository;

    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public List<Integer> getAvailableHalls() {
        return hallRepository.findAll().stream().map(Hall::getNumber).collect(Collectors.toList());
    }
}
