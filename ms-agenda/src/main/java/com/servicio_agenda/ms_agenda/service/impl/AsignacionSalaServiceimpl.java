package com.servicio_agenda.ms_agenda.service.impl;

import com.servicio_agenda.ms_agenda.model.AsignacionSala;
import com.servicio_agenda.ms_agenda.repository.AsignacionSalaRepository;
import com.servicio_agenda.ms_agenda.service.AsignacionSalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionSalaServiceImpl implements AsignacionSalaService {

    private final AsignacionSalaRepository repository;

    /
    
@Override
@Transactional(readOnly = true)
  public List<AsignacionSala> listarTodas() {
      return repository.findAll();}

    /
     
@Override
@Transactional(readOnly = true)
  public Optional<AsignacionSala> buscarPorId(Long id) {
      return repository.findById(id);}

    /
     
@Override
@Transactional
  public AsignacionSala guardar(AsignacionSala asignacion) {
      return repository.save(asignacion);}

    /
     
@Override
@Transactional
  public void eliminar(Long id) {
      repository.deleteById(id);}
}