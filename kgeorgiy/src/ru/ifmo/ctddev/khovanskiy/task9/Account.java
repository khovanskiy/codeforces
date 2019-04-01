package ru.ifmo.ctddev.khovanskiy.task9;

import java.rmi.RemoteException;

public class Account implements IAccount {
	
	protected int amount = 0;
	protected String id = "";
	protected String owner = "";

	public Account(String id, String owner) {
		this.id = id;
		this.owner = owner;
	}

	@Override
	public synchronized String getId() throws RemoteException {
		return id;
	}

	@Override
	public synchronized String getOwner() throws RemoteException {
		return owner;
	}

	@Override
	public synchronized int getAmount() throws RemoteException {
		return amount;
	}

	@Override
	public synchronized void changeAmount(int amount) throws RemoteException {
		this.amount += amount;
	}
}
