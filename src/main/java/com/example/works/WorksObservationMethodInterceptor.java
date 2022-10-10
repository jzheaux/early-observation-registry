package com.example.works;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class WorksObservationMethodInterceptor implements MethodInterceptor, PointcutAdvisor, ApplicationContextAware {
	ObjectProvider<ObservationRegistry> registry;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ObservationRegistry registry = this.registry.getIfAvailable();
		if (registry == null) {
			return invocation.proceed();
		}
		return Observation.createNotStarted("observation", registry).observeChecked(invocation::proceed);
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
		this.registry = context.getBeanProvider(ObservationRegistry.class);
	}
}
