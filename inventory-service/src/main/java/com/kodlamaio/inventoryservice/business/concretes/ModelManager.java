package com.kodlamaio.inventoryservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.ModelService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryservice.business.rules.ModelBusinessRules;
import com.kodlamaio.inventoryservice.entities.Model;
import com.kodlamaio.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private final ModelMapperService mapper;
    private final ModelRepository repository;
    private final ModelBusinessRules rules;

    @Override
    public List<GetAllModelsResponse> getAll() {
        var models = repository.findAll();
        var response = models
                .stream()
                .map(Model -> mapper.forResponse().map(Model, GetAllModelsResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetModelResponse getById(UUID id) {
        rules.checkIfModelExists(id);
        var model = repository.findById(id);
        var response = mapper.forResponse().map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(UUID.randomUUID());
        repository.save(model);
        var response = mapper.forResponse().map(model, CreateModelResponse.class);
        return response;
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request) {
        rules.checkIfModelExists(id);
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(id);
        repository.save(model);
        var response = mapper.forResponse().map(model, UpdateModelResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfModelExists(id);
        repository.deleteById(id);
    }
}
