package ru.ifmo.ctddev.khovanskiy.task9;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	public static void main(String[] args) throws RemoteException,
			MalformedURLException {
		Bank bank = new Bank();
		UnicastRemoteObject.exportObject(bank);
		Naming.rebind("rmi://localhost/bank", bank);
	}
}
