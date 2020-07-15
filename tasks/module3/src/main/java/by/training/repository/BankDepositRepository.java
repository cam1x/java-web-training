package by.training.repository;

import by.training.entity.BankDeposit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankDepositRepository implements Repository<BankDeposit> {

    private List<BankDeposit> deposits = new ArrayList<>();

    @Override
    public void add(BankDeposit entity) {
        deposits.add(entity);
    }

    @Override
    public boolean remove(BankDeposit entity) {
        return deposits.remove(entity);
    }

    @Override
    public Optional<BankDeposit> getById(long id) {
        return deposits.stream()
                .filter(x->x.getAccountId()==id)
                .findFirst();
    }

    @Override
    public List<BankDeposit> getAll() {
        return new ArrayList<>(deposits);
    }
}
