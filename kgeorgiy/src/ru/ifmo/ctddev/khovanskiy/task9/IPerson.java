package ru.ifmo.ctddev.khovanskiy.task9;

import java.rmi.RemoteException;

public interface IPerson {
	/**
	 * Gets passport number
	 * 
	 * @return passport number
	 * @throws RemoteException
	 */
	String getPassportId() throws RemoteException;

	/**
	 * Gets person`s name
	 * 
	 * @return name
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;

	/**
	 * Gets person`s surname
	 * 
	 * @return surname
	 * @throws RemoteException
	 */
	String getSurname() throws RemoteException;
}
