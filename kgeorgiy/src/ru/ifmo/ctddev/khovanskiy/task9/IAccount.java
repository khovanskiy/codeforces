package ru.ifmo.ctddev.khovanskiy.task9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAccount extends Remote {
	/**
	 * Gets account`s id
	 * 
	 * @return account`s id
	 * @throws RemoteException
	 */
	String getId() throws RemoteException;

	/**
	 * Gets passport`s number of owner
	 * 
	 * @return passport`s number
	 * @throws RemoteException
	 */
	String getOwner() throws RemoteException;

	/**
	 * Gets current amount
	 * 
	 * @return current amount
	 * @throws RemoteException
	 */
	int getAmount() throws RemoteException;

	/**
	 * Sets new amount
	 * 
	 * @param amount
	 *            to have been setted
	 * @throws RemoteException
	 */
	void changeAmount(int amount) throws RemoteException;
}
