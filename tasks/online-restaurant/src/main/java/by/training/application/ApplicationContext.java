package by.training.application;

import by.training.contact.*;
import by.training.core.BeanRegistry;
import by.training.core.BeanRegistryImpl;
import by.training.dao.*;
import by.training.discount.DiscountDaoImpl;
import by.training.discount.DiscountServiceImpl;
import by.training.discount.EditDiscountCommand;
import by.training.dish.*;
import by.training.order.*;
import by.training.order.state.OrderStateBuilder;
import by.training.role.RoleDaoImpl;
import by.training.role.RoleServiceImpl;
import by.training.security.ActionProhibitedCommand;
import by.training.security.CryptographyManagerImpl;
import by.training.user.*;
import by.training.validator.RegisterDataValidator;
import by.training.wallet.*;
import by.training.wish.WishDaoImpl;
import by.training.wish.WishServiceImpl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ApplicationContext implements BeanRegistry {
    private final static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private final static Lock INITIALIZE_LOCK = new ReentrantLock();
    private static ApplicationContext INSTANCE;

    private BeanRegistry beanRegistry = new BeanRegistryImpl();

    private ApplicationContext() {

    }

    public static void initialize() {
        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("Context was already initialized");
            } else {
                ApplicationContext context = new ApplicationContext();
                context.init();
                INSTANCE = context;
                INITIALIZED.set(true);
            }
        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static ApplicationContext getInstance() {
        if (INSTANCE != null && INITIALIZED.get()) {
            return INSTANCE;
        } else {
            throw new IllegalStateException("Context wasn't initialized");
        }
    }

    @Override
    public void destroy() {
        ApplicationContext context = getInstance();
        DataSource dataSource = context.getBean(DataSource.class);
        dataSource.close();
        beanRegistry.destroy();
    }

    @Override
    public <T> void registerBean(T bean) {
        this.beanRegistry.registerBean(bean);
    }

    @Override
    public <T> void registerBean(Class<T> beanClass) {
        this.beanRegistry.registerBean(beanClass);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return this.beanRegistry.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String name) {
        return this.beanRegistry.getBean(name);
    }

    @Override
    public <T> boolean removeBean(T bean) {
        return this.beanRegistry.removeBean(bean);
    }

    private void init() {
        registerDataSource();
        registerClasses();
    }

    private void registerDataSource() {
        DataSource dataSource = new DataSourceImpl();
        TransactionManager transactionManager = new TransactionManagerImpl(dataSource);
        ConnectionManager connectionManager = new ConnectionManagerImpl(transactionManager, dataSource);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager);
        registerBean(dataSource);
        registerBean(transactionManager);
        registerBean(connectionManager);
        registerBean(transactionInterceptor);
    }

    private void registerClasses() {
        registerBean(OrderStateBuilder.class);
        registerBean(CryptographyManagerImpl.class);
        registerBean(RegisterDataValidator.class);
        registerDaoClasses();
        registerServiceClasses();
        registerCommandClasses();
    }

    private void registerCommandClasses() {
        registerBean(ViewUserListCommand.class);
        registerBean(RegisterViewUserCommand.class);
        registerBean(LoginUserCommand.class);
        registerBean(RegisterSaveUserCommand.class);
        registerBean(LoginViewUserCommand.class);
        registerBean(LogoutUserCommand.class);
        registerBean(DeleteUserCommand.class);
        registerBean(LockUserCommand.class);
        registerBean(UnlockUserCommand.class);
        registerBean(EditUserRolesCommand.class);
        registerBean(ShowMenuCommand.class);
        registerBean(SaveDishCommand.class);
        registerBean(DeleteDishCommand.class);
        registerBean(ShowDishDetailsCommand.class);
        registerBean(ViewUserProfileCommand.class);
        registerBean(DeleteContactCommand.class);
        registerBean(EditWalletCommand.class);
        registerBean(SaveWalletCommand.class);
        registerBean(DeleteWalletCommand.class);
        registerBean(SaveContactCommand.class);
        registerBean(EditContactCommand.class);
        registerBean(ViewOrderHistoryCommand.class);
        registerBean(ViewOrderBasketCommand.class);
        registerBean(AddDishToOrderCommand.class);
        registerBean(DeleteDishFromOrderCommand.class);
        registerBean(EditDishCommand.class);
        registerBean(EditDishQuantityCommand.class);
        registerBean(ConfirmOrderCommand.class);
        registerBean(DeleteOrderCommand.class);
        registerBean(ActionProhibitedCommand.class);
        registerBean(ViewAllOrdersCommand.class);
        registerBean(OrderNextStatusCommand.class);
        registerBean(OrderPrevStatusCommand.class);
        registerBean(ChangeUserAvatarCommand.class);
        registerBean(OrderSuccessCommand.class);
        registerBean(OrderDetailsCommand.class);
        registerBean(LoginSuccessCommand.class);
        registerBean(EditDiscountCommand.class);
    }

    private void registerServiceClasses() {
        registerBean(RoleServiceImpl.class);
        registerBean(ContactServiceImpl.class);
        registerBean(DiscountServiceImpl.class);
        registerBean(WalletServiceImpl.class);
        registerBean(UserServiceImpl.class);
        registerBean(WishServiceImpl.class);
        registerBean(DishServiceImpl.class);
        registerBean(OrderServiceImpl.class);
    }

    private void registerDaoClasses() {
        registerBean(WalletDaoImpl.class);
        registerBean(DiscountDaoImpl.class);
        registerBean(ContactDaoImpl.class);
        registerBean(RoleDaoImpl.class);
        registerBean(UserDaoImpl.class);
        registerBean(WishDaoImpl.class);
        registerBean(DishDaoImpl.class);
        registerBean(OrderDaoImpl.class);
    }
}