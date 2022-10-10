package com.example.works;

import java.util.function.Supplier;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.function.SingletonSupplier;

public class LazyObservationMethodInterceptor implements MethodInterceptor, PointcutAdvisor, ApplicationContextAware {
	ApplicationContext context;

	Supplier<ObservationRegistry> registry = SingletonSupplier.of(() -> context.getBean(ObservationRegistry.class));

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return Observation.createNotStarted("observation", this.registry.get()).observeChecked(invocation::proceed);
	}

	@Override
	public Advice getAdvice() {
		return this;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}

	@Override
	public Pointcut getPointcut() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("advised");
		return pointcut;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
