package org.flowframe.kernel.jpa.container.services.impl;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.flowframe.kernel.jpa.container.services.IEntityManagerFactoryManager;

public class EMFManager implements IEntityManagerFactoryManager {
	private EntityManagerFactory humanTaskEmf;
	private EntityManagerFactory jbpmEmf;
	private EntityManagerFactory kernelSystemEmf;

	@Override
	public EntityManagerFactory getHumanTaskEmf() {
		return this.humanTaskEmf;
	}

	@Override
	public EntityManagerFactory getJbpmEmf() {
		return this.jbpmEmf;
	}

	@Override
	public EntityManagerFactory getKernelSystemEmf() {
		return this.kernelSystemEmf;
	}

	public void bindKernelSystemEntityManagerFactory(
			EntityManagerFactory kernelSystemEntityManagerFactory, Map<?, ?> properties) {
		this.kernelSystemEmf = kernelSystemEntityManagerFactory;
	}

	public void unbindKernelSystemEntityManagerFactory(
			EntityManagerFactory kernelSystemTransManager, Map<?, ?> properties) {
		this.kernelSystemEmf = null;
	}	

	public void bindConxJbpmEntityManagerFactory(
			EntityManagerFactory conxJBpmEntityManagerFactory, Map<?, ?> properties) {
		this.jbpmEmf = conxJBpmEntityManagerFactory;
	}

	public void unbindConxJbpmEntityManagerFactory(
			EntityManagerFactory conxJBpmEntityManagerFactory, Map<?, ?> properties) {
		this.jbpmEmf = null;
	}	

	public void bindConxHumanTaskEntityManagerFactory(
			EntityManagerFactory conxHumanTaskEntityManagerFactory, Map<?, ?> properties) {
		this.humanTaskEmf = conxHumanTaskEntityManagerFactory;
	}

	public void unbindConxHumanTaskEntityManagerFactory(
			EntityManagerFactory conxHumanTaskEntityManagerFactory, Map<?, ?> properties) {
		this.humanTaskEmf = null;
	}
}
