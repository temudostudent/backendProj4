package aor.paj.ctn.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class StartupBean {
    @Inject
    UserBean userBean;

    @PostConstruct
    public void init() {
        userBean.createDefaultUsersIfNotExistent();
    }
}