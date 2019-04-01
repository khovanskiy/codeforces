package ru.ifmo.ctddev.khovanskiy.task9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Bank implements IBank {

	private Map<String, IRemotePerson> registry = new HashMap<>();
	private Map<String, IAccount> accounts = new HashMap<>();

	public Bank() {
	}

	@Override
	public synchronized IRemotePerson getRemotePerson(String id) {
		return registry.get(id);
	}

	@Override
	public synchronized ILocalPerson getLocalPerson(String id)
			throws RemoteException {
		IRemotePerson remotePerson = registry.get(id);
		return new LocalPerson(remotePerson.getPassportId(),
				remotePerson.getName(), remotePerson.getSurname());
	}

	@Override
	public synchronized void createPerson(String passwordNumber, String name,
			String surname) throws RemoteException {
		IRemotePerson remotePerson = new RemotePerson(passwordNumber, name,
				surname);
		UnicastRemoteObject.exportObject(remotePerson);
		registry.put(passwordNumber, remotePerson);
	}

	@Override
	public synchronized IAccount getAccount(String id) {
		return accounts.get(id);
	}

	@Override
	public void createAccount(String id, String owner)
			throws RemoteException {
		IAccount remoteAccount;
		synchronized (this) {
			remoteAccount = new Account(id, owner);
			accounts.put(id, remoteAccount);			
		}
		UnicastRemoteObject.exportObject(remoteAccount);
	}

}
