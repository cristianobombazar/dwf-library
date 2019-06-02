package com.github.dwflibrary.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        if (Objects.isNull(ApplicationContextProvider.context)) {
            throw new IllegalStateException("Context isn't available!");
        }
        return ApplicationContextProvider.context;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        if (Objects.isNull(ApplicationContextProvider.context)) {
            ApplicationContextProvider.context = applicationContext;
        }
    }

    public static <E> E getBean(Class<E> bean){
        return getContext().getBean(bean);
    }
}
