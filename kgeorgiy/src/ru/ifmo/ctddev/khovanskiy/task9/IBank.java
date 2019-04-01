package ru.ifmo.ctddev.khovanskiy.task9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBank extends Remote {
	/**
	 * Gets remote object of person
	 * 
	 * @param id
	 *            passport `s number of person
	 * @return remote object
	 * @throws RemoteException
	 */
	IRemotePerson getRemotePerson(String id) throws RemoteException;

	/**
	 * Gets local object of person
	 * 
	 * @param id
	 *            passport`s number of person
	 * @return local object
	 * @throws RemoteException
	 */
	ILocalPerson getLocalPerson(String id) throws RemoteException;

	/**
	 * Creates person object
	 * 
	 * @param passwordNumber
	 *            passportNumber of person
	 * @param name
	 *            name of person
	 * @param surname
	 *            surname of person
	 * @throws RemoteException
	 */
	void createPerson(String passwordNumber, String name, String surname)
			throws RemoteException;

	/**
	 * Gets person`s account by given passport`s id
	 * 
	 * @param id
	 *            account `s id
	 * @return account object
	 * @throws RemoteException
	 */
	IAccount getAccount(String id) throws RemoteException;

	/**
	 * Creates account
	 * 
	 * @param id
	 *            account`s id
	 * @param owner
	 *            owner`s passport number
	 * @throws RemoteException
	 */
	void createAccount(String id, String owner) throws RemoteException;
}
