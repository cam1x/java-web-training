package by.training.service;

import by.training.entity.BankDeposit;
import by.training.repository.BankDepositRepository;

import java.util.List;
import java.util.Optional;

public class BankDepositService implements Service<BankDeposit> {
    private BankDepositRepository repository;

    public BankDepositService(BankDepositRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(BankDeposit deposit) {
        repository.add(deposit);
    }

    @Override
    public boolean remove(BankDeposit deposit) {
        return repository.remove(deposit);
    }

    @Override
    public Optional<BankDeposit> findById(long id) {
        return repository.getById(id);
    }

    @Override
    public List<BankDeposit> getAll() {
        return repository.getAll();
    }
}
